package controller;

import CardCollections.*;
import Data.*;
import GameGround.Battle;
import com.google.gson.Gson;
import effects.*;
import view.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    private static View view = View.getInstance();
    private static ArrayList<Account> accounts;


    static {
        accounts = new ArrayList<>();
    }

    public static void main() {
        InstanceBuilder.creation();
        Request request = new Request();
        boolean isFinish = false;
        do {
            request.getNewCommand();
            if (request.getType() == RequestType.EXIT_GAME) {
                isFinish = true;
            }

            if (!request.isValid()) {
                view.printError(request.getError());

            }

        } while (!isFinish);
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static String createAccount(String userName, String passWord) {
        return Account.addUser(userName, passWord);
    }

    public static String login(String userName, String passWord) {
        return Account.login(userName, passWord);
    }

    public static boolean checkForValidUserName(String userName) {
        return Account.checkForValidUserName(userName);
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

    public static String buy(String cardName, Shop shop) {
        return shop.buy(cardName);
    }

    public static String sell(String cardName, Shop shop) {
        return shop.sell(cardName);
    }

    public static String movingCard(int x, int y, Battle battle) {
        return battle.movingCard(x, y);
    }

    public static String attack(String opponentCardId, Battle battle) {
        return battle.attack(opponentCardId, false, null);
    }

    public static String insertCard(String cardName, int x, int y, Battle battle) {
        return battle.insertingCardFromHand(cardName, x, y);
    }

    public static void endTurn(Battle battle) {
        battle.endTurn();
    }

    public static String searchInShop(String cardName, Shop shop) {
        return shop.search(cardName);
    }

    public static Account getAccount(String userName) {
        return Account.getAccount(userName);
    }

    public static String useSpecialPower(int x, int y, Battle battle) {
        return battle.useSpecialPower(x, y);
    }

    public static String selectCardOrItem(String cardItemID, Battle battle) {
        return battle.selectCardOrItem(cardItemID);
    }

    public static String showCardInfoFromGraveYard(String cardID, Battle battle) {
        return battle.showCardInfoFromGraveYard(cardID);
    }

    public static String showCardsOfGraveYard(Battle battle) {
        return battle.showCardsOfGraveYard();
    }

    public static String attackCombo(String opponentCardID, Battle battle, String... cardIDs) {
        return battle.attackCombo(opponentCardID, cardIDs);
    }

    public static String logout() {
        return Account.logout();
    }

    public static String showGameInfo(Battle battle) {
        return battle.showGameInfo().toString();
    }

    public static String selectCard(String cardID) {
        return Battle.getCurrentBattle().selectCardOrItem(cardID);
    }
}
