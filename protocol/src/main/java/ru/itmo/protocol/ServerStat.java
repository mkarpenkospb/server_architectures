package ru.itmo.protocol;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class ServerStat {
    private long sortingTime = 0;
    private long clientTime = 0;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    private abstract class StatItem {
        private boolean started = false;
        private boolean finished = false;
        private long startTime;
        private long endTime;

        public boolean isStarted() {
            return started;
        }

        public boolean isFinished() {
            return finished;
        }

        public void start() {
            started = true;
            startTime = System.currentTimeMillis();
        }

        public void finish() {
            finished = true;
            endTime = System.currentTimeMillis();
        }

        public void updateSorting() {
            executor.submit(() -> {
                sortingTime += (endTime - startTime);
            });
        }

        public void updateClient() {
            executor.submit(() -> {
                clientTime += (endTime - startTime);
            });
        }
    }

    public class ClientStat extends StatItem {}
    public class SortStat extends StatItem {}

    public ClientStat getNewClientStat() {
        return new ClientStat();
    }

    public SortStat getNewSortStat() {
        return new SortStat();
    }

    public long getSortingTime() {
        return sortingTime;
    }
    public long getClientTime() {
        return clientTime;
    }
}
