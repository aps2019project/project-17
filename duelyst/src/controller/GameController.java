package controller;
import CardCollections.*;
import Data.*;
import effects.*;
import view.*;

import java.util.ArrayList;

public class GameController {
    private static View view = View.getInstance();
    private static ArrayList<Account> accounts;

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static void addUser(Account account){
        accounts.add(account);
    }

    public void main() {
        boolean isFinish = false;
        do {
            Request request=new Request();
            request.getNewCommand();
            if (request.getType() == RequestType.EXIT) {
                isFinish = true;
            }
            if (!request.isValid()) {

                continue;
            }
            switch (request.getType()) {

            }
        } while (!isFinish);
    }

    public static String createAccount(String userName, String passWord){
        return Account.addUser(userName, passWord);
    }

    public static String login(String userName, String passWord){
        return Account.login(userName, passWord);
    }

    public static String save(){
        return Account.save();
    }

    public static String logout(){
        return Account.logout();
    }
}
