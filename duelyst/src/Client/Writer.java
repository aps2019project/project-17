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
            String string = gson.toJson(object);
            socketDetail.objectOutputStream.writeObject(string);
            socketDetail.objectOutputStream.flush();
            System.err.println("write: " + string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
