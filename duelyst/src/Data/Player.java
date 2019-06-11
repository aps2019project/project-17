package Data;

import CardCollections.Deck;
import CardCollections.Hand;
import Cards.Card;
import Cards.Item;
import Effects.Effect;
import Effects.enums.SpecialSituation;

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
    private ArrayList<Effect> effects = new ArrayList<>();
    private ArrayList<Effect> specialSituationBuff = new ArrayList<>();
    private SpecialSituation specialSituation = SpecialSituation.NONE;

    public Player() {
    }

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
        if (mainDeck.getItem() != null) {
            System.out.println("collectable -> " + mainDeck.getItem().getName());
            collectAbleItems.add(this.mainDeck.getItem());
        }
        setHand();
        this.mana = 2;
        this.previousMana = 2;
        return true;
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

    public void setSpecialSituation(SpecialSituation specialSituation) {
        this.specialSituation = specialSituation;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void passTurn() {
        for (Effect effect : effects) {
            effect.action(null);
            effect.checkForRemove();
        }
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (effect.isDisable())
                effects.remove(effect);
        }
    }

    public void useSpecialSituationBuff() {
        for (Effect effect : specialSituationBuff) {
            effect.action(null);
        }
    }

    public SpecialSituation getSpecialSituation() {
        return specialSituation;
    }
}
