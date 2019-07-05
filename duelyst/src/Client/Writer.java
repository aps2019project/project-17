package Client;

import java.io.IOException;
import java.net.Socket;

public class Writer {
    private SocketDetail socketDetail;

    public Writer(SocketDetail socketDetail) {
        this.socketDetail = socketDetail;
    }

    public void write(Object object) {
        try {
            socketDetail.objectOutputStream.writeObject(object);
            socketDetail.objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
