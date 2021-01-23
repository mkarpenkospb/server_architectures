package ru.itmo.asynch;

import ru.itmo.protocol.ServerStat;

import java.nio.ByteBuffer;

public class RespondContext {
    private ByteBuffer data;
    private ServerStat.ClientStat clientStat;

    public void setBuffer(ByteBuffer data) {
        this.data = data;
    }
    public void setClientStat(ServerStat.ClientStat clientStat) {
        this.clientStat = clientStat;
    }

    public ByteBuffer getData() {
        return data;
    }
    public ServerStat.ClientStat getClientStat() {
        return clientStat;
    }

    public boolean isFinished() {
        return !data.hasRemaining();
    }
}
