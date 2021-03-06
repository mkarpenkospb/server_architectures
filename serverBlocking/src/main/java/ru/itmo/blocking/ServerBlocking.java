package ru.itmo.blocking;

import ru.itmo.protocol.Server;
import ru.itmo.protocol.ServerStat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerBlocking implements Server {

    private ServerStat statistic = new ServerStat();
    private final int port;
    private final int threadsNum;

    public ServerBlocking(int port, int threadsNum) {
        this.port = port;
        this.threadsNum = threadsNum;
    }

    public ServerStat getStatistic() {
        return statistic;
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);
        try(ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server started");
            while (true) {
                Socket socket = server.accept();
                Thread thread = new Thread(new ClientHolder(socket, executor, statistic));
                // чтобы создать и забыть
                thread.setDaemon(true);
                thread.start();
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
