package ru.itmo.blocking;

import ru.itmo.protocol.Protocol.IntegerArray;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientHolder implements Runnable {
    private final Socket socket;
    private final ExecutorService executor;
    private final Executor replier;

    public ClientHolder(Socket socket, ExecutorService executor) {
        this.socket = socket;
        this.executor = executor;
        this.replier = Executors.newSingleThreadExecutor();
    }

    @Override
    public void run() {
        try (DataInputStream is = new DataInputStream(socket.getInputStream());
             DataOutputStream os = new DataOutputStream(socket.getOutputStream())
        ) {
            while (true) {
                int messageSize = is.readInt();

                byte[] buffer = new byte[messageSize];
                int received = 0;
                while (received != messageSize) {
                    received += is.read(buffer);
                }

                IntegerArray clientData = IntegerArray.parseFrom(buffer);
                List<Integer> copy = new ArrayList<>(clientData.getArrayList());
                Future<List<Integer>> future = executor.submit(new ServerTask(copy));
                replier.execute(new ReplyTask(os, future.get()));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
