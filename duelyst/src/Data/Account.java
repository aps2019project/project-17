package Data;

import CardCollections.Collection;
import CardCollections.*;
import controller.GameController;

import java.util.ArrayList;

public class Account implements Comparable<Account> {

    private Player player;
    private Collection collection;
    private MatchHistory matchHistory;
    private String userName;
    private String passWord;
    private int numbOfWins;
    private int numbOfDraw;
    private int numbOfLose;
    private int daric;
    private Shop shop;
    private static Account loginUser;

    private Account(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.numbOfWins = 0;
        this.numbOfDraw = 0;
        this.numbOfLose = 0;
        this.daric = 15000;
        this.matchHistory = new MatchHistory();
        this.collection = new Collection();
       this.player = new Player(this.userName);
        Collection.addCollection(this.collection);
        this.shop = new Shop(this.collection);
    }

    public static String addUser(String userName, String passWord) {

        for (Account account : GameController.getAccounts()) {
            if (account.userName.equals(userName))
                return "UserName Already Exist! Please Try again with another UserName.";
        }

        Account account = new Account(userName, passWord);

        GameController.getAccounts().add(account);
        return "Account Successfully created";
    }

    public static String login(String userName, String passWord) {
        for (Account account : GameController.getAccounts()) {
            if (account.userName.equals(userName)) {
                if (account.passWord.equals(passWord)) {
                    loginUser = account;
                    return "login successfully done :) Enjoy the game";
                }
                return "your password is wrong!";
            }
        }
        return "cant find account with this user name";
    }

    public static String logout() {
        loginUser = null;
        return "logout successfully done";
    }

    public Deck getMainDeck() {
        return this.collection.getMainDeck();
    }

    public String save() {
        return "Saved!";
    }

    public int getNumbOfDraw() {
        return numbOfDraw;
    }

    public int getNumbOfLose() {
        return numbOfLose;
    }

    public static ArrayList<Account> getAccounts() {
        return GameController.getAccounts();
    }

    public static Account getLoginUser() {
        return loginUser;
    }

    public Shop getShop() {
        return shop;
    }

    public Player getPlayer() {
        return player;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public int getNumbOfWins() {
        return numbOfWins;
    }

    public static Account getAccount(String userName) {
        for (int i = 0; i < GameController.getAccounts().size(); i++) {
            if (GameController.getAccounts().get(i).getUserName().equals(userName))
                return GameController.getAccounts().get(i);
        }
        return null;
    }

    @Override
    public int compareTo(Account o) {
        Integer firstNumOfWins = this.numbOfWins;
        Integer secondNumOfWins = o.numbOfWins;

        return firstNumOfWins.compareTo(secondNumOfWins);
    }

    public MatchHistory getMatchHistory() {
        return matchHistory;
    }

    public void incrementNumbOfWins() {
        this.numbOfWins++;
    }

    public void incrementNumbOfDraw() {
        this.numbOfDraw++;
    }

    public void incrementNumbOfLose() {
        this.numbOfLose++;
    }

    public Collection getCollection() {
        return this.collection;
    }


    public int getDaric() {
        this.daric = this.collection.getDaric();
        return this.daric;
    }
}
