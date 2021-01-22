package ru.itmo.nonblocking;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SenderInfo {
    private ByteBuffer data;
    private final SocketChannel socketChannel;
    private boolean ready = false;

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

    public void sendData() {
        try {
            if (data.hasRemaining()) {
                socketChannel.write(data);
            } else {
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
    }
}
