package duelyst.Server;

import Data.Account;
import io.joshworks.restclient.http.Unirest;
import io.joshworks.restclient.request.GetRequest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class Server {
    private static Map<Account, ReaderWriter> accountToReaderWriter = new ConcurrentHashMap<>();
    private static Map<Object, SocketDetail> unProcessedToSocket = new ConcurrentHashMap<>();
    private static Map<SocketDetail, ReaderWriter> socketDetailToReaderWriter = new ConcurrentHashMap<>();
    private static Map<ReaderWriter, Account> ReaderWriterToAccount = new ConcurrentHashMap<>();
    private static Map<Object, Object> processedToUnProcess = new ConcurrentHashMap<>();
    private static BlockingQueue<Object> commands = new LinkedBlockingQueue<>();
    private static BlockingQueue<Object> processedCommands = new LinkedBlockingQueue<>();

    private static GetRequest getRequest;

    private static GetRequest getRequest1;

    static {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "data_base");
        System.out.println(Unirest.post(ConnectionDataBaseDetail.INIT).fields(map).asString().getStatus());
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(ConnectionDataBaseDetail.PORT);
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketDetail socketDetail = new SocketDetail(socket);
                    ReaderWriter readerWriter = new ReaderWriter(socketDetail);
                    socketDetailToReaderWriter.put(socketDetail, readerWriter);
                    System.err.println("socket connected");
                    readerWriter.getReader().run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                process();
            }
        }).start();
        new Thread(Server::writing).start();
    }


    private static void process() {
        try {
            if (commands.size() == 0)
                return;
            Object unProcessedObject = commands.take();
            System.err.println("taken " + unProcessedObject);
            //TODO processing
            Object processedObject = null;
            System.err.println("processed  " + processedObject);
            processedCommands.put(processedObject);
            processedToUnProcess.put(processedObject, unProcessedObject);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void writing() {
        while (true) {
            try {
                Object processedString = processedCommands.take();
                Object onProcessedString = processedToUnProcess.get(processedString);
                SocketDetail socketDetail;
                if (unProcessedToSocket.containsKey(processedString))
                    System.out.println("ok");
                socketDetail = unProcessedToSocket.get(onProcessedString);
                ReaderWriter readerWriter = socketDetailToReaderWriter.get(socketDetail);
                readerWriter.getWriter().write(processedString);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<Account, ReaderWriter> getAccountToReaderWriter() {
        return accountToReaderWriter;
    }

    public static BlockingQueue<Object> getCommands() {
        return commands;
    }


    public static Map<Object, SocketDetail> getUnProcessedToSocket() {
        return unProcessedToSocket;
    }

    public static Map<ReaderWriter, Account> getReaderWriterToAccount() {
        return ReaderWriterToAccount;
    }

    private static void createAccount(String name, String pass) {
        System.out.println("account created with " + name + " - " + pass);
    }
}
