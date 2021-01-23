package ru.itmo.nonblocking;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class SelectorReceiver implements Runnable {
    private final Selector selector;
    private final ExecutorService executor;
    public SelectorReceiver(Selector selector, ExecutorService executor) {
        this.selector = selector;
        this.executor = executor;
    }

    @Override
    public void run() {
        Set<SelectionKey> selectedKeys;
        Iterator<SelectionKey> iter;
        try {
            while (true) {
                selector.select(10);
                selectedKeys = selector.selectedKeys();
                iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isReadable()) {
                        ReceiverInfo info = (ReceiverInfo) key.attachment();
                        if (!info.isSized()) {
                            info.setMessageSize();
                            continue;
                        }
                        if (info.isRemoved()) {
                            key.cancel();
                            continue;
                        }
                        info.receiveData();
                        if (info.isReady()) {
                            executor.submit(new ServerTask(info.getClientTasks(), info.getMessage(), info.getStatistic().getNewSortStat(), info.getClientTime()));
                            info.reset();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
