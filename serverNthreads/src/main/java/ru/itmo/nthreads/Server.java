package ru.itmo.nthreads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final int threadsNum;

    private ExecutorService executor;


    public Server(int port, int threadsNum) {
        this.port = port;
        this.threadsNum = threadsNum;
    }

    void start() throws IOException {
        executor = Executors.newFixedThreadPool(threadsNum);
        try(ServerSocket server = new ServerSocket(port)) {
            while (true) {
                Socket socket = server.accept();
                Thread thread = new Thread(new ClientHolder(socket, executor));
                // чтобы создать и забыть
                thread.setDaemon(true);
                thread.start();
            }
        }
    }
}
