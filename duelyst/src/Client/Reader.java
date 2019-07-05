package Client;

import Data.Account;
import com.google.gson.Gson;

import java.io.IOException;

import static Client.Client.getCommands;

public class Reader implements Runnable {
    private SocketDetail socketDetail;

    public Reader(SocketDetail socketDetail) {
        this.socketDetail = socketDetail;
    }

    private void read() {
        try {
            Gson gson = new Gson();
            String string;
            string = (String) socketDetail.objectInputStream.readObject();
            if (string == null || string.equals(""))
                return;
            try {
                Message message = gson.fromJson(string, Message.class);
                System.out.println(message);
                if (message != null) {
                    getCommands().put(message);
                    System.err.println("read " + string);
                    return;
                }
                Account account = gson.fromJson(string, Account.class);
                System.out.println(account);
                if (account != null) {
                    getCommands().put(account);
                    System.err.println("read " + string);
                    return;
                }
                System.err.println("read " + string);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true)
            read();
    }
}
