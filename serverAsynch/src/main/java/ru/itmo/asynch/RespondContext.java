package ru.itmo.asynch;

import java.nio.ByteBuffer;

public class RespondContext {
    private ByteBuffer data;

    public void setBuffer(ByteBuffer data) {
        this.data = data;
    }

    public ByteBuffer getData() {
        return data;
    }

    public boolean isFinished() {
        return !data.hasRemaining();
    }
}
