package duelyst.Server;

import Data.Account;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
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
                    System.out.println(socketDetailToReaderWriter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(Server::reading).start();
        new Thread(() -> {
            while (true) {
                process();
            }
        }).start();
        new Thread(Server::writing).start();
    }

    private static void reading() {
        while (true) {
            ArrayList<ReaderWriter> readerWriters = new ArrayList<>(socketDetailToReaderWriter.values());
            for (int i = 0; i < socketDetailToReaderWriter.size(); i++) {
                System.out.println(i);
                ReaderWriter readerWriter = readerWriters.get(i);
                readerWriter.getReader().read();
            }
        }
    }

    private static void process() {
        try {
            if (commands.size() == 0)
                return;
            Object object = commands.take();
            String s = (String) object;
            System.err.println("taken " + object);
            Pattern createAccountPattern = Pattern.compile("create account (?<name>\\w+) (?<pass>\\w+)");
            Matcher createAccountMatcher = createAccountPattern.matcher(s);
            if (createAccountMatcher.matches())
                createAccount(createAccountMatcher.group("name"), createAccountMatcher.group("pass"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void writing() {
        while (true) {
            try {
                Object processedString = processedCommands.take();
                Object onProcessedString = processToOnProcess.get(processedString);
                SocketDetail socketDetail = data.get(onProcessedString);
                ReaderWriter readerWriter = socketDetailToReaderWriter.get(socketDetail);
                readerWriter.getWriter().write((String)processedString);
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
