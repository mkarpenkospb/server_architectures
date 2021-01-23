package ru.itmo.asynch;

import java.nio.ByteBuffer;

public class ReceiveContext {
    private static final int INT_SIZE = 4;
    private final ByteBuffer dataSize = ByteBuffer.allocate(INT_SIZE);
    private int messageSize;
    private ByteBuffer data;

    public ByteBuffer getDataSize() {
        return dataSize;
    }
    public ByteBuffer getData() {
        return data;
    }

    public boolean isSized() {
        return !dataSize.hasRemaining();
    }

    public void setSize() {
        messageSize = dataSize.getInt();
    }

    public boolean isEmpty() {
        return data == null;
    }

    public boolean isFinished() {
        return !data.hasRemaining();
    }

    public void allocateBuffer() {
        data = ByteBuffer.allocate(messageSize);
    }

}
