package Server;

import Data.Account;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Server {
    private static Map<Account, ReaderWriter> accountToReaderWriter = new HashMap<>();
    private static Map<String, SocketDetail> data = new HashMap<>();
    private static Map<SocketDetail, ReaderWriter> socketDetailToReaderWriter = new HashMap<>();
    private static Map<ReaderWriter, Account> ReaderWriterToAccount = new HashMap<>();
    private static Map<String, String> processToOnProcess = new HashMap<>();
    private static BlockingQueue<String> commands = new LinkedBlockingQueue<>();
    private static BlockingQueue<String> processedCommands = new LinkedBlockingQueue<>();


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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> reading()).start();
        new Thread(() -> {
            while (true) {
                process();
            }
        });
        new Thread(() -> writing()).start();
    }

    private static void reading() {
        while (true) {
            ArrayList<ReaderWriter> readerWriters = new ArrayList<>(socketDetailToReaderWriter.values());
            for (int i = 0; i < readerWriters.size(); i++) {
                readerWriters.get(i).getReader().read();
                System.err.println(commands);
            }
        }
    }

    private static void process() {
        try {
            String string = commands.take();
            Pattern createAccountPattern = Pattern.compile("create account (?<name>\\w+) (?<pass>\\w+)");
            Matcher createAccountMatcher = createAccountPattern.matcher(string);
            if (createAccountMatcher.matches())
                createAccount(createAccountMatcher.group("name"), createAccountMatcher.group("pass"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void writing() {
        while (true) {
            try {
                String processedString = processedCommands.take();
                String onProcessedString = processToOnProcess.get(processedString);
                SocketDetail socketDetail = data.get(onProcessedString);
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

    public static BlockingQueue<String> getCommands() {
        return commands;
    }


    public static Map<String, SocketDetail> getData() {
        return data;
    }

    public static Map<ReaderWriter, Account> getReaderWriterToAccount() {
        return ReaderWriterToAccount;
    }

    private static void createAccount(String name, String pass) {
        System.out.println("account created with " + name + " - " + pass);
    }
}
