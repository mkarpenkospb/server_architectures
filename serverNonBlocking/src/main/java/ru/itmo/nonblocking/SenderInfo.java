package ru.itmo.nonblocking;

import ru.itmo.protocol.ServerStat;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SenderInfo {
    private ByteBuffer data;
    private final SocketChannel socketChannel;
    private boolean ready = false;
    private ServerStat.ClientStat clientTime;

    public SenderInfo(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.data = buffer;
        buffer.flip();
        this.ready = false;
    }

    public void setTimer(ServerStat.ClientStat clientTime) {
        this.clientTime = clientTime;
    }

    public void sendData() {
        try {
            if (data.hasRemaining()) {
                socketChannel.write(data);
            }
            if (!data.hasRemaining()) {
                if (!clientTime.isFinished()) {
                    clientTime.finish();
                    clientTime.updateClient();
                }
                ready = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        return ready;
    }

    public void reset() {
        ready = false;
        data = null;
        clientTime = null;
    }

    public boolean isEmpty() {
        return data == null;
    }
}
