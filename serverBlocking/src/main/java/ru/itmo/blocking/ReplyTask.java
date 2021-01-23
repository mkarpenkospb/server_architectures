package ru.itmo.blocking;
import ru.itmo.protocol.Protocol;
import ru.itmo.protocol.Protocol.IntegerArray;
import ru.itmo.protocol.ServerStat;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class ReplyTask implements Runnable {
    private final DataOutputStream os;
    private final List<Integer> sortedData;
    private final ServerStat.ClientStat clientTime;

    public ReplyTask(DataOutputStream os, List<Integer> sortedData, ServerStat.ClientStat clientTime) {
        this.os = os;
        this.sortedData = sortedData;
        this.clientTime =clientTime;
    }

    @Override
    public void run() {
        try {
            IntegerArray response = IntegerArray.newBuilder()
                    .addAllArray(sortedData)
                    .build();
            os.writeInt(response.getSerializedSize());
            os.write(response.toByteArray());
            os.flush();
            clientTime.finish();
            clientTime.updateClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
