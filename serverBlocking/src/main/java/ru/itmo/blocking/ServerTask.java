package ru.itmo.blocking;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class ServerTask implements Callable<List<Integer>> {
    private final List<Integer> data;

    private void swap(int i, int j) {
        Integer tmp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, tmp);
    }

    private void insertionSort() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (data.get(j) >= data.get(j - 1)) break;
                swap(j, j-1);
            }
        }
    }

    ServerTask(List<Integer> data) {
        this.data = data;
    }

    // insertion sort
    @Override
    public List<Integer> call() {
        insertionSort();

//        System.out.println(data);
        return data;
    }
}
