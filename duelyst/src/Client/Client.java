package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements Runnable {
    private static BlockingQueue<Object> commands = new LinkedBlockingQueue<>();
    private static BlockingQueue<Object> processedCommands = new LinkedBlockingQueue<>();
    private static SocketDetail socketDetail;
    private static ReaderWriter readerWriter;

    public static BlockingQueue<Object> getCommands() {
        return commands;
    }

    public static BlockingQueue<Object> getProcessedCommands() {
        return processedCommands;
    }

    public static void connectToServer() {
        try {
            Socket socket = new Socket(ConnectionDetail.IP, ConnectionDetail.PORT);
            System.out.println(socket.getPort());
            socketDetail = new SocketDetail(socket);
            readerWriter = new ReaderWriter(socketDetail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        connectToServer();
        new Thread(readerWriter.getReader()).start();
        new Thread(() -> writing()).start();
    }

    private static void processing() {
        while (true) {
            try {
                Object unProcessedObject = commands.take();
                //TODO processing
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writing() {
        while (true) {
            try {
                Object object = processedCommands.take();
                System.out.println("taked");
                readerWriter.getWriter().write(object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void send(Object object) {
        try {
            processedCommands.put(object);
            System.out.println("send");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Object get() {
        try {
            return commands.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeSocket() {
        try {
            socketDetail.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
