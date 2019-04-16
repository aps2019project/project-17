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

    private Account(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.numbOfWins = 0;
        this.numbOfDraw = 0;
        this.numbOfLose = 0;
        this.darik = 0;
        // TODO: player
        // TODO: collection
        // TODO: decks
        // TODO: matchHistory
    }

    public static String addUser(String userName, String passWord) {

        Account account = new Account(userName, passWord);

        for (Account account1 : accounts) {
            if (account1.userName.equals(account.userName))
                return "UserName Already Exist! Please Try again with another UserName.";
        }
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
                return "your password is wrong! Please Try again";
            }
        }
        return "username is Wrong! Please Try again";
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
                if (o1.userName.compareTo(o2.userName) > 0) {
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

    public void incrementDarik(int darik) {
        this.darik += darik;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void addDecks(Deck deck) {
        this.decks.add(deck);
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

    public void incrementNumbOfWins(int numbOfWins) {
        this.numbOfWins++;
    }

    public int getNumbOfDraw() {
        return numbOfDraw;
    }

    public void incrementNumbOfDraw(int numbOfDraw) {
        this.numbOfDraw++;
    }

    public int getNumbOfLose() {
        return numbOfLose;
    }

    public void incrementNumbOfLose(int numbOfLose) {
        this.numbOfLose++;
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static Account getLoginUser() {
        return loginUser;
    }
}
