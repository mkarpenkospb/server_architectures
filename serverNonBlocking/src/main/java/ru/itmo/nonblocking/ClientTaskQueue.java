package ru.itmo.nonblocking;

import ru.itmo.protocol.Protocol.IntegerArray;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicReference;

enum State {
    NEW, REGISTERED, UNREGISTERED
}

public class ClientTaskQueue {
    private static final int INT_SIZE = 4;
    private final SenderInfo info;
    private final ConcurrentLinkedDeque<ByteBuffer> queue = new ConcurrentLinkedDeque<>();
    private final AtomicReference<State> state = new AtomicReference<>(State.UNREGISTERED);

    public ClientTaskQueue(SenderInfo info) {
        this.info = info;
    }

    public void add(List<Integer> sorted) {
        IntegerArray response = IntegerArray.newBuilder()
                .addAllArray(sorted)
                .build();

        ByteBuffer buffer = ByteBuffer.allocate(INT_SIZE + response.getSerializedSize());
        buffer.putInt(response.getSerializedSize());
        buffer.put(response.toByteArray());
        queue.add(buffer);
        state.compareAndSet(State.UNREGISTERED, State.NEW);
    }


    public SocketChannel getChannel() {
        return info.getSocketChannel();
    }

    public SenderInfo getInfo() {
        return info;
    }

    public ByteBuffer getNextBuffer() {
        if (!queue.isEmpty()) {
            return queue.pop();
        }
        return null;
    }

    public boolean setRegistered() {
        return state.compareAndSet(State.NEW, State.REGISTERED);
    }
    // с однопоточным селектором тут false невозможен, ибо флаг State.REGISTERED только он ставит
    public boolean setUnregistered() {
        return state.compareAndSet(State.REGISTERED, State.UNREGISTERED);
    }

}
