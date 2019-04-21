package CardCollections;


import effects.Cards;
import effects.Hero;


import java.util.ArrayList;

public class Deck {
    private String name;

    private ArrayList<Cards> cards;
    private Item item;
    private Hero hero;
    private static ArrayList<Deck> decks;

    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();


    }

    public String getName() {
        return name;
    }

    public ArrayList<Cards> getCards() {
        return cards;
    }


    public Item getItem() {
        return item;

    }

    public Hero getHero() {
        return hero;
    }

    public static boolean isDeckValidate(String deckName){
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName))
                return deck.cards.size() == 20;
    public void setHero(Hero hero) {
        this.hero = hero;
    }

        }
        return false;
    }


    public static void setMainDeck(String deckName){
    }

    public int getNumberOfDeckCards(){
        return this.cards.size();
    }

//    public Deck getDeck(String deckName) {
//
//    }

}
