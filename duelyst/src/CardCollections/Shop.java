package CardCollections;

import Cards.*;
import Client.*;
import Data.Account;
import InstanceMaker.CardMaker;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Shop implements Serializable {

    private Collection collection;

    public Shop(Collection collection) {
        this.collection = collection;
        CardMaker.creation();
    }

    public String search(String cardName) {
        this.collection = Account.getLoginUser().getCollection();
        if (returnCardFromShop(cardName) == null && returnItemFromShop(cardName) == null)
            return "there is not any card\\item in shop with this name";

        if (returnCardFromShop(cardName) != null)
            return returnCardFromShop(cardName).getId();

        return returnItemFromShop(cardName).getId();
    }

    public Card getCardFromId(String cardId) {
        for (Card card : CardMaker.getAllCards()) {
            if (card.getId().equals(cardId)) {
                return card;
            }
        }
        return null;
    }

    public Card getCardFromName(String name) {
        for (Card card : CardMaker.getAllCards()) {
            if (card.getName().toLowerCase().equals(name))
                return card;
        }
        return null;
    }

    public Item getItemFromId(String itemId) {
        for (Item item : CardMaker.getAllItems()) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }

    public Item getItemFromName(String name) {
        for (Item item : CardMaker.getAllItems()) {
            if (item.getName().toLowerCase().equals(name))
                return item;
        }
        return null;
    }


    public String buy(String cardName) {
        this.collection = Account.getLoginUser().getCollection();
        if (returnCardFromShop(cardName) == null && returnItemFromShop(cardName) == null)
            return "this card\\item doesnt exist in shop";

        if (returnCardFromShop(cardName) != null) {

            Card card = returnCardFromShop(cardName);
            if (this.collection.getDaric() < card.getPrice())
                return "you don't have enough money to buy this card";

            Client.send(new Message("buy card " + cardName.trim().toLowerCase()));
            Gson gson = new Gson();
            Message message = gson.fromJson(((Message) Client.get()).getData(), Message.class);
            if (message.getData().trim().equalsIgnoreCase("no"))
                return "server didnt allowed you to buy this card";
            else if (message.getData().trim().equalsIgnoreCase("ok")) {
                this.collection.addCardToCollection(card);
                this.collection.changeDaric(-card.getPrice());
                return "card successfully added";
            }
            return "something wrong happen";
        }

        Item item = returnItemFromShop(cardName);
        if (this.collection.getDaric() < item.getPrice())
            return "you don't have enough money";

        if (this.collection.getItems().size() == 3)
            return "item storage in your collection is full";
        Client.send(new Message("buy item " + cardName.trim().toLowerCase()));
        Gson gson = new Gson();
        Message message = gson.fromJson(((Message) Client.get()).getData(), Message.class);
        if (message.getData().trim().equalsIgnoreCase("no"))
            return "server didnt allow you to buy this item";
        else if (message.getData().trim().equalsIgnoreCase("ok")) {
            this.collection.addItemToCollection(item);
            this.collection.changeDaric(-item.getPrice());
            return "item successfully added";
        }
        return "something wrong happen";
    }

    public String sell(String cardName) {
        this.collection = Account.getLoginUser().getCollection();
        if (returnItemFromCollection(cardName) == null && returnCardFromCollection(cardName) == null)
            return "you dont have this card\\item";

        if (returnCardFromCollection(cardName) != null) {
            Client.send(new Message("sell card " + cardName));
            Card card = returnCardFromCollection(cardName);
            this.collection.changeDaric(card.getPrice());
            this.collection.removeCard(card);
            return "card successfully sell";
        }

        Item item = returnItemFromCollection(cardName);
        Client.send(new Message("sell item " + cardName));
        this.collection.changeDaric(item.getPrice());
        this.collection.getItems().remove(item);
        return "item successfully sell";
    }

    public ArrayList<Item> getItemsInShop() {
        this.collection = Account.getLoginUser().getCollection();
        ArrayList<Item> items = new ArrayList<>();
        Item[] totalItems = CardMaker.getAllItems();
        for (Item totalItem : totalItems) {
            if (returnItemFromCollection(totalItem.getName().trim().toLowerCase()) == null)
                items.add(totalItem);
        }
        return items;
    }

    private Card returnCardFromShop(String cardName) {
        this.collection = Account.getLoginUser().getCollection();
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(CardMaker.getAllCards()));
        Card card = Account.getLoginUser().getCollection().findCard(cardName.trim());
        if (card != null)
            return null;
        for (Card card1 : cards)
            if (card1.getName().trim().equalsIgnoreCase(cardName))
                return card1;
        return null;
    }

    private Item returnItemFromShop(String itemName) {
        this.collection = Account.getLoginUser().getCollection();
        if (Account.getLoginUser().getCollection().findItem(itemName) != null)
            return null;
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(CardMaker.getAllItems()));
        for (Item item : items)
            if (item.getName().trim().equalsIgnoreCase(itemName))
                return item;
        return null;
    }

    private Card returnCardFromCollection(String cardName) {
        this.collection = Account.getLoginUser().getCollection();
        for (int i = 0; i < this.collection.getCards().size(); i++) {
            Card card = this.collection.getCards().get(i);

            if (card.getName().toLowerCase().equals(cardName))
                return card;
            if (card.getId().equals(cardName))
                return card;
        }
        return null;
    }

    private Item returnItemFromCollection(String itemName) {
        this.collection = Account.getLoginUser().getCollection();
        for (int i = 0; i < this.collection.getItems().size(); i++) {
            Item item = this.collection.getItems().get(i);

            if (item.getName().toLowerCase().equals(itemName))
                return item;
        }
        return null;
    }

    public ArrayList<Hero> getShopHeroes() {
        this.collection = Account.getLoginUser().getCollection();
        ArrayList<Hero> shopHeroes = new ArrayList<>();
        for (Card card : CardMaker.getHeroes()) {
            if (card instanceof Hero && returnCardFromCollection(card.getName().trim().toLowerCase()) == null) {
                shopHeroes.add((Hero) card);
            }
        }
        return shopHeroes;
    }

    public ArrayList<Minion> getShopMinions() {
        ArrayList<Minion> shopMinions = new ArrayList<>();
        for (Card card : CardMaker.getMinions()) {
            if (card instanceof Minion && returnCardFromCollection(card.getName().trim().toLowerCase()) == null) {
                if (card instanceof Hero)
                    continue;
                shopMinions.add((Minion) card);
            }
        }
        return shopMinions;
    }

    public ArrayList<Spell> getShopSpells() {
        this.collection = Account.getLoginUser().getCollection();
        ArrayList<Spell> shopSpells = new ArrayList<>();
        for (Card card : CardMaker.getSpells()) {
            if (card instanceof Spell && returnCardFromCollection(card.getName().trim().toLowerCase()) == null) {
                shopSpells.add((Spell) card);
            }
        }
        return shopSpells;
    }

    public void update() {
        CardMaker.creation();
    }

    public Collection getCollection() {
        this.collection = Account.getLoginUser().getCollection();
        return collection;
    }
}
