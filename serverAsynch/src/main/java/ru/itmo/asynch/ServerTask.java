package ru.itmo.asynch;

import com.google.protobuf.InvalidProtocolBufferException;
import ru.itmo.protocol.Protocol;
import ru.itmo.protocol.Protocol.IntegerArray;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Collections;
import java.util.List;

public class ServerTask implements Runnable {
    private static final int INT_SIZE = 4;

    private final ByteBuffer data;
    private final AsynchronousSocketChannel client;
    private final RespondContext respondContext;

    private static void insertionSort(List<Integer> array) {
        for (int i = 0; i < array.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (array.get(j) >= array.get(j - 1)) break;
                Collections.swap(array, j, j-1);
            }
        }
    }

    ServerTask(AsynchronousSocketChannel client, ByteBuffer bytes, RespondContext respondContext) {
        this.data = bytes;
        this.client = client;
        this.respondContext = respondContext;
    }

    @Override
    public void run() {
        try {
            List<Integer> array = IntegerArray.parseFrom(data).getArrayList();
            insertionSort(array);
            IntegerArray response = IntegerArray.newBuilder().addAllArray(array).build();

            ByteBuffer buf = ByteBuffer.allocate(INT_SIZE + response.getSerializedSize());
            buf.putInt(response.getSerializedSize());
            buf.put(response.toByteArray());

            respondContext.setBuffer(buf);
            //  Контекст не нужен тут особо
            client.write(respondContext.getData(), respondContext, new CompletionHandler<>() {
                @Override
                public void completed(Integer result, RespondContext attachment) {
                    if (!attachment.isFinished()) {
                        client.write(attachment.getData(), attachment, this);
                    }
                }

                @Override
                public void failed(Throwable exc, RespondContext attachment) {
                    exc.printStackTrace();
                }
            });

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

}
