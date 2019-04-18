package CardCollections;

import effects.*;

import java.util.ArrayList;

public class Deck {
    private String name;
    private Hero hero;
    private ArrayList<Cards> cards;
    private ArrayList<Item> items;
    // private Hero hero;


    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public boolean isDeckValidate(String deckName){
        if(this.cards.size()==20 && this.hero!=null){
            return true;
        }
        return false;
    }

    public int getNumberOfDeckCards(){
        return this.cards.size();
    }

//    public Deck getDeck(String deckName) {
//
//    }
}
