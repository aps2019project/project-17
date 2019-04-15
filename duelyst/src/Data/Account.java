package Data;

import effects.*;
import CardCollections.*;
import GameGround.*;

import java.util.ArrayList;
import java.util.Collection;

public class Account {

    private Player player;
    private Collection collection;
    private int darik;
    private ArrayList<Deck> decks = new ArrayList<>();
    private MatchHistory matchHistory;
    private String userName;
    private String passWord;
    private int numbOfWins;
    private int numbOfDraw;
    private int numbOfLose;
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Account loginUser;


    public static void addUser(Account account) {
        accounts.add(account);
    }

    public static void login(String userName, String passWord) {
        for (Account account : accounts) {
            if (account.userName.equals(userName) && account.passWord.equals(passWord)) {
                loginUser = account;
                return;
            }
        }
    }

    public static void logout() {
        loginUser = null;
    }

    public static ArrayList<Account> getLeaderBoard() {
        sortAccounts();
        return accounts;
    }

    private static void sortAccounts() {
        accounts.sort((o1, o2) -> {
            if(o1.numbOfWins > o2.numbOfWins)
                return -1;
            else if(o1.numbOfWins == o2.numbOfWins){
                if(o1.userName. compareTo(o2.userName) > 0){
                    return -1;
                }
            }
            return 0;
        });
    }

    public static Collection getCollection() {
        return getCollection();
        // return is for ignore error
    }

    public ArrayList<Deck> getAllDecks() {
        return getAllDecks();
        // return is for ignore error
    }



}
