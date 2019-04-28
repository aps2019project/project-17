package controller;
import CardCollections.*;
import Data.*;
import effects.*;
import view.*;

import java.util.ArrayList;

public class GameController {
    private View view = View.getInstance();

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
