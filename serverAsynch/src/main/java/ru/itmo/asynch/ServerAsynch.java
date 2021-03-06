package ru.itmo.asynch;

import ru.itmo.protocol.Server;
import ru.itmo.protocol.ServerStat;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerAsynch implements Server {
    private ServerStat statistic = new ServerStat();
    private final int port;
    private final int threadsNum;

    public ServerAsynch(int port, int threadsNum) {
        this.port = port;
        this.threadsNum = threadsNum;
    }

    public ServerStat getStatistic() {
        return statistic;
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
                ReceiveContext receiveContext = new ReceiveContext(statistic.getNewClientStat());

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
                                        executor.submit(new ServerTask(client, attachment.getData(), new RespondContext(),
                                                attachment.getClientTime(), statistic
                                                ));

                                        ReceiveContext newReceiveContext = new ReceiveContext(statistic.getNewClientStat());
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

    @Override
    public void updateStatistic() {
        statistic = new ServerStat();
    }
}
