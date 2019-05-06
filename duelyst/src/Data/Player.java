package Data;

import CardCollections.*;
import effects.Buff;
import effects.Card;
import effects.Item;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    private Deck mainDeck;
    private Deck copyMainDeck;
    private Hand hand;
    private int mana;
    private int previousMana;
    private String userName;
    private ArrayList<Item> collectAbleItems;
    private ArrayList<Card> graveYard;
    private int holdingFlags;
    private Card nextCard;
    private Buff buff = new Buff();
    private boolean putInGroundAttackEnemyHero;

    public Player(String userName, Deck deck) {
        this.mana = 2;
        this.previousMana = 2;
        this.collectAbleItems = new ArrayList<>();
        this.graveYard = new ArrayList<>();
        this.hand = new Hand();
        this.userName = userName;
        this.holdingFlags = 0;
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
        this.nextCard = null;
        this.mainDeck = null;
    }

    public Buff getBuff() {
        return buff;
    }


    public void setPutInGroundAttackEnemyHero(boolean putInGroundAttackEnemyHero) {
        this.putInGroundAttackEnemyHero = putInGroundAttackEnemyHero;
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

    public void addItemToCollectAbleItems(Item item) {
        this.collectAbleItems.add(item);
    }

    public void changeNumberOfHoldingFlags(int value) {
        this.holdingFlags += value;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck deck) {
        this.mainDeck = deck;
        this.copyMainDeck = new Deck(this.mainDeck.getName());
        setCopyMainDeck(true);
        collectAbleItems.add(this.mainDeck.getItem());
    }

    private void setCopyMainDeck(boolean addHero) {
        for (int i = 0; i < mainDeck.getCards().size(); i++) {
            copyMainDeck.addCard(mainDeck.getCards().get(i));
        }
        if (addHero)
            copyMainDeck.setHero(mainDeck.getHero());
    }

    public boolean isPutInGroundAttackEnemyHero() {
        return putInGroundAttackEnemyHero;
    }

    private void setHand() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int n = random.nextInt() % this.copyMainDeck.getCards().size();
            while (n < 0) {
                n = random.nextInt() % this.copyMainDeck.getCards().size();
            }
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

    public void addMana(int change) {
        this.mana += change;
    }

    public void lessMana(int change) {
        this.mana -= change;
    }

    private void setNextCard() {
        Random random = new Random();
        int n = random.nextInt() % this.copyMainDeck.getCards().size();
        while (n < 0) {
            n = random.nextInt() % this.copyMainDeck.getCards().size();
        }
        this.nextCard = this.copyMainDeck.getCards().get(n);
        this.copyMainDeck.getCards().remove(n);
    }

    public void addCardToHand() {
        if (this.hand.getCards().size() >= 5)
            return;
        while (this.hand.getCards().size() < 5) {
            hand.addCard(this.nextCard);
            setNextCard();
        }
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

    @Override
    public boolean equals(Object player) {
        if (player instanceof Player) {
            Player player1 = (Player) player;
            return this.userName.equals(player1.getUserName());
        }
        return false;
    }

    public void action(int x, int y) {
        if (this.buff == null)
            return;
        this.buff.action(x, y, buff.getBuffDetails());
    }

    public int getPreviousMana() {
        return previousMana;
    }

    public void setPreviousMana(int previousMana) {
        this.previousMana = previousMana;
    }

    public boolean isPlayerReadyForBattle() {
        if (mainDeck == null || !mainDeck.isDeckValidate())
            return false;
        setHand();
        return true;
    }
}
