package CardCollections;

import Cards.*;
import Client.*;
import Data.Account;
import InstanceMaker.CardMaker;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class Collection implements Serializable {
    private ArrayList<Card> cards;
    private ArrayList<Item> items;
    private ArrayList<Deck> decks;
    private ArrayList<Deck> validDecks;
    private Deck mainDeck;
    private int daric;

    public Collection() {
        this.cards = new ArrayList<>();
        this.items = new ArrayList<>();
        this.decks = new ArrayList<>();
        this.daric = 1500000;
        this.mainDeck = null;
    }

    public void delete() {
        toDelete();
        Deck deck = new Deck("deck");
        Deck deck1 = new Deck("AI");
        decks.add(deck);
        decks.add(deck1);

        for (int i = 0; i < 20; i++)
            if (cards.get(i).getName().equalsIgnoreCase("total disarm") || cards.get(i).getName().equalsIgnoreCase("fireball") || cards.get(i).getName().equalsIgnoreCase("lighting bolt") || cards.get(i).getName().equalsIgnoreCase("all disarm") || cards.get(i).getName().equalsIgnoreCase("kings guard"))
                deck.addCard(cards.get(i));

        for (int i = 20; i < 36; i++) {
            if (cards.get(i).getName().equalsIgnoreCase("oghab"))
                continue;
            deck.addCard(cards.get(i));
        }

        for (int i = 30; i < 50; i++)
            deck1.addCard(cards.get(i));

        deck.setHero((Hero) cards.get(66));
        deck.setItem(CardMaker.getAllItems()[13]);
        deck1.setHero((Hero) cards.get(69));
        this.mainDeck = deck;
    }

    public Card findCard(String cardNameID) {
        for (Card card : cards) {
            if (card.getName().equals(cardNameID))
                return card;

            if (card.getId().equals(cardNameID))
                return card;
        }
        return null;
    }

    public Item findItem(String itemNameID) {
        for (Item item : items) {
            if (item.getName().equals(itemNameID))
                return item;

            if (item.getId().equals(itemNameID))
                return item;
        }
        return null;
    }

    public String search(String name) {
        if (findCard(name) != null)
            return findCard(name).getId();

        else if (findItem(name) != null)
            return findItem(name).getId();
        return "Can't find this card\\item";
    }

    public String createDeck(String deckName) {
        for (Deck deck1 : decks) {
            if (deck1.getName().equals(deckName)) {
                return "this deck name already exist! Please try again with another deckName";
            }
        }
        Client.send(new Message("create deck " + deckName.trim()));
        Gson gson = new Gson();
        Object object = Client.get();
        Message message = gson.fromJson(object.toString(), Message.class);
        Deck deck = gson.fromJson(message.toString(), Deck.class);
        if (deck.getName().trim().equals(deckName))
            decks.add(deck);
        return "deck Successfully created";
    }

    public String deleteDeck(String deckName) {
        Deck garbageDeck = findDeck(deckName);
        if (garbageDeck != null) {
            Client.send(new Message("delete deck " + deckName));
            decks.remove(garbageDeck);
            return "Deck Successfully deleted";
        }
        return "No such deck found";
    }

    public String addToDeck(String cardID, String deckName) {
        Deck deck = findDeck(deckName);
        if (deck == null)
            return "cannot find deck with this name";

        Card card = findCard(cardID);
        Item item = findItem(cardID);

        if (card == null && item == null)
            return "invalid card\\item";
        if (card != null) {
            if (card instanceof Hero) {
                if (deckHasHero(deckName))
                    return "deck already has hero";
                deck.setHero((Hero) card);
                return "hero successfully add";
            }
            if (!deckCardsHasStorage(deckName))
                return "deck Card storage is full";
            Client.send(new Message("add to deck " + deckName + " " + card.getName()));
            deck.addCard(card);
            return "card successfully add";
        }
        if (deckHasItem(deckName))
            return "deck already has item";
        deck.setItem(item);
        return "item successfully add";
    }

    public String removeFromDeck(String cardID, String deckName) {
        Deck deck = findDeck(deckName);
        Item item = findItem(cardID);
        Card card = findCard(cardID);

        if (deck == null)
            return "cannot find deck with this name";

        if (item == null && card == null)
            return "invalid card\\item";

        if (card != null) {
            if (card instanceof Hero) {
                if (card.getName().equals(deck.getHero().getName())) {
                    deck.setHero(null);
                    Client.send(new Message("remove from deck " + deckName + " " + card.getName()));
                    return "removing this hero from deck successfully done";
                }
                return "this deck doesnt have this card";
            }
            if (!isCardInDeck(cardID, deckName))
                return "card does not exist in this deck";
            deck.getCards().remove(card);
            Client.send(new Message("remove from deck " + deckName + " " + card.getName()));
            return "card successfully removed from deck";
        }

        if (item.getName().equals(deck.getItem().getName())) {
            deck.setItem(null);
            return "deck item successfully removed";
        }

        return "item does not exist in this deck";
    }

    public String isDeckValidate(String deckName) {
        Deck deck = findDeck(deckName);

        if (deck == null)
            return "cannot find deck with this name";

        if (deck.getCards().size() == 20 && deck.getHero() != null)
            return "deck is validate";

        return "deck is not validate";
    }

    public String setMainDeck(String deckName) {
        if (findDeck(deckName) == null)
            return "cant find deck with this name";
        this.mainDeck = findDeck(deckName);
        Account.getLoginUser().getPlayer().setMainDeck(findDeck(deckName));
        return "main deck successfully choose";
    }

    public Deck findDeck(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    private boolean isCardInDeck(String cardID, String deckName) {
        Deck deck = findDeck(deckName);

        if (deck == null)
            return false;

        for (int i = 0; i < deck.getCards().size(); i++) {
            Card card = deck.getCards().get(i);
            if (card.getId().equals(cardID))
                return true;
        }
        return false;
    }

    private boolean deckCardsHasStorage(String deckName) {
        return findDeck(deckName).getCards().size() < 20;
    }

    private boolean deckHasItem(String deckName) {
        return findDeck(deckName).getItem() != null;
    }

    private boolean deckHasHero(String deckName) {
        return findDeck(deckName).getHero() != null;
    }

    public String save() {
        return "successfully saved";
    }

    public ArrayList<Hero> getCollectionHeroes() {
        ArrayList<Hero> collectionHeroes = new ArrayList<>();
        for (Card card : this.cards) {
            if (card instanceof Hero) {
                collectionHeroes.add((Hero) card);
            }
        }
        return collectionHeroes;
    }

    public ArrayList<Minion> getCollectionMinions() {
        ArrayList<Minion> collectionMinions = new ArrayList<>();
        for (Card card : this.cards) {
            if (card instanceof Minion) {
                collectionMinions.add((Minion) card);
            }
        }
        return collectionMinions;
    }

    public ArrayList<Spell> getCollectionSpells() {
        ArrayList<Spell> collectionSpells = new ArrayList<>();
        for (Card card : this.cards) {
            if (card instanceof Spell) {
                collectionSpells.add((Spell) card);
            }
        }
        return collectionSpells;
    }

    void addCardToCollection(Card card) {
        this.cards.add(card);
    }

    void addItemToCollection(Item item) {
        for (Item item1 : this.items) {
            if (item1.getName().equals(item.getName()))
                return;
        }
        this.items.add(item);
    }

    void removeCard(Card card) {
        cards.remove(card);
        for (Deck deck : decks) {
            if (deck.returnCardFromDeck(card.getName()) != null)
                deck.getCards().remove(deck.returnCardFromDeck(card.getName()));
        }
    }

    public int getDaric() {
        return daric;
    }

    public void changeDaric(int value) {
        this.daric += value;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    private void toDelete() {
        for (int i = 0; i < 70; i++)
            cards.add(CardMaker.getAllCards()[i]);
    }

    public Deck returnDeckFromName(String deckName) {
        for (Deck deck : this.decks) {
            if (deck.getName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public ArrayList<Deck> getValidDecks() {
        this.validDecks = new ArrayList<>();
        for (Deck deck : decks) {
            if (isDeckValidate(deck.getName()).equals("deck is validate"))
                this.validDecks.add(deck);
        }
        return validDecks;
    }

    public void updateCollectionFromServer(ArrayList<Card> cards) {
        this.cards = new ArrayList<>();
        for (Card card : cards) this.cards.add(CardMaker.getCardByName(card.getName()));
    }
}
