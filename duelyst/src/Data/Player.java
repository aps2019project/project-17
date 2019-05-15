package Data;

import CardCollections.*;
import effects.*;

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
        this.mana = 10;
        this.previousMana = 10;
        this.collectAbleItems = new ArrayList<>();
        this.graveYard = new ArrayList<>();
        this.hand = new Hand();
        this.userName = userName;
        this.holdingFlags = 0;
        this.nextCard = null;
        setMainDeck(deck);
    }

    public Player(String userName) {
        this.mana = 120;
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
    }

    private void setCopyMainDeck() {
        for (int i = 0; i < mainDeck.getCards().size(); i++) {
            copyMainDeck.addCard(mainDeck.getCards().get(i));
        }

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
        setNextCard();
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

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void lessMana(int change) {
        this.mana -= change;
    }

    private void setNextCard() {
        int n = new Random().nextInt() % copyMainDeck.getCards().size();
        while (n < 0) {
            n = new Random().nextInt() % copyMainDeck.getCards().size();
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
        setCopyMainDeck();
        this.hand = new Hand();
        if (mainDeck.getItem() != null)
            collectAbleItems.add(this.mainDeck.getItem());
        setHand();
        return true;
    }

    private void setComboHands() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < copyMainDeck.getCards().size(); j++) {
                if (copyMainDeck.getCards().get(j) instanceof Minion) {
                    if (((Minion) copyMainDeck.getCards().get(j)).getAttackType().equals(AttackType.COMBO)) {
                        this.hand.addCard(copyMainDeck.getCards().get(j));
                        this.copyMainDeck.getCards().remove(copyMainDeck.getCards().get(j));
                        break;
                    }
                }
            }
        }
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            int n = random.nextInt() % this.copyMainDeck.getCards().size();
            while (n < 0) {
                n = random.nextInt() % this.copyMainDeck.getCards().size();
            }
            this.hand.addCard(copyMainDeck.getCards().get(n));
            this.copyMainDeck.getCards().remove(n);
        }
        setNextCard();
    }
}
