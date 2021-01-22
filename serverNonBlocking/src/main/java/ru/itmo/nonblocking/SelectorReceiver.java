package ru.itmo.nonblocking;

import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class SelectorReceiver implements Runnable {
    private final Selector selector;
    private final ExecutorService executor;

//    Map<int> has

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
                selector.select();
                selectedKeys = selector.selectedKeys();
                iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isReadable()) {
                        ClientInfo info = (ClientInfo) key.attachment();
                        if (!info.isSized()) {
                            info.setMessageSize();
                            continue;
                        }
                        info.receiveData();
                        if (info.isReady()) {

                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }
}
