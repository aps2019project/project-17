package Data;

import effects.*;
import CardCollections.Collection;
import CardCollections.*;
import GameGround.*;

import java.util.ArrayList;

public class Account implements Comparable<Account> {

    private Player player;
    private Collection collection;
    private int darik;
    private MatchHistory matchHistory;
    private String userName;
    private String passWord;
    private int numbOfWins;
    private int numbOfDraw;
    private int numbOfLose;
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Account loginUser;

    private Account(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.numbOfWins = 0;
        this.numbOfDraw = 0;
        this.numbOfLose = 0;
        this.darik = 15000;
        this.matchHistory = new MatchHistory();
        this.player = new Player();
        this.collection = new Collection();
    }

    public static String addUser(String userName, String passWord) {

        for (Account account : accounts) {
            if (account.userName.equals(userName))
                return "UserName Already Exist! Please Try again with another UserName.";
        }

        Account account = new Account(userName, passWord);

        accounts.add(account);
        return "Account Successfully created";
    }

    public static String login(String userName, String passWord) {
        for (Account account : accounts) {
            if (account.userName.equals(userName)) {
                if (account.passWord.equals(passWord)) {
                    loginUser = account;
                    return "login successfully done :) Enjoy the game";
                }
                return "your password is wrong!";
            }
        }
        return "username doesnt exist!";
    }

    public static String logout() {
        loginUser = null;
        return "logout successfully done";
    }

    public static ArrayList<Account> getLeaderBoard() {
        sortAccounts();
        return accounts;
    }

    private static void sortAccounts() {
        accounts.sort((o1, o2) -> {
            if (o1.numbOfWins > o2.numbOfWins)
                return -1;
            else if (o1.numbOfWins == o2.numbOfWins) {
                return o1.userName.compareTo(o2.userName);
            }
            return 1;
        });
    }

    public static Collection getCollection() {
        return getCollection();
        // return is for ignore error
    }

    public String setMainDeck(String deckName) {
        Deck deck = this.collection.findDeck(deckName);

        if (deck == null) {
            return "cannot find deck with this name";
        }

        this.player.setMainDeck(deck);
        this.collection.setMainDeck(deck);
        return "set main deck successfully done";
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public int getDarik() {
        return darik;
    }

    public void incrementDarik(int change) {
        this.darik += change;
    }

    public void decrementDarik(int change) {
        this.darik -= change;
    }

    public MatchHistory getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(GameData gameData) {
        this.matchHistory.addGameData(gameData);
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

    public void incrementNumbOfWins() {
        this.numbOfWins++;
    }

    public int getNumbOfDraw() {
        return numbOfDraw;
    }

    public void incrementNumbOfDraw() {
        this.numbOfDraw++;
    }

    public int getNumbOfLose() {
        return numbOfLose;
    }

    public void incrementNumbOfLose() {
        this.numbOfLose++;
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static Account getLoginUser() {
        return loginUser;
    }

    public static String save() {
        return "Saved!";
    }

    /**
     * for comparing accounts according to their num of wins in increasing order
     */
    @Override
    public int compareTo(Account o) {
        Integer firstNumOfWins = this.numbOfWins;
        Integer secondNumOfWins = o.numbOfWins;

        return firstNumOfWins.compareTo(secondNumOfWins);
    }
}
