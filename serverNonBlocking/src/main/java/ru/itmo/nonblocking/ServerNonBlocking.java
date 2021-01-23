package ru.itmo.nonblocking;

import ru.itmo.protocol.Server;
import ru.itmo.protocol.ServerStat;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerNonBlocking implements Server {
    private ServerStat statistic = new ServerStat();
    private long ids = 0;
    private final int port;
    private final int threadsNum;
    // TODO ? А если кто-то из клиентов отвалился, выкинуть его отсюда?
    private final Set<ClientTaskQueue> clients = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public ServerNonBlocking(int port, int threadsNum) {
        this.port = port;
        this.threadsNum = threadsNum;
    }

    public void start() {
        System.out.println("start server");
        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);

        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            server.bind(new InetSocketAddress(this.port));
            // серверный канал блокирующий
            Selector selectorReceiver = Selector.open();
            Selector selectorSender = Selector.open();

            Thread receiver = new Thread(new SelectorReceiver(selectorReceiver, executor));
            receiver.setDaemon(true);
            receiver.start();

            Thread sender = new Thread(new SelectorSender(selectorSender, clients));
            sender.setDaemon(true);
            sender.start();

            while (true) {
                SocketChannel clientChannel = server.accept();
                clientChannel.configureBlocking(false);
                ClientTaskQueue clientTasks = new ClientTaskQueue(ids++, new SenderInfo(clientChannel), selectorSender);
                clients.add(clientTasks);

                clientChannel.register(selectorReceiver,
                        SelectionKey.OP_READ, new ReceiverInfo(clientChannel,  clientTasks, clients, statistic));
                selectorReceiver.wakeup();
//                System.out.println("new client registered");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ServerStat getStatistic() {
        return statistic;
    }

    @Override
    public void updateStatistic() {
        statistic = new ServerStat();
    }

}
