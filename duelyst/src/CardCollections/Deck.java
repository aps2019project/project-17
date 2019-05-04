package CardCollections;


import controller.InstanceBuilder;
import effects.*;


import java.util.ArrayList;
import java.util.Arrays;

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

    private static void initilixeFirstAIKH() {
        Hero[] heroes = InstanceBuilder.getHeroes();
        Minion[] minions = InstanceBuilder.getMinions();
        Spell[] spells = InstanceBuilder.getSpells();
        Item[] items = InstanceBuilder.getItems();
        deckKillHero = new Deck("Kill Hero Story");
        deckKillHero.setHero(heroes[0]);
        deckKillHero.setItem(items[0]);
        addToKillHero(minions, 0, 8, 10, 12, 16, 17, 20, 21, 25, 37, 35, 39);
        addToKillHero(spells, 0, 6, 9, 10, 11, 17, 19);

    }

    private static void addToKillHero(Minion[] minions, int... a) {
        for (int i1 : a) {
            deckKillHero.addCard(minions[i1]);
        }
    }

    private static void addToKillHero(Spell[] spells, int... a){
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
        return cards.size() == 20;
    }


    public static void setMainDeck(String deckName) {
    }

    public int getNumberOfDeckCards() {
        return this.cards.size();
    }
}
