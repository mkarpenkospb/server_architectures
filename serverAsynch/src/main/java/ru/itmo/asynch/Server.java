package ru.itmo.asynch;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final int threadsNum;

    public Server(int port, int threadsNum) {
        this.port = port;
        this.threadsNum = threadsNum;
    }

    public void start() {
        System.out.println("server start");
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
                                        attachment.updateReceivedOnData(result);
                                        client.read(attachment.getData(), attachment, this);
                                    } else {
                                        attachment.getData().flip();
                                        executor.submit(new ServerTask(client, attachment.getData(), new RespondContext()));

                                        ReceiveContext newReceiveContext = new ReceiveContext();
                                        client.read(newReceiveContext.getDataSize(), newReceiveContext, this);
                                    }
                                } else {
                                    attachment.updateReceivedOnSized(result);
                                    client.read(attachment.getDataSize(), attachment, this);
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
