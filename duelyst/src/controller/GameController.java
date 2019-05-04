package controller;

import CardCollections.*;
import Data.*;
import GameGround.Battle;
import com.google.gson.Gson;
import effects.BuffDetail;
import effects.Hero;
import effects.Item;
import view.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    private static View view = View.getInstance();
    private static ArrayList<Account> accounts;
    private static final String addressOfBuff = "duelyst//src//effects//Buff.json";
    private static final String addressOfHero = "duelyst//src//effects//Hero.json";
    private static final String addressOfHeroBuff = "duelyst//src//effects//HeroBuff.json";
    private static final String addressOfItem = "duelyst//src//effects//addressOfItem.json";
    private static final String addressOfItemBuff = "duelyst//src//effects//addressOfItemBuff.json";
    private static final String addressOfMinoin = "duelyst//src//effects//Minion.json";
    private static final String addressOfMinoinBuff = "duelyst//src//effects//minionBuff.json";
    private static final String addressOfSpell = "duelyst//src//effects//Spell.json";
    private static BuffDetail[] spellBuff;
    private static Hero[] heroes;
    private static BuffDetail[] heroBuff;
    private static Item[] items;

    static {
        accounts = new ArrayList<>();
        try {
            spellBuff = (BuffDetail[]) creatingInstance(InstanceType.BUFF);
            heroes = (Hero[]) creatingInstance(InstanceType.HERO);
            heroBuff = (BuffDetail[]) creatingInstance(InstanceType.HEROBUFF);
            items = (Item[]) creatingInstance(InstanceType.ITEM);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main() {
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

    private static String jsonReader(String fileAddress) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileAddress));
        String line;
        StringBuilder jsonString = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }
        return jsonString.toString();
    }

    private static Object[] creatingInstance(InstanceType instanceType) throws IOException {
        Gson gson = new Gson();
        BuffDetail[] buffDetails;
        switch (instanceType) {
            case BUFF:
                buffDetails = gson.fromJson(jsonReader(addressOfBuff), BuffDetail[].class);
                return buffDetails;
            case HERO:
                Hero[] heroes;
                heroes = gson.fromJson(jsonReader(addressOfHero), Hero[].class);
                return heroes;
            case HEROBUFF:
                buffDetails = gson.fromJson(jsonReader(addressOfHeroBuff), BuffDetail[].class);
                return buffDetails;
            case ITEM:
                Item[] items;
                items = gson.fromJson(jsonReader(addressOfItem), Item[].class);
                return items;
            case ITEMBUFF:
                break;
            case MINION:
                break;
            case MINIONBUFF:
                break;
            case SPELL:
                break;
        }
        return null;
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
        Deck deck = collection.findDeck(deckName);
        if (deck != null) {
            Account.getLoginUser().getPlayer().setMainDeck(deck);
        }
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
        return battle.attack(opponentCardId);
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

    public static String logout() {
        return Account.logout();
    }

    public static String showGameInfo(Battle battle) {
        return battle.showGameInfo().toString();
    }
}
