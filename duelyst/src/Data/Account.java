package Data;

import CardCollections.Collection;
import CardCollections.*;
import controller.GameController;

public class Account implements Comparable<Account> {

    private Player player;
    private Collection collection;
    private MatchHistory matchHistory;
    private String userName;
    private String passWord;
    private int numOfWins;
    private int numOfLose;
    private int daric;
    private Shop shop;
    private static Account loginUser;

    public Account(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.numOfWins = 0;
        this.numOfLose = 0;
        this.daric = 15000000;
        this.matchHistory = new MatchHistory();
        this.collection = new Collection();
        this.player = new Player(this.userName);
//        this.shop = new Shop(this.collection);
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

    public static boolean checkForValidUserName(String userName) {
        for (Account account : GameController.getAccounts()) {
            if (account.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public void addGamaData(GameData gameData) {
        this.matchHistory.addGameData(gameData);
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


    public static Account getAccount(String userName) {
        for (int i = 0; i < GameController.getAccounts().size(); i++) {
            if (GameController.getAccounts().get(i).getUserName().equals(userName))
                return GameController.getAccounts().get(i);
        }
        return null;
    }

    @Override
    public int compareTo(Account o) {
        Integer firstNumOfWins = this.numOfWins;
        Integer secondNumOfWins = o.numOfWins;

        return firstNumOfWins.compareTo(secondNumOfWins);
    }

    public void incrementNumbOfWins() {
        this.numOfWins++;
    }

    public void incrementNumbOfLose() {
        this.numOfLose++;
    }

    public Collection getCollection() {
        return this.collection;
    }


    public int getDaric() {
        this.daric = this.collection.getDaric();
        return this.daric;
    }

    public void changeDaric(int value) {
        this.daric += value;
    }

    public int getNumOfLose() {
        return numOfLose;
    }

    public int getNumOfWins() {
        return numOfWins;
    }
}
