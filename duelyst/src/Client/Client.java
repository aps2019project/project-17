package Client;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements Runnable{
    private static BlockingQueue<Object> commands = new LinkedBlockingQueue<>();
    private static BlockingQueue<Object> processedCommands = new LinkedBlockingQueue<>();
    private static SocketDetail socketDetail;

    public static BlockingQueue<Object> getCommands() {
        return commands;
    }

    public static BlockingQueue<Object> getProcessedCommands() {
        return processedCommands;
    }

    public static void connectToServer(){
        Socket socket = new Socket();
        socketDetail = new SocketDetail(socket);
    }

    @Override
    public void run() {
        connectToServer();
        while (true) {
            Object object =
        }
    }
}
