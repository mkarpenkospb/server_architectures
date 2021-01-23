package ru.itmo.nonblocking;

import com.google.protobuf.InvalidProtocolBufferException;
import ru.itmo.protocol.Protocol.IntegerArray;
import ru.itmo.protocol.ServerStat;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReceiverInfo {
    private static final int INT_SIZE = 4;

    private int messageSize;
    // TODO: убрать это, там еть hasRemaining
    private int received = 0;
    // TODO: data каждый раз новая все равно, так что можно парсинг потокам в пуле оставить
    private ByteBuffer data;
    private final ByteBuffer dataSize = ByteBuffer.allocate(INT_SIZE);
    private final ClientTaskQueue clientTasks;
    private final Set<ClientTaskQueue> clients;
    private final SocketChannel socketChannel;
    private boolean ready = false;
    private boolean sized = false;
    private boolean removed = false;
    private ServerStat.ClientStat clientTime;
    private final ServerStat statistic;

    public ReceiverInfo(SocketChannel socketChannel, ClientTaskQueue clientTasks, Set<ClientTaskQueue> clients, ServerStat statistic) {
        this.socketChannel = socketChannel;
        this.clientTasks = clientTasks;
        this.clients = clients;
        this.clientTime = statistic.getNewClientStat();
        this.statistic = statistic;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public ServerStat.ClientStat getClientTime() {
        return clientTime;
    }

    public ServerStat getStatistic() {
        return statistic;
    }

    public void setMessageSize() {
        if (!clientTime.isStarted()) {
            clientTime.start();
        }
        try {
            if (received < INT_SIZE) {
                received += socketChannel.read(dataSize);
            }
            if (received == INT_SIZE) {
                sized = true;
                received = 0;
                dataSize.flip();
                messageSize = dataSize.getInt();
                if (messageSize == -1) {
                    clients.remove(clientTasks);
                    removed = true;
                }
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

    public boolean isRemoved() {
        return removed;
    }

    public ClientTaskQueue getClientTasks() {
        return  clientTasks;
    }

    List<Integer> getMessage() throws InvalidProtocolBufferException {
        IntegerArray request = IntegerArray.parseFrom(data);
        List<Integer> array = new ArrayList<>(request.getArrayList());
        return array;
    }

    public void reset() {
        ready = false;
        sized = false;
        received = 0;
        messageSize = 0;
        data.clear();
        dataSize.clear();
        clientTime = statistic.getNewClientStat();
    }
}
