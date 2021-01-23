package ru.itmo.gui;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import ru.itmo.asynch.ServerAsynch;
import ru.itmo.blocking.ServerBlocking;
import ru.itmo.client.Client;
import ru.itmo.nonblocking.ServerNonBlocking;
import ru.itmo.protocol.Server;
import ru.itmo.protocol.ServerStat;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class Tester {
    private static int PORT = 8088;
    private static String HOST = "localhost";
    private static int THREADS = 10;
    private Main.ExperimentParameters parameters;
    private final ProgressBar pb;


    private final ArrayList<XYChart.Data<Number, Number>> sorting = new ArrayList<>();
    private final ArrayList<XYChart.Data<Number, Number>> clientOnServer = new ArrayList<>();
    private final ArrayList<XYChart.Data<Number, Number>> clientFullTime = new ArrayList<>();

    public Tester(Main.ExperimentParameters parameters, ProgressBar pb) {
        this.parameters = parameters;
        this.pb = pb;
    }

    public static class Counter {
        private final AtomicLong counter = new AtomicLong(0);

        public long getValue() {
            return counter.get();
        }

        public void increment(long value) {
            while (true) {
                long existingValue = getValue();
                long newValue = existingValue + value;
                if (counter.compareAndSet(existingValue, newValue)) {
                    return;
                }
            }
        }
    }

    public ArrayList<XYChart.Data<Number, Number>> getSorting() {
        return sorting;
    }
    public ArrayList<XYChart.Data<Number, Number>> getClientOnServer() {
        return clientOnServer;
    }
    public ArrayList<XYChart.Data<Number, Number>> getClientFullTime() {
        return clientFullTime;
    }

    private long runClients(int M, long delta, int N) {
        ArrayList<Thread> threads = new ArrayList<>();
        Counter cnt = new Counter();

        for (int i = 0; i < M; i++) {
            Thread t = new Thread(() -> cnt.increment(new Client(HOST, PORT, M, N, delta).start()));
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

        return cnt.getValue();
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
        Function<Long, Long> runClientsWithParam = getRunningClientsFunction();
        Function<Long, Double> getDenominator = getDenominatorFunction();
        try {
            long length = parameters.to - parameters.from;
            long path;
            for (long p = parameters.from; p < parameters.to; p += parameters.step) {
                ServerStat stat = server.getStatistic();
                path = (p - parameters.from);
                long res = runClientsWithParam.apply(p);
                Thread.sleep(100);
                double denominator = getDenominator.apply(p);

                sorting.add(new XYChart.Data<>(p, (double) stat.getSortingTime() / denominator));
                clientOnServer.add(new XYChart.Data<>(p, (double) stat.getClientTime() /denominator));
                clientFullTime.add(new XYChart.Data<>(p, (double) res / denominator));
                server.updateStatistic();
                double progress = (double) path / (double) length;
                System.out.print((double)Math.round(progress * 100d) / 100d + ", ");
                // не работает(
                pb.setProgress( progress);
            }
            System.out.println("finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Function<Long, Double> getDenominatorFunction() {
        switch (parameters.parameter) {
            case N:
            case DELTA:
                return (Long n) -> (double) parameters.M * (double) parameters.X;
            case M:
                return (Long m) -> (double) m * (double) parameters.X;
            default:
                throw new IllegalStateException("Unexpected value: " + parameters.parameter);
        }
    }


    private Function<Long, Long> getRunningClientsFunction() {
        switch (parameters.parameter) {
            case N:
                return (Long n) -> runClients(parameters.M, parameters.delta, n.intValue());
            case M:
                return (Long m) -> runClients(m.intValue(), parameters.delta, parameters.N);
            case DELTA:
                return (Long d) -> runClients(parameters.M, d, parameters.N);
            default:
                throw new IllegalStateException("Unexpected value: " + parameters.parameter);
        }
    }
}
