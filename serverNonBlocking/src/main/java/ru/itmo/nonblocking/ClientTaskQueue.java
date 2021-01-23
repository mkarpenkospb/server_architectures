package ru.itmo.nonblocking;

import ru.itmo.protocol.Protocol.IntegerArray;
import ru.itmo.protocol.ServerStat;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicReference;

enum State {
    NEW, REGISTERED, UNREGISTERED
}

public class ClientTaskQueue {
    private final long id;
    private static final int INT_SIZE = 4;
    private final SenderInfo info;
    private final Selector selectorSender;
    private final ConcurrentLinkedDeque<StatBuffer> queue = new ConcurrentLinkedDeque<>();
    private final AtomicReference<State> state = new AtomicReference<>(State.UNREGISTERED);

    public ClientTaskQueue(long id, SenderInfo info, Selector selectorSender) {
        this.info = info;
        this.selectorSender = selectorSender;
        this.id = id;
    }

    public void add(List<Integer> sorted, ServerStat.ClientStat clientTime) {
        IntegerArray response = IntegerArray.newBuilder()
                .addAllArray(sorted)
                .build();

        ByteBuffer buffer = ByteBuffer.allocate(INT_SIZE + response.getSerializedSize());
        buffer.putInt(response.getSerializedSize());
        buffer.put(response.toByteArray());
        queue.add(new StatBuffer(buffer, clientTime));
        state.compareAndSet(State.UNREGISTERED, State.NEW);
        selectorSender.wakeup();
    }

    public SocketChannel getChannel() {
        return info.getSocketChannel();
    }

    public SenderInfo getInfo() {
        return info;
    }

    public StatBuffer getNext() {
        if (!queue.isEmpty()) {
            return queue.pop();
        }
        return null;
    }

    public boolean setRegistered() {
        return state.compareAndSet(State.NEW, State.REGISTERED);
    }
    // с однопоточным селектором тут false невозможен, ибо флаг State.REGISTERED только он ставит
    public void setUnregistered() {
        state.compareAndSet(State.REGISTERED, State.UNREGISTERED);
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ClientTaskQueue) {
            return  ((ClientTaskQueue) other).id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    public static class StatBuffer {
        private final ByteBuffer buffer;
        private final ServerStat.ClientStat clientTime;

        public StatBuffer(ByteBuffer buffer, ServerStat.ClientStat clientTime) {
            this.buffer = buffer;
            this.clientTime = clientTime;
        }

        public ByteBuffer getBuffer() {
            return buffer;
        }

        public ServerStat.ClientStat getClientTime() {
            return clientTime;
        }
    }

}
