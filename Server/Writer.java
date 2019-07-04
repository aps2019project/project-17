package Server;

import java.io.IOException;
import java.net.Socket;

public class Writer {
    private SocketDetail socketDetail;

    public Writer(Socket socket) {
        this.socketDetail = new SocketDetail(socket);
    }

    public void write(String string) {
        try {
            socketDetail.dataOutputStream.writeUTF(string);
            socketDetail.dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
