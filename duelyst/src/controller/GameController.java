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

    public static void addUser(Account account) {
        accounts.add(account);
    }

    public static void main() {
        boolean isFinish = false;
        do {
            Request request = new Request();
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

    public static String createAccount(String userName, String passWord) {
        return Account.addUser(userName, passWord);
    }

    public static String login(String userName, String passWord) {
        return Account.login(userName, passWord);
    }

    public static String accountSave(Account account) {
        return account.save();
    }

    public static String collectionSave(Collection collection) {
        return collection.save();
    }

    public static String search(String name, Collection collection) {
        return collection.search(name);
    }

    public static String createDeck(String name, Collection collection) {
        return collection.createDeck(name);
    }

    public static String deleteDeck(String name, Collection collection) {
        return collection.deleteDeck(name);
    }

    public static String addToDeck(String cardID, String deckName, Collection collection) {
        return collection.addToDeck(cardID, deckName);
    }

    public static String removeFromDeck(String cardID, String deckName, Collection collection) {
        return collection.removeFromDeck(cardID, deckName);
    }

    public static String isDeckValidate(String deckName, Collection collection) {
        return collection.isDeckValidate(deckName);
    }

    public static String setMainDeck(String deckName, Collection collection) {
        return collection.setMainDeck(deckName);
    }

    public static String buy(String cardName,Shop shop){
        return shop.buy(cardName);
    }

    public static String sell(String cardName,Shop shop){
        return shop.sell(cardName);
    }

    public static String searchInShop(String cardName,Shop shop){
        return shop.search(cardName);
    }

    public static String logout() {
        return Account.logout();
    }
}
