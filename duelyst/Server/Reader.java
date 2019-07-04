package duelyst.Server;

import java.io.IOException;
import java.net.Socket;

public class Reader {
    private SocketDetail socketDetail;

    public Reader(Socket socket) {
        socketDetail = new SocketDetail(socket);
    }

    public void read() {
        try {
            Object object;
            object = socketDetail.objectInputStream.readObject();
            if (object != null && !object.equals("")) {
                System.err.println("read " + object);
                Server.getCommands().put(object);
                Server.getData().put(object, this.socketDetail);
                System.out.println("out");
            }
        } catch (InterruptedException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
