package Data;

import CardCollections.*;
import effects.Cards;
import effects.Item;

import java.util.ArrayList;

public class Player {

    private Deck mainDeck;
    private Hand hand;
    private int mana;
    private ArrayList<Item> collectableItems;
    private ArrayList<Cards> graveYard;

    public Player() {}

    public Deck getMainDeck() {
        return mainDeck;
    }

    public Hand getHand() {
        return hand;
    }

    public int getMana() {
        return mana;
    }

    public ArrayList<Cards> getGraveYard() {
        return this.graveYard;
    }

    public void addCardToGraveYard(Cards card){
        this.graveYard.add(card);
    }

    public ArrayList<Item> getCollectableItems() {
        return collectableItems;
    }

    public void addMana(int change){
        this.mana += change;
    }

    public void lessMana(int change){
        this.mana -= change;
    }

    public void addCardToHand(Cards card){
        hand.addCard(card);
        // or it might handle in controller!
        // or here we can inputting String and pass the Card related to that String!
    }
    public void removeCardFromHand(Cards card){
        hand.removeCard(card);
        // or it might handle in controller!
        // or here we can inputting String and pass the Card related to that String!
    }
}
