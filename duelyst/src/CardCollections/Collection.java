package CardCollections;

import effects.*;

import java.util.ArrayList;

import effects.Card;

public class Collection {
    private ArrayList<Card> cards;
    private ArrayList<Item> items;
    private static ArrayList<Deck> decks = new ArrayList<>();


    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
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
        Deck deck = new Deck(deckName);
        for (Deck deck1 : decks) {
            if (deck1.getName().equals(deckName)) {
                return "Deck Already Exist! Please Try again with another DeckName.";
            }
        }
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

    public void deleteDeck(String deckName) {
        Deck garbageDeck = findDeck(deckName);
        if (garbageDeck != null) {
            decks.remove(garbageDeck);
        }
    }

    public boolean isCardInDeck(String cardID, String deckName) {
        if (findDeck(deckName) == null || findCard(cardID) == null) {
            return false;
        }
        return findDeck(deckName).getCards().contains(findCard(cardID));
    }

    public boolean isItemInDeck(String itemID, String deckName) {
        if (findDeck(deckName) == null || findItem(itemID) == null) {
            return false;
        }
        return findDeck(deckName).getItem().getName().equals(findItem(itemID).getName());
    }

    public String addToDeck(String ID, String deckName) {
        if (findCard(ID) == null || findItem(ID) == null)
            return "No such Card/Item ID found in collection";

        if (!isCardInDeck(ID, deckName) || !isItemInDeck(ID, deckName))
            return "This Card/Item ID doesn't exist in this deck!";

        if (findDeck(deckName).getNumberOfDeckCards() + 1 > 20)
            return "The Deck card storage is full.";

        if (findDeck(deckName).getHero() != null)
            return "This deck has hero";
        else {
            findDeck(deckName).setHero((Hero) findCard(ID));
            return "Hero added successfully";
        }
//        if(findCardByID(ID)!=null){
//            findDeck(deckName).getCards().add(findCardByID(ID));
//        }
    }

}
