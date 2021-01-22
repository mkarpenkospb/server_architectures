package ru.itmo.nonblocking;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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
        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);

        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            server.bind(new InetSocketAddress(this.port));
            // серверный канал блокирующий
            Selector selector = Selector.open();

            Thread receiver = new Thread(new SelectorReceiver(selector));
            receiver.setDaemon(true);
            receiver.start();

            while (true) {
                SocketChannel client = server.accept();
                client.configureBlocking(false);
                client.register(selector,
                        SelectionKey.OP_READ & SelectionKey.OP_WRITE, new ClientInfo(client));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
