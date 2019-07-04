package duelyst.Server;

import java.io.IOException;
import java.net.Socket;

public class Writer {
    private SocketDetail socketDetail;

    public Writer(Socket socket) {
        this.socketDetail = new SocketDetail(socket);
    }

    public void write(String string) {
        try {
            socketDetail.objectOutputStream.writeObject(string);
            socketDetail.objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
