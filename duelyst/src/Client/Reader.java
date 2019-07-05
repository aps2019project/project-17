package Client;

import com.google.gson.Gson;

import java.io.IOException;

import static Client.Client.getCommands;

public class Reader implements Runnable {
    private SocketDetail socketDetail;

    public Reader(SocketDetail socketDetail) {
        this.socketDetail = socketDetail;
    }

    private void read() {
        try {
            Gson gson = new Gson();
            String data = (String) socketDetail.objectInputStream.readObject();
            Message message = gson.fromJson(data, Message.class);
            getCommands().put(message);
            System.err.println("read " + data);
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true)
            read();
    }
}
