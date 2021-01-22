package ru.itmo.nonblocking;

import com.google.protobuf.InvalidProtocolBufferException;
import ru.itmo.protocol.Protocol;
import ru.itmo.protocol.Protocol.IntegerArray;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class ReceiverInfo {
    private static final int INT_SIZE = 4;

    private int messageSize;
    private int received = 0;
    private ByteBuffer data;
    private final ByteBuffer dataSize = ByteBuffer.allocate(INT_SIZE);
    private final ClientTaskQueue clientTasks;
    private final SocketChannel socketChannel;
    private boolean ready = false;
    private boolean sized = false;


    public ReceiverInfo(SocketChannel socketChannel, ClientTaskQueue clientTasks) {
        this.socketChannel = socketChannel;
        this.clientTasks = clientTasks;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setMessageSize() {
        try {
            if (received < INT_SIZE) {
                received += socketChannel.read(data);
            }
            if (received == INT_SIZE) {
                sized = true;
                received = 0;
                messageSize = dataSize.getInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = ByteBuffer.allocate(messageSize);
    }

    public void receiveData() {
        try {
            if (received < messageSize) {
                received += socketChannel.read(data);
            }
            if (received == messageSize) {
                received = 0;
                ready = true;
                data.flip();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        return ready;
    }

    public boolean isSized() {
        return sized;
    }

    public ClientTaskQueue getClientTasks() {
        return  clientTasks;
    }

    List<Integer> getMessage() throws InvalidProtocolBufferException {
        IntegerArray request = IntegerArray.parseFrom(data);
        return request.getArrayList();
    }

    public void reset() {
        ready = false;
        sized = false;
        received = 0;
        messageSize = 0;
        data.clear();
        dataSize.clear();
    }
}
