package ru.itmo.asynch;

import ru.itmo.protocol.ServerStat;

import java.nio.ByteBuffer;

public class ReceiveContext {
    private static final int INT_SIZE = 4;
    private int received = 0;
    private final ByteBuffer dataSize = ByteBuffer.allocate(INT_SIZE);
    private int messageSize;
    private ByteBuffer data;
    private boolean sized = false;
    private boolean finished = false;
    private final ServerStat.ClientStat clientTime;

    public ReceiveContext(ServerStat.ClientStat clientTime) {
        this.clientTime = clientTime;
        clientTime.start();
    }

    public ServerStat.ClientStat getClientTime() {
        return clientTime;
    }

    public void updateReceivedOnSized(int value) {
        received += value;
        if (received == INT_SIZE) {
            sized = true;
        }
    }

    public void updateReceivedOnData(int value) {
        received += value;
        if (received == messageSize) {
            finished = true;
        }
    }

    public ByteBuffer getDataSize() {
        return dataSize;
    }
    public ByteBuffer getData() {
        return data;
    }

    public boolean isSized() {
        return sized;
    }

    public void setSize() {
        received = 0;
        dataSize.flip();
        messageSize = dataSize.getInt();
    }

    public boolean isEmpty() {
        return data == null;
    }

    public boolean isFinished() {
        return finished;
    }

    public void allocateBuffer() {
        data = ByteBuffer.allocate(messageSize);
    }

}
