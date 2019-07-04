package Server;

import java.io.IOException;
import java.net.Socket;

public class Reader {
    private SocketDetail socketDetail;

    public Reader(Socket socket) {
        socketDetail = new SocketDetail(socket);
    }

    public void read() {
        try {
            String string = socketDetail.dataInputStream.readUTF();
            if (!string.equals("")) {
                Server.getCommands().put(string);
                Server.getData().put(string, this.socketDetail);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
