package ru.itmo.blocking;
import ru.itmo.protocol.Protocol;
import ru.itmo.protocol.Protocol.IntegerArray;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class ReplyTask implements Runnable {
    private final DataOutputStream os;
    private final List<Integer> sortedData;

    public ReplyTask(DataOutputStream os, List<Integer> sortedData) {
        this.os = os;
        this.sortedData = sortedData;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
