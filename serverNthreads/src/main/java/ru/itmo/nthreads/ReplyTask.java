package ru.itmo.nthreads;

import ru.itmo.protocol.Protocol;
import ru.itmo.protocol.Protocol.IntegerArray;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class ReplyTask implements Runnable {
    private final Socket socket;
    private final List<Integer> sortedData;

    public ReplyTask(Socket socket, List<Integer> sortedData) {
        this.socket = socket;
        this.sortedData = sortedData;
    }

    @Override
    public void run() {
        try (DataOutputStream os = new DataOutputStream(socket.getOutputStream())) {
            IntegerArray response = IntegerArray.newBuilder()
                    .addAllArray(sortedData)
                    .build();
            os.writeInt(response.getSerializedSize());
            response.writeTo(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
