package duelyst.Server;

import Data.Account;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Server {
    private static Map<Account, ReaderWriter> accountToReaderWriter = new ConcurrentHashMap<>();
    private static Map<Object, SocketDetail> data = new ConcurrentHashMap<>();
    private static Map<SocketDetail, ReaderWriter> socketDetailToReaderWriter = new ConcurrentHashMap<>();
    private static Map<ReaderWriter, Account> ReaderWriterToAccount = new ConcurrentHashMap<>();
    private static Map<Object, Object> processToOnProcess = new ConcurrentHashMap<>();
    private static BlockingQueue<Object> commands = new LinkedBlockingQueue<>();
    private static BlockingQueue<Object> processedCommands = new LinkedBlockingQueue<>();


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
            Object object = commands.take();
            String s = ((String) ((FakeClient.Message) object).object).trim();
            System.out.println(s);
            System.err.println("taken " + object);
            Pattern createAccountPattern = Pattern.compile("create account (?<name>\\w+) (?<pass>\\w+)");
            Matcher createAccountMatcher = createAccountPattern.matcher(s);
            if (createAccountMatcher.matches())
                createAccount(createAccountMatcher.group("name"), createAccountMatcher.group("pass"));
            String s1 = "done";
            FakeClient.Message message = new FakeClient.Message(s1);
            processedCommands.put(message);
            processToOnProcess.put( message,object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void writing() {
        while (true) {
            try {
                Object processedString = processedCommands.take();
                Object onProcessedString = processToOnProcess.get(processedString);
                SocketDetail socketDetail;
                if (data.containsKey(processedString))
                    System.out.println("ok");
                socketDetail = data.get(onProcessedString);
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


    public static Map<Object, SocketDetail> getData() {
        return data;
    }

    public static Map<ReaderWriter, Account> getReaderWriterToAccount() {
        return ReaderWriterToAccount;
    }

    private static void createAccount(String name, String pass) {
        System.out.println("account created with " + name + " - " + pass);
    }
}
