package ru.itmo.nonblocking;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final int threadsNum;
    // TODO ? А если кто-то из клиентов отвалился, выкинуть его отсюда?
    private final Set<ClientTaskQueue> clients = new HashSet<>();

    public Server(int port, int threadsNum) {
        this.port = port;
        this.threadsNum = threadsNum;
    }

    public void start() {
        System.out.println("start server");
        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);

        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            server.bind(new InetSocketAddress(this.port));
            // серверный канал блокирующий
            Selector selector = Selector.open();

            Thread receiver = new Thread(new SelectorReceiver(selector, executor));
            receiver.setDaemon(true);
            receiver.start();

            Thread sender = new Thread(new SelectorSender(selector, clients));
            sender.setDaemon(true);
            sender.start();

            while (true) {
                SocketChannel client = server.accept();
                client.configureBlocking(false);
                ClientTaskQueue clientTasks = new ClientTaskQueue(new SenderInfo(client));
                clients.add(clientTasks);

                client.register(selector,
                        SelectionKey.OP_READ, new ReceiverInfo(client,  clientTasks));
                System.out.println("new client registered");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
