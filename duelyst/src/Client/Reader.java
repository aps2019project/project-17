package Client;

import java.io.IOException;

import static Client.Client.getCommands;

public class Reader implements Runnable {
    private SocketDetail socketDetail;

    public Reader(SocketDetail socketDetail) {
        this.socketDetail = socketDetail;
    }

    private void read() {
        try {
            Object object;
            object = socketDetail.objectInputStream.readObject();
            if (object != null && !object.equals("")) {
                System.err.println("read " + object);
                getCommands().put(object);
            }
        } catch (InterruptedException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        read();
    }
}
