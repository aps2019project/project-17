package Data;

import CardCollections.Deck;
import CardCollections.Hand;
import Cards.*;
import Effects.Effect;
import Effects.enums.MinionType;
import Effects.enums.SpecialSituation;
import InstanceMaker.CardMaker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Player implements Serializable {
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
    private boolean isKingWisdomActive = false;

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
            if (hand.getCards().get(i).getName().toLowerCase().trim().equals(cardName))
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
        this.copyMainDeck = new Deck(deck.getName().trim());
    }

    private void setCopyMainDeck() {
        setDeckReady();
        logicCopyMainDeck();
    }

    public Hand getHand() {
        return hand;
    }

    public int getMana() {
        return mana;
    }

    public void incrementMana(){
        this.mana+=2;
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

    private boolean setNextCard() {
        if (copyMainDeck.getCards().size() <= 0) {
            nextCard = null;
            return false;
        }

        int n = new Random().nextInt() % copyMainDeck.getCards().size();
        while (n < 0) {
            n = new Random().nextInt() % copyMainDeck.getCards().size();
        }
        this.nextCard = this.copyMainDeck.getCards().get(n);
        this.copyMainDeck.getCards().remove(n);
        if (copyMainDeck.getCards().size() <= 0) {
            nextCard = null;
            return false;
        }
        return copyMainDeck.getCards().size() > 0;
    }

    public void addCardToHand() {
        if (this.hand.getCards().size() >= 5)
            return;
        while (this.hand.getCards().size() < 5) {
            if (this.nextCard == null)
                return;
            hand.addCard(this.nextCard);
            setNextCard();
        }
    }

    private void logicCopyMainDeck() {
        mainDeck.getHero().setCanMove(true);
        mainDeck.getHero().setCanAttack(true);
        mainDeck.getHero().setCanCounterAttack(true);
        for (int i = 0; i < mainDeck.getCards().size(); i++) {
            if (mainDeck.getCards().get(i) instanceof Minion) {
                ((Minion) mainDeck.getCards().get(i)).setCanMove(true);
                ((Minion) mainDeck.getCards().get(i)).setCanAttack(true);
                ((Minion) mainDeck.getCards().get(i)).setCanCounterAttack(true);
            }
            copyMainDeck.addCard(mainDeck.getCards().get(i));
        }
        copyMainDeck.setHero(mainDeck.getHero());
    }

    public void changeMana(int value) {
        this.mana += value;
    }

    public void changeManaFake(int value) {
        // TODO: 2019-06-30
        this.previousMana += 2;
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
        if (mainDeck != null) {
            for (int i = 0; i < mainDeck.getCards().size(); i++) {
                Card card = CardMaker.getCardByName(mainDeck.getCards().get(i).getName().trim());
                if (card instanceof Hero) {
                    mainDeck.setHero((Hero) card);
                    mainDeck.getCards().remove(i);
                    break;
                }
            }
        }
        if (mainDeck == null || !mainDeck.isDeckValidate())
            return false;
        this.graveYard = new ArrayList<>();
        this.holdingFlags = 0;
        this.nextCard = null;
        setCopyMainDeck();
        this.hand = new Hand();
        if (mainDeck.getItem() != null) {
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
            while (n < 0)
                n = random.nextInt() % this.copyMainDeck.getCards().size();

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
        if (isKingWisdomActive) {
            previousMana++;
            mana++;
        }
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

    private void setNewDeck(Deck deck) {
        Deck newDeck = Deck.copyDeck(deck);
        setMainDeck(newDeck);
    }

    private void setDeckReady() {
        Deck finalDeck = new Deck(mainDeck.getName());
        for (int i = 0; i < mainDeck.getCards().size(); i++) {
            if (mainDeck.getCards().get(i) instanceof Spell)
                finalDeck.addCard(Spell.spellCopy((Spell) mainDeck.getCards().get(i)));
            else if (mainDeck.getCards().get(i) instanceof Minion)
                finalDeck.addCard((Minion.minionCopy((Minion) mainDeck.getCards().get(i))));
            else if (mainDeck.getCards().get(i) instanceof Hero)
                finalDeck.addCard(Hero.heroCopy((Hero) mainDeck.getCards().get(i), ((Hero) mainDeck.getCards().get(i)).getCoolDown()));
        }
        finalDeck.setHero(Hero.heroCopy(mainDeck.getHero(), mainDeck.getHero().getCoolDown()));
        if (mainDeck.getItem() != null)
            finalDeck.setItem(mainDeck.getItem());
        setMainDeck(finalDeck);
    }

    public boolean isKingWisdomActive() {
        return isKingWisdomActive;
    }

    public void setKingWisdomActive(boolean kingWisdomActive) {
        isKingWisdomActive = kingWisdomActive;
    }
}
