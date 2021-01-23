package ru.itmo.asynch;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int INT_SIZE = 4;

    private final int port;
    private final int threadsNum;

    public Server(int port, int threadsNum) {
        this.port = port;
        this.threadsNum = threadsNum;
    }

    public void start() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);
        try (AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open()) {
            server.bind(new InetSocketAddress(port));
            while (true) {
                // В целом, как-то принять клиента нужно
                AsynchronousSocketChannel client = server.accept().get();


                // На случай если все со всем перемешается, контексты сделаем уникальными каждый раз
                ReceiveContext receiveContext = new ReceiveContext();

                client.read(receiveContext.getDataSize(), receiveContext,
                        new CompletionHandler<>() {
                            @Override
                            public void completed(Integer result, ReceiveContext attachment) {
                                if (attachment.isSized()) {
                                    if (attachment.isEmpty()) {
                                        attachment.setSize();
                                        attachment.allocateBuffer();
                                    }
                                    if (!attachment.isFinished()) {
                                        client.read(receiveContext.getData(), receiveContext, this);
                                    } else {
                                        executor.submit(new ServerTask(client, receiveContext.getData(), new RespondContext()));

                                        ReceiveContext newReceiveContext = new ReceiveContext();
                                        client.read(newReceiveContext.getDataSize(), newReceiveContext, this);
                                    }
                                } else {
                                    client.read(receiveContext.getDataSize(), receiveContext, this);
                                }
                            }

                            @Override
                            public void failed(Throwable exc, ReceiveContext attachment) {
                                exc.printStackTrace();
                            }
                        });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
