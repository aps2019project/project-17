package controller;

import CardCollections.*;
import Data.*;
import GameGround.*;
import view.*;

import java.util.ArrayList;

import static Data.MODE.*;

public class GameController {

    private static View view = View.getInstance();
    private static ArrayList<Account> accounts;


    static {
        accounts = new ArrayList<>();
    }

    public static void main() {
        InstanceBuilder.creation();
        boolean isFinish = false;
        do {
            Request.getNewCommand();
            if (Request.getType() == RequestType.EXIT_GAME) {
                isFinish = true;
            }

            if (!Request.isValid()) {
                view.printError(Request.getError());

            }

        } while (!isFinish);
    }

    /**
     * Account
     */

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

    public static String logout() {
        return Account.logout();
    }

    /**
     * Collection
     */

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

    /**
     * Shop
     */

    public static String buy(String cardName, Shop shop) {
        return shop.buy(cardName);
    }

    public static String sell(String cardName, Shop shop) {
        return shop.sell(cardName);
    }

    public static String searchInShop(String cardName, Shop shop) {
        return shop.search(cardName);
    }

    /**
     * Battle
     */

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
        return battle.GraveYard_showInfo(cardID);
    }

    public static String showCardsOfGraveYard(Battle battle) {
        return battle.GraveYard_showCards();
    }

    public static String attackCombo(String opponentCardID, Battle battle, String... cardIDs) {
        return battle.attackCombo(opponentCardID, cardIDs);
    }

    public static void initializeAIStory() {
        AI.initializeAIStory();
    }

    public static void setAiPlayer(MODE mode) {
        switch (mode) {
            case KH:
                AI.setAiPlayer(KH);
                break;
            case HF:
                AI.setAiPlayer(HF);
                break;
            case CF:
                AI.setAiPlayer(CF);
                break;
        }
    }

    public static void createBattleKillHeroSingle(Player player, SinglePlayerModes mode) {//todo :Pooya:check it please
        new BattleKillHero(player, mode);
    }

    public static void createBattleKillHeroMulti(Player playerOne, Player playerTwo) {
        new BattleKillHero(playerOne, playerTwo);
    }

    public static void createBattleHoldingFlagSingle(Player player, SinglePlayerModes mode) {
        new BattleHoldingFlag(player, mode);
    }

    public static void createBattleHoldingFlagMulti(Player playerOne, Player playerTwo) {
        new BattleHoldingFlag(playerOne, playerTwo);
    }

    public static void createBattleCaptureFlagSingle(Player player, int numberOfFlags, SinglePlayerModes mode) {
        new BattleCaptureFlag(player, numberOfFlags, mode);
    }

    public static void createBattleCaptureFlagMulti(Player playerOne, Player playerTwo, int numberOfFlags) {
        new BattleCaptureFlag(playerOne, playerTwo, numberOfFlags);
    }

    public static void createNewAIInstance(String userName, Deck deck) {

        new AI(userName, deck);
    }


}
