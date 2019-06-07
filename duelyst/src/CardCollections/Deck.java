package CardCollections;


import Cards.*;
import InstanceMaker.CardMaker;


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

    private static Deck deckKillHero;
    private static Deck deckHoldFlag;
    private static Deck deckCaptureFlag;

    static {
        initializeSecondAIHF();
        initializeFirstAIKH();
        initializeThirdAICF();
    }

    private static void initializeFirstAIKH() {
        Hero[] heroes = CardMaker.getHeroes();
        Minion[] minions = CardMaker.getMinions();
        Spell[] spells = CardMaker.getSpells();
        Item[] items = CardMaker.getAllItems();
        deckKillHero = new Deck("Kill Hero Story");
        deckKillHero.setHero(heroes[0]);
        deckKillHero.setItem(items[0]);
        addToKillHero(minions, 0, 8, 10, 10, 12, 16, 17, 20, 21, 25, 37, 35, 39);
        addToKillHero(spells, 0, 6, 9, 10, 11, 17, 19);

    }

    private static void initializeSecondAIHF() {
        Hero[] heroes = CardMaker.getHeroes();
        Minion[] minions = CardMaker.getMinions();
        Spell[] spells = CardMaker.getSpells();
        Item[] items = CardMaker.getAllItems();
        deckHoldFlag = new Deck("Hold flag Story");
        deckHoldFlag.setHero(heroes[4]);
        deckHoldFlag.setItem(items[17]);
        addToHoldFlag(minions, 2, 3, 5, 8, 12, 15, 15, 19, 23, 27, 30, 33, 39);
        addToHoldFlag(spells, 2, 3, 5, 8, 9, 13, 19);
    }

    private static void initializeThirdAICF() {
        Hero[] heroes = CardMaker.getHeroes();
        Minion[] minions = CardMaker.getMinions();
        Spell[] spells = CardMaker.getSpells();
        Item[] items = CardMaker.getAllItems();
        deckCaptureFlag = new Deck("Capture Flag Story");
        deckCaptureFlag.setHero(heroes[6]);
        deckCaptureFlag.setItem(items[11]);
        addToCaptureFlag(minions, 6, 7, 10, 14, 16, 16, 20, 24, 25, 28, 29, 31, 34);
        addToCaptureFlag(spells, 6, 10, 12, 14, 15, 16, 17);
    }

    private static void addToCaptureFlag(Spell[] spells, int... a) {
        for (int i1 : a) {
            deckCaptureFlag.addCard(spells[i1 - 1]);
        }
    }

    private static void addToCaptureFlag(Minion[] minions, int... a) {
        for (int i1 : a) {
            deckCaptureFlag.addCard(minions[i1 - 1]);
        }
    }

    private static void addToHoldFlag(Spell[] spells, int... a) {
        for (int i1 : a) {
            deckHoldFlag.addCard(spells[i1 - 1]);
        }
    }

    private static void addToHoldFlag(Minion[] minions, int... a) {
        for (int i1 : a) {
            deckHoldFlag.addCard(minions[i1 - 1]);
        }
    }

    private static void addToKillHero(Minion[] minions, int... a) {
        for (int i1 : a) {
            deckKillHero.addCard(minions[i1]);
        }
    }

    private static void addToKillHero(Spell[] spells, int... a) {
        for (int i1 : a) {
            deckKillHero.addCard(spells[i1]);
        }
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
        return cards.size() == 20 && getHero() != null;
    }

    public static Deck getDeckKillHero() {
        return deckKillHero;
    }

    public static Deck getDeckHoldFlag() {
        return deckHoldFlag;
    }

    public static Deck getDeckCaptureFlag() {
        return deckCaptureFlag;
    }

    Card returnCardFromDeck(String cardName) {
        for (Card card : this.cards) {
            if (card.getName().equals(cardName) || card.getId().equals(cardName))
                return card;
        }
        return null;
    }

    public String returnIdFromName(String cardName){
        Card card=returnCardFromDeck(cardName);
        if(card==null){
            return "This deck doesn't have this card";
        }
        return card.getId();
    }

}
