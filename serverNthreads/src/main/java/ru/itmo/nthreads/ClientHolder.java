package ru.itmo.nthreads;

import ru.itmo.protocol.Protocol.IntegerArray;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ClientHolder implements Runnable {
    private final Socket socket;
    private final Executor executor;
    private final Executor replier;


    public ClientHolder(Socket socket, Executor executor) {
        this.socket = socket;
        this.executor = executor;
        this.replier = Executors.newSingleThreadExecutor();
    }


    @Override
    public void run() {
        try (DataInputStream is = new DataInputStream(socket.getInputStream())) {
            while (true) {
                int messageSize = is.readInt();
                IntegerArray clientData = IntegerArray.parseFrom(is);
                executor.execute(new ServerTask(clientData.getArrayList()));

            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
