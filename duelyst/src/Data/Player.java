package Data;

import CardCollections.*;
import effects.Card;
import effects.Item;
import effects.Minion;

import javax.swing.*;
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
    private Card nextCard;
    private static Player AIPlayer;
    private static Player playerModeKH;
    private static Player playerModeCF;
    private static Player playerModeHF;

    public Player(String userName, Deck deck) {
        this.mana = 2;
        this.collectAbleItems = new ArrayList<>();
        this.graveYard = new ArrayList<>();
        this.hand = new Hand();
        this.userName = userName;
        this.holdingFlags = 0;
        this.playerHasFlag = false;
        this.nextCard = null;
        setMainDeck(deck);
    }

    public Player(String userName) {
        this.mana = 2;
        this.collectAbleItems = new ArrayList<>();
        this.graveYard = new ArrayList<>();
        this.hand = new Hand();
        this.userName = userName;
        this.holdingFlags = 0;
        this.playerHasFlag = false;
        this.nextCard = null;
        this.mainDeck = null;
    }

    public void allMinionsReset() {
        for (int i = 0; i < this.mainDeck.getCards().size(); i++) {
            if (this.mainDeck.getCards().get(i) instanceof Minion) {
                Minion minion = (Minion) this.mainDeck.getCards().get(i);
                minion.setCanAttack(true);
                minion.setCanMove(true);
                minion.setCanCounterAttack(true);
            }
        }
        this.mainDeck.getHero().setCanAttack(true);
        this.mainDeck.getHero().setCanMove(true);
        this.mainDeck.getHero().setCanCounterAttack(true);
    }

    public Item getItemFromHand(String itemName) {
        for (Item item : this.collectAbleItems) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }


    public Card getCardFromHand(String cardName) {
        for (int i = 0; i < hand.getCards().size(); i++) {
            if (hand.getCards().get(i).getName().equals(cardName))
                return hand.getCards().get(i);
        }
        return null;
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

    private void setMainDeck(Deck deck) {
        this.mainDeck = deck;
        this.copyMainDeck = new Deck(this.mainDeck.getName());
        setCopyMainDeck(true);
        setHand();
        collectAbleItems.add(this.mainDeck.getItem());
    }

    private void setCopyMainDeck(boolean addHero) {
        for (int i = 0; i < mainDeck.getCards().size(); i++) {
            copyMainDeck.addCard(mainDeck.getCards().get(i));
        }
        if (addHero)
            copyMainDeck.setHero(mainDeck.getHero());
    }

    private void setHand() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int n = random.nextInt(this.copyMainDeck.getCards().size());
            this.hand.addCard(copyMainDeck.getCards().get(n));
            this.copyMainDeck.getCards().remove(n);
        }
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

    private void setNextCard() {
        if (copyMainDeck.getCards().size() == 0)
            setCopyMainDeck(false);
        Random random = new Random();
        int n = random.nextInt(this.copyMainDeck.getCards().size());
        this.nextCard = this.copyMainDeck.getCards().get(n);
        this.copyMainDeck.getCards().remove(n);
    }

    public void addCardToHand() {
        if (this.hand.getCards().size() >= 5)
            return;
        hand.addCard(this.nextCard);
        setNextCard();
    }

    public void changeMana(int value) {
        this.mana += value;
    }

    public Card getNextCard() {
        return nextCard;
    }

    public void removeCardFromHand(Card card) {
        hand.removeCard(card);
    }

    public Deck getCopyMainDeck() {
        return copyMainDeck;
    }

    public boolean equals(Player player) {
        return this.userName.equals(player.userName);
    }

    public static Player getAIPlayer() {
        return AIPlayer;
    }

    public static void setAIPlayer(Player AIPlayer) {
        Player.AIPlayer = AIPlayer;
    }

    public static Player getPlayerModeKH() {
        return playerModeKH;
    }

    public static void setPlayerModeKH(Player playerModeKH) {
        Player.playerModeKH = playerModeKH;
    }

    public static Player getPlayerModeCF() {
        return playerModeCF;
    }

    public static void setPlayerModeCF(Player playerModeCF) {
        Player.playerModeCF = playerModeCF;
    }

    public static Player getPlayerModeHF() {
        return playerModeHF;
    }

    public static void setPlayerModeHF(Player playerModeHF) {
        Player.playerModeHF = playerModeHF;
    }
}
