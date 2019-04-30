package CardCollections;

import effects.*;

import java.util.ArrayList;

import effects.Card;

public class Collection {
    private ArrayList<Card> cards;
    private ArrayList<Item> items;
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private int daric;
    private static ArrayList<Collection> collections = new ArrayList<>();

    public Collection() {
        this.cards = new ArrayList<>();
        this.items = new ArrayList<>();
        this.daric = 15000;
    }

    public int getDaric() {
        return daric;
    }

    public void addCardToCollection(Card card) {
        this.cards.add(card);
    }

    public void addItemToCollection(Item item) {
        this.items.add(item);
    }

    public static ArrayList<Collection> getCollections() {
        return collections;
    }

    public void changeDaric(int value) {
        this.daric += value;
    }

    public static void addCollection(Collection collection) {
        collections.add(collection);
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

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public String search(String name) {
        if (findCard(name) != null)
            return findCard(name).getId();

        else if (findItem(name) != null)
            return findItem(name).getId();
        return null;
    }

    private Card findCard(String cardNameID) {
        for (Card card : cards) {
            if (card.getName().equals(cardNameID))
                return card;

            if (card.getId().equals(cardNameID))
                return card;
        }
        return null;
    }

    private Item findItem(String itemNameID) {
        for (Item item : items) {
            if (item.getName().equals(itemNameID))
                return item;

            if (item.getId().equals(itemNameID))
                return item;
        }
        return null;
    }

    public String createDeck(String deckName) {
        for (Deck deck1 : decks) {
            if (deck1.getName().equals(deckName)) {
                return "this Deck name Already Exist! Please Try again with another DeckName.";
            }
        }
        Deck deck = new Deck(deckName);
        decks.add(deck);
        return "Deck Successfully created";
    }

    public Deck findDeck(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public String deleteDeck(String deckName) {
        Deck garbageDeck = findDeck(deckName);
        if (garbageDeck != null) {
            decks.remove(garbageDeck);
            return "Deck Successfully deleted";
        } else {
            return "No such deck found";
        }
    }

    private boolean isCardInDeck(String cardID, String deckName) {
        Deck deck = findDeck(deckName);

        for (int i = 0; i < deck.getCards().size(); i++) {
            Card card = deck.getCards().get(i);
            if (card.getId().equals(cardID))
                return true;
        }
        return false;
    }

    private boolean deckCardsHasStorage(String deckName) {
        Deck deck = findDeck(deckName);
        return deck.getCards().size() < 20;
    }

    private boolean deckHasItem(String deckName) {
        Deck deck = findDeck(deckName);

        return deck.getItem() != null;
    }

    private boolean deckHasHero(String deckName) {
        Deck deck = findDeck(deckName);
        return deck.getHero() != null;
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
            if (card.getClass().toString().equals("class Hero")) {
                if (deckHasHero(deckName))
                    return "deck already has hero";

                addHeroToDeck(card, deck);
                return "hero successfully add";
            }

            if (isCardInDeck(cardID, deckName))
                return "deck already has this card";

            if (deckCardsHasStorage(deckName))
                return "deck Card storage is full";

            addCardToDeck(card, deck);
            return "card successfully add";
        }

        if (deckHasItem(deckName))
            return "deck already has item";

        addItemToDeck(item, deck);
        return "item successfully add";

    }

    private void addHeroToDeck(Card card, Deck deck) {
        deck.setHero((Hero) card);
    }

    private void addCardToDeck(Card card, Deck deck) {
        deck.addCard(card);
    }

    private void addItemToDeck(Item item, Deck deck) {
        deck.setItem(item);
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
                    return "removing hero from deck successfully done";
                }
                return "invalid Hero card";
            }
            if (!isCardInDeck(cardID, deckName))
                return "card does not exist in this deck";
            deck.getCards().remove(card);
            return "card successfully removed from deck";
        }

        if (item.getName().equals(deck.getItem().getName())) {
            deck.setItem(null);
            return "deck item successfully removed";
        }

        return "item does not exist in this deck";
    }

    public String save() {
        return "successfully saved";
    }

    public String isDeckValidate(String deckName) {
        Deck deck = findDeck(deckName);

        if (deck == null)
            return "cannot find deck with this name";

        if (deck.getCards().size() == 20 && deck.getHero() != null)
            return "deck is validate";

        return "deck is not validate";
    }

    public ArrayList<Hero> getCollectionHeros() {
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

    public int numberOfItems() {
        return this.items.size();
    }
}
