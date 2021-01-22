package ru.itmo.nthreads;

import java.util.Collections;
import java.util.List;

public class ServerTask implements Runnable {
    private final List<Integer> data;

    private void insertionSort() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (data.get(i) >= data.get(j - 1)) break;
                Collections.swap(data, j, j - 1);
            }
        }
    }

    ServerTask(List<Integer> data) {
        this.data = data;
    }

    // insertion sort
    @Override
    public void run() {
        insertionSort();
    }
}
