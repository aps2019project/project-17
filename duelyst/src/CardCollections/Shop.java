package CardCollections;

import Cards.*;
import InstanceMaker.CardMaker;

import java.util.ArrayList;
import java.util.Arrays;

public class Shop {

    private ArrayList<Card> cardsInShop;
    private ArrayList<Item> itemsInShop;
    private Collection collection;

    public Shop(Collection collection) {
        this.collection = collection;
        this.cardsInShop = new ArrayList<>();
        this.itemsInShop = new ArrayList<>();
        this.initShop();
    }

    private void initShop() {
        CardMaker.creation();
        cardsInShop.addAll(Arrays.asList(CardMaker.getSpells()));
        cardsInShop.addAll(Arrays.asList(CardMaker.getHeroes()));
        cardsInShop.addAll(Arrays.asList(CardMaker.getMinions()));
        itemsInShop.addAll(Arrays.asList(CardMaker.getItems()));
    }

    public String search(String cardName) {
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
            if (card.getName().equals(name))
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
            if (item.getName().equals(name))
                return item;
        }
        return null;
    }


    public String buy(String cardName) {
        if (returnCardFromShop(cardName) == null && returnItemFromShop(cardName) == null)
            return "this card\\item doesnt exist in shop";

        if (returnCardFromShop(cardName) != null) {

            Card card = returnCardFromShop(cardName);
            if (this.collection.getDaric() < card.getPrice())
                return "you don't have enough money to buy this card";

            this.collection.addCardToCollection(card);
            this.cardsInShop.remove(card);
            this.collection.changeDaric(-card.getPrice());
            return "card successfully added";
        }

        Item item = returnItemFromShop(cardName);
        if (this.collection.getDaric() < item.getPrice())
            return "you don't have enough money";

        if (this.collection.getItems().size() == 3)
            return "item storage in your collection is full";

        this.collection.addItemToCollection(item);
        this.itemsInShop.remove(item);
        this.collection.changeDaric(-item.getPrice());
        return "item successfully added";
    }

    public String sell(String cardName) {
        if (returnItemFromCollection(cardName) == null && returnCardFromCollection(cardName) == null)
            return "you dont have this card\\item";

        if (returnCardFromCollection(cardName) != null) {
            Card card = returnCardFromCollection(cardName);

            cardsInShop.add(card);
            this.collection.changeDaric(card.getPrice());
            this.collection.removeCard(card);
            return "card successfully sell";
        }

        Item item = returnItemFromCollection(cardName);
        itemsInShop.add(item);
        this.collection.changeDaric(item.getPrice());
        this.collection.getItems().remove(item);
        return "item successfully sell";
    }

    public ArrayList<Item> getItemsInShop() {
        return itemsInShop;
    }

    private Card returnCardFromShop(String cardName) {
        for (Card card : cardsInShop) {
            if (card.getName().toLowerCase().equals(cardName))
                return card;
        }
        return null;
    }

    private Item returnItemFromShop(String itemName) {
        for (Item item : itemsInShop) {
            if (item.getName().toLowerCase().equals(itemName))
                return item;
        }
        return null;
    }

    private Card returnCardFromCollection(String cardName) {
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
        for (int i = 0; i < this.collection.getItems().size(); i++) {
            Item item = this.collection.getItems().get(i);

            if (item.getName().toLowerCase().equals(itemName))
                return item;
        }
        return null;
    }

    public ArrayList<Hero> getShopHeroes() {
        ArrayList<Hero> shopHeroes = new ArrayList<>();
        for (Card card : this.cardsInShop) {
            if (card instanceof Hero) {
                shopHeroes.add((Hero) card);
            }
        }
        return shopHeroes;
    }

    public ArrayList<Minion> getShopMinions() {
        ArrayList<Minion> shopMinions = new ArrayList<>();
        for (Card card : this.cardsInShop) {
            if (card instanceof Minion) {
                if (card instanceof Hero)
                    continue;
                shopMinions.add((Minion) card);
            }
        }
        return shopMinions;
    }

    public ArrayList<Spell> getShopSpells() {
        ArrayList<Spell> shopSpells = new ArrayList<>();
        for (Card card : this.cardsInShop) {
            if (card instanceof Spell) {
                shopSpells.add((Spell) card);
            }
        }
        return shopSpells;
    }

}
