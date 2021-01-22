package ru.itmo.nonblocking;

import java.util.Collections;
import java.util.List;

public class ServerTask implements Runnable {
    private final ClientTaskQueue clientTasks;
    private final List<Integer> data;

    private void insertionSort() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (data.get(j) >= data.get(j - 1)) break;
                Collections.swap(data, j, j-1);
            }
        }
    }

    ServerTask(ClientTaskQueue clientTasks, List<Integer> data) {
        this.clientTasks = clientTasks;
        this.data = data;
    }


    @Override
    public void run() {
        insertionSort();
        clientTasks.add(data);
    }
}
