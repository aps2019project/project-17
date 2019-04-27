package CardCollections;


import effects.*;


import java.util.ArrayList;

public class Deck {
    private String name;

    private ArrayList<Card> cards;
    private Item item;
    private Hero hero;
    private static ArrayList<Deck> decks;

    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();

    }

    public void setItem(Item item) {
        this.item = item;
    }

    public static ArrayList<Deck> getDecks() {
        return decks;
    }

    public String addCard(Card card){
        for (Card card1 : cards) {
            if (card1.getName().equals(card.getName()))
                return "this card already exist";
        }
        cards.add(card);
        return "card successfully add";
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }


    public Item getItem() {
        return item;

    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public static boolean isDeckValidate(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName))
                return deck.isDeckValidate();

        }
        return false;
    }

    public boolean isDeckValidate() {
        return cards.size() == 20;
    }


    public static void setMainDeck(String deckName) {
    }

    public int getNumberOfDeckCards() {
        return this.cards.size();
    }

//    public Deck getDeck(String deckName) {
//
//    }

}
