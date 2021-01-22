package ru.itmo.client;

import ru.itmo.protocol.Protocol.IntegerArray;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Client {
    private final String host;
    private final int port;
    private final int x;
    private final int n;
    private final int d;

    //TODO N в сообщеие хз зачем
    public Client(String host, int port, int x, int n, int d) {
        this.host = host;
        this.port = port;
        this.x = x;
        this.n = n;
        this.d = d;
    }

    public void start() {
        List<Integer> data = generateArray();
        try (Socket socket = new Socket(host, port);
             DataOutputStream os = new DataOutputStream(socket.getOutputStream());
             DataInputStream is = new DataInputStream(socket.getInputStream());
        ) {
            for (int i = 0; i < x; i++) {
                // ------------------ send request ----------------
                IntegerArray request = IntegerArray.newBuilder()
                        .addAllArray(data).build();
                os.writeInt(request.getSerializedSize());
                request.writeDelimitedTo(os);
                os.flush();

                // ------------------ get respond ----------------
                is.readInt();
                IntegerArray serverResponse = IntegerArray.parseDelimitedFrom(is);
                System.out.println(serverResponse.getArrayList());

                // ------------------ sleep --------------------
                Thread.sleep(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<Integer> generateArray() {
        List<Integer> data = Arrays.asList(new Integer[n]);
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            data.set(i, r.nextInt());
        }
        return data;
    }

}
