package Data;

import CardCollections.*;
import effects.Card;
import effects.Item;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    private Deck mainDeck;
    private Deck copyMainDeck;
    private Hand hand;
    private int mana;
    private String userName;
    private ArrayList<Item> collectAbleItems;
    private ArrayList<Card> graveYard;
    private int holdingFlags;
    private boolean playerHasFlag;

    public Player(String userName) {
        this.mana = 9;
        this.collectAbleItems = new ArrayList<>();
        this.graveYard = new ArrayList<>();
        this.copyMainDeck = new Deck(mainDeck.getName());
        this.hand = new Hand();
        this.userName = userName;
        this.holdingFlags = 0;
        this.playerHasFlag = false;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Item> getCollectAbleItems() {
        return collectAbleItems;
    }

    public int getHoldingFlags() {
        return holdingFlags;
    }

    public boolean isPlayerHasFlag() {
        return playerHasFlag;
    }

    public void addItemToCollectAbleItems(Item item) {
        this.collectAbleItems.add(item);
    }

    public void changeNumberOfHoldingFlags(int value) {
        this.holdingFlags += value;
    }

    public void setPlayerHasFlag(boolean playerHasFlag) {
        this.playerHasFlag = playerHasFlag;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck deck) {
        this.mainDeck = deck;
        setCopyMainDeck();
        setHand();
        collectAbleItems.add(this.mainDeck.getItem());
    }

    private void setCopyMainDeck() {
        for (int i = 0; i < mainDeck.getCards().size(); i++) {
            copyMainDeck.addCard(mainDeck.getCards().get(i));
        }
    }

    private void setHand() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int n = random.nextInt(this.copyMainDeck.getCards().size());
            this.hand.addCard(copyMainDeck.getCards().get(n));
            this.copyMainDeck.getCards().remove(n);
        }
    }

    public Card getCardFromHand(String cardName){
        for (int i = 0; i < hand.getCards().size(); i++) {
            if (hand.getCards().get(i).getName().equals(cardName))
                return hand.getCards().get(i);
        }
        return null;
    }

    public Hand getHand() {
        return hand;
    }

    public int getMana() {
        return mana;
    }

    public ArrayList<Card> getGraveYard() {
        return this.graveYard;
    }

    public void addCardToGraveYard(Card card) {
        this.graveYard.add(card);
    }

    public ArrayList<Item> getCollectableItems() {
        return collectAbleItems;
    }

    public void addMana(int change) {
        this.mana += change;
    }

    public void lessMana(int change) {
        this.mana -= change;
    }

    public void addCardToHand() {
        Random random = new Random();
        int n = random.nextInt(this.copyMainDeck.getCards().size());
        hand.addCard(this.copyMainDeck.getCards().get(n));
        this.copyMainDeck.getCards().remove(n);

        if (copyMainDeck.getCards().size() == 0)
            setCopyMainDeck();
    }

    public void removeCardFromHand(Card card) {
        hand.removeCard(card);
        graveYard.add(card);
    }

    public boolean equals(Player player){
        return this.userName.equals(player.userName);
    }
}
