package ru.itmo.gui;

import ru.itmo.asynch.ServerAsynch;
import ru.itmo.blocking.ServerBlocking;
import ru.itmo.client.Client;
import ru.itmo.nonblocking.ServerNonBlocking;
import ru.itmo.protocol.Server;
import ru.itmo.protocol.ServerStat;

import java.util.ArrayList;

public class Tester {
    private static int PORT = 8088;
    private static String HOST = "localhost";
    private static int THREADS = 10;
    private Main.ExperimentParameters parameters;

    public Tester(Main.ExperimentParameters parameters) {
        this.parameters = parameters;
    }

    private void runClients(int M, long delta, int N) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            Thread t = new Thread(() -> {
                new Client(HOST, PORT, parameters.X, N, delta).start();
            });
            threads.add(t);
        }
        threads.forEach(Thread::start);
        threads.forEach((t) -> {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void start() {
        System.out.println("Tester started");
        Server server;
        switch (parameters.architecture) {
            case ASYNCH:
                server = new ServerAsynch(PORT, THREADS);
                break;
            case BLOCKING:
                server = new ServerBlocking(PORT, THREADS);
                break;
            case NONBLOCKING:
                server = new ServerNonBlocking(PORT, THREADS);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + parameters.architecture);
        }

        Server finalServer = server;
        Thread serverThread = new Thread(finalServer::start);
        serverThread.setDaemon(true);
        serverThread.start();
        ServerStat stat = server.getStatistic();
        try {
            switch (parameters.parameter) {
                case N:
                    int from = (int) parameters.from;
                    int to = (int) parameters.to;
                    int step = (int) parameters.step;
                    for (int n = from; n < to; n += step) {
                        runClients(parameters.M, parameters.delta, n);
                        Thread.sleep(100);
                        System.out.println(stat.getClientTime() / (long) parameters.X / (long) parameters.M);
                        System.out.println(stat.getSortingTime() / (long) parameters.X / (long) parameters.M);
                        server.updateStatistic();
                    }

                    break;
                case M:
                    server = new ServerBlocking(PORT, THREADS);
                    break;
                case DELTA:
                    server = new ServerNonBlocking(PORT, THREADS);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + parameters.architecture);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
