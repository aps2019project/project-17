import java.io.IOException;

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
                System.err.println("read: " + object);
                Server.getCommands().put(object);
                Server.getUnProcessedToSocket().put(object, this.socketDetail);
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
