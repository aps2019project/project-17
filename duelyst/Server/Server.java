import Data.Account;
import com.google.gson.Gson;
import io.joshworks.restclient.http.Unirest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Server {
    private static Map<Account, ReaderWriter> accountToReaderWriter = new ConcurrentHashMap<>();
    private static Map<Object, SocketDetail> unProcessedToSocket = new ConcurrentHashMap<>();
    private static Map<SocketDetail, ReaderWriter> socketDetailToReaderWriter = new ConcurrentHashMap<>();
    private static Map<ReaderWriter, Account> ReaderWriterToAccount = new ConcurrentHashMap<>();
    private static Map<Object, Object> processedToUnProcess = new ConcurrentHashMap<>();
    private static BlockingQueue<Object> commands = new LinkedBlockingQueue<>();
    private static BlockingQueue<Object> processedCommands = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws IOException {
        for (String s : getAllUserName()) {
            System.out.println(s);
        }
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
            Object processedObject = null;
            System.err.println("taken " + unProcessedObject);
            String message;
            if (unProcessedObject instanceof Message) {
                message = ((Message) unProcessedObject).getData().trim();
                Pattern createAccountPattern = Pattern.compile("create account (?<name>\\w+) (?<pass>\\w+)");
                Matcher createMatcher = createAccountPattern.matcher(message);
                Pattern loginAccountPattern = Pattern.compile("login (?<name>\\w+) (?<pass>\\w+)");
                Matcher loginMatcher = loginAccountPattern.matcher(message);
                if (createMatcher.matches()) {
                    String userName = createMatcher.group("name");
                    if (getAllUserName().contains(userName))
                        processedObject = new Message("this user name already exist");
                    else {
                        String pass = createMatcher.group("pass");
                        Account account = new Account(userName, pass);
                        addUserToDataBase(account);
                        processedObject = new Message("account successfully created");
                    }
                } else if (loginMatcher.matches()) {
                    String userName = loginMatcher.group("name");
                    Account account = getAccount(userName);
                    if (account == null)
                        processedObject = new Message("invalid user name");
                    else {
                        processedObject = account;
                    }

                } else
                    processedObject = new Message("invalid command");
            }
            System.err.println("processed  " + processedObject);
            assert processedObject != null;
            processedCommands.put(processedObject);
            processedToUnProcess.put(processedObject, unProcessedObject);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void writing() {
        while (true) {
            try {
                Object processedObject = processedCommands.take();
                Object unProcessedObject = processedToUnProcess.get(processedObject);
                SocketDetail socketDetail;
                socketDetail = unProcessedToSocket.get(unProcessedObject);
                ReaderWriter readerWriter = socketDetailToReaderWriter.get(socketDetail);
                readerWriter.getWriter().write(processedObject);
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

    private static ArrayList<String> getAllUserName() {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("name", ConnectionDataBaseDetail.ACCOUNT_DB);
        String result = Unirest.post(ConnectionDataBaseDetail.GET_ALL_KEYS).fields(map).asObject(String.class).getBody();
        return new ArrayList<>(Arrays.asList(gson.fromJson(result, String[].class)));
    }

    private static Account[] getAllAccounts() {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("name", ConnectionDataBaseDetail.ACCOUNT_DB);
        String result = Unirest.post(ConnectionDataBaseDetail.GET_ALL_VALUES).fields(map).asObject(String.class).getBody();
        System.out.println(result);
        return gson.fromJson(result, Account[].class);
    }

    private static void addUserToDataBase(Account account) {
        Gson gson = new Gson();
        String userName = account.getUserName();
        String json = gson.toJson(account);
        Map<String, Object> map = new HashMap<>();
        map.put("name", ConnectionDataBaseDetail.ACCOUNT_DB);
        map.put("key", userName);
        map.put("value", json);
        System.out.println(Unirest.post(ConnectionDataBaseDetail.PUT).fields(map).asString().getStatus());
    }

    private static Account getAccount(String userName) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("name", ConnectionDataBaseDetail.ACCOUNT_DB);
        map.put("key", userName);
        String result = Unirest.post(ConnectionDataBaseDetail.GET).fields(map).asString().getBody();
        if (Unirest.post(ConnectionDataBaseDetail.GET).fields(map).asString().getStatus() != 200)
            return null;
        return gson.fromJson(result, Account.class);
    }

    private static void deleteAccount(String userName) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", ConnectionDataBaseDetail.ACCOUNT_DB);
        map.put("key", userName);
        System.out.println(Unirest.post(ConnectionDataBaseDetail.DEL).fields(map).asString().getStatus());
    }
}
