package ru.itmo.nthreads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final int threadsNum;

    private Executor executor;


    public Server(int port, int threadsNum) {
        this.port = port;
        this.threadsNum = threadsNum;
    }


    void start() throws IOException {
        executor = Executors.newFixedThreadPool(threadsNum);
        try(ServerSocket server = new ServerSocket(port)) {
            while (true) {
                Socket socket = server.accept();

            }
        }
    }
}
