package Client;

import com.google.gson.Gson;

import java.io.EOFException;
import java.io.IOException;

import static Client.Client.getCommands;

public class Reader implements Runnable {
    private SocketDetail socketDetail;
    private boolean condition = true;

    public Reader(SocketDetail socketDetail) {
        this.socketDetail = socketDetail;
    }

    private void read() {
        try {
            Gson gson = new Gson();
            String data = (String) socketDetail.objectInputStream.readObject();
            System.err.println("read " + data);
            Message message = gson.fromJson(data, Message.class);
            getCommands().put(message);
        }catch (EOFException e) {
            try {
                socketDetail.socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (condition)
            read();
    }

    public void setConditionFalse() {
        this.condition = false;
    }
}
