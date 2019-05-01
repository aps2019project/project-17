package CardCollections;


import effects.*;


import java.util.ArrayList;

public class Deck {
    private String name;

    private ArrayList<Card> cards;
    private Item item;
    private Hero hero;

    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();

    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void addCard(Card card) {
        cards.add(card);
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

    public boolean isDeckValidate() {
        return cards.size() == 20;
    }


    public static void setMainDeck(String deckName) {
    }

    public int getNumberOfDeckCards() {
        return this.cards.size();
    }
}
