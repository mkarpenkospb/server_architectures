package ru.itmo.nonblocking;

import ru.itmo.protocol.ServerStat;

import java.util.Collections;
import java.util.List;

public class ServerTask implements Runnable {
    private final ClientTaskQueue clientTasks;
    private final List<Integer> data;
    private final ServerStat.SortStat sortTime;
    private final ServerStat.ClientStat clientTime;

    private void insertionSort() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (data.get(j) >= data.get(j - 1)) break;
                Collections.swap(data, j, j-1);
            }
        }
    }

    ServerTask(ClientTaskQueue clientTasks, List<Integer> data, ServerStat.SortStat sortTime, ServerStat.ClientStat clientTime) {
        this.sortTime = sortTime;
        this.clientTime = clientTime;
        this.clientTasks = clientTasks;
        this.data = data;
    }


    @Override
    public void run() {
        sortTime.start();
        insertionSort();
        sortTime.finish();
        sortTime.updateSorting();
        clientTasks.add(data, clientTime);

    }
}
