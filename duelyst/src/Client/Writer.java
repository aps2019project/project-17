package Client;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;

public class Writer {
    private SocketDetail socketDetail;

    public Writer(SocketDetail socketDetail) {
        this.socketDetail = socketDetail;
    }

    public void write(Object object) {
        try {
            Gson gson = new Gson();
            String data = gson.toJson(object);
            Message message = new Message(data);
            socketDetail.objectOutputStream.writeObject(message);
            socketDetail.objectOutputStream.flush();
            System.err.println("write: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
