package ru.itmo.blocking;

import ru.itmo.protocol.Protocol.IntegerArray;
import ru.itmo.protocol.ServerStat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
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
    private final ServerStat statistic;

    private final ArrayList<Long> sorting = new ArrayList<>();
    private final ArrayList<Long> clientOnServer = new ArrayList<>();
    private final ArrayList<Long> client = new ArrayList<>();


    public ClientHolder(Socket socket, ExecutorService executor, ServerStat statistic) {
        this.socket = socket;
        this.executor = executor;
        this.replier = Executors.newSingleThreadExecutor();
        this.statistic = statistic;
    }

    @Override
    public void run() {
        try (DataInputStream is = new DataInputStream(socket.getInputStream());
             DataOutputStream os = new DataOutputStream(socket.getOutputStream())
        ) {
            while (true) {
                byte[] sizeBuffer = new byte[4];
                int receivedSize = 0;
                while (receivedSize != 4) {
                    receivedSize += is.read(sizeBuffer);
                }
                int messageSize = ByteBuffer.wrap(sizeBuffer).getInt();

                byte[] buffer = new byte[messageSize];
                int received = 0;
                while (received != messageSize) {
                    received += is.read(buffer);
                }

                IntegerArray clientData = IntegerArray.parseFrom(buffer);
                List<Integer> copy = new ArrayList<>(clientData.getArrayList());
                ServerStat.ClientStat clientTime = statistic.getNewClientStat();
                clientTime.start();
                Future<List<Integer>> future = executor.submit(new ServerTask(copy, statistic.getNewSortStat()));
                replier.execute(new ReplyTask(os, future.get(), clientTime));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
