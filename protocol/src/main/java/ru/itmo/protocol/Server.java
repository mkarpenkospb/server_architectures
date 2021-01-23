package ru.itmo.protocol;

public interface  Server {
    void start();
    ServerStat getStatistic();
    void updateStatistic();
}
