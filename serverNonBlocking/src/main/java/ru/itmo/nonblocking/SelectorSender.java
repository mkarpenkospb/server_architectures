package ru.itmo.nonblocking;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class SelectorSender implements Runnable {
    private final Selector selector;
    private final Set<ClientTaskQueue> clients;

    public SelectorSender(Selector selector, Set<ClientTaskQueue> clients) {
        this.selector = selector;
        this.clients = clients;
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
                    if (key.isWritable()) {
                        ClientTaskQueue tasks = (ClientTaskQueue) key.attachment();
                        SenderInfo info = tasks.getInfo();
                        info.sendData();
                        if (info.isReady()) {
                            info.reset();
                            ByteBuffer head = tasks.getNextBuffer();
                            if (head == null) {
                                key.cancel();
                                tasks.setUnregistered();
                            } else {
                                info.setBuffer(head);
                            }
                        }
                    }

                    Iterator<ClientTaskQueue> iterClients = clients.iterator();
                    while (iterClients.hasNext()) {
                        ClientTaskQueue current = iterClients.next();
                        if (current.setRegistered()) {
                            current.getChannel().register(selector, SelectionKey.OP_WRITE, current);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
