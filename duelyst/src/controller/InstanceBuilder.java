package controller;

import com.google.gson.Gson;
import effects.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class InstanceBuilder {
    private static final String addressOfBuff = "duelyst//src//effects//Buff.json";
    private static final String addressOfHero = "duelyst//src//effects//Hero.json";
    private static final String addressOfHeroBuff = "duelyst//src//effects//HeroBuff.json";
    private static final String addressOfItem = "duelyst//src//effects//Item.json";
    private static final String addressOfItemBuff = "duelyst//src//effects//ItemBuff.json";
    private static final String addressOfMinion = "duelyst//src//effects//Minion.json";
    private static final String addressOfMinionBuff = "duelyst//src//effects//minionBuff.json";
    private static final String addressOfSpell = "duelyst//src//effects//Spell.json";
    private static Spell[] spells;
    private static BuffDetail[] spellBuff;
    private static Hero[] heroes;
    private static BuffDetail[] heroBuff;
    private static Item[] items;
    private static BuffDetail[] itemBuff;
    private static Minion[] minions;
    private static BuffDetail[] minionBuff;

    static void creation() {
        try {
            spells = (Spell[]) creatingInstance(InstanceType.SPELL);
            spellBuff = (BuffDetail[]) creatingInstance(InstanceType.BUFF);
            addBuffsToSpell();
            heroes = (Hero[]) creatingInstance(InstanceType.HERO);
            heroBuff = (BuffDetail[]) creatingInstance(InstanceType.HERO_BUFF);
            addBuffsToMinion(heroes, heroBuff);
            items = (Item[]) creatingInstance(InstanceType.ITEM);
            itemBuff = (BuffDetail[]) creatingInstance(InstanceType.ITEM_BUFF);
            addBuffsToItem();
            minions = (Minion[]) creatingInstance(InstanceType.MINION);
            minionBuff = (BuffDetail[]) creatingInstance(InstanceType.MINION_BUFF);
            addBuffsToMinion(minions, minionBuff);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String jsonReader(String fileAddress) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileAddress));
        String line;
        StringBuilder jsonString = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }
        return jsonString.toString();
    }

    private static Object[] creatingInstance(InstanceType instanceType) throws IOException {
        Gson gson = new Gson();
        BuffDetail[] buffDetails;
        switch (instanceType) {
            case BUFF:
                buffDetails = gson.fromJson(jsonReader(addressOfBuff), BuffDetail[].class);
                return buffDetails;
            case HERO:
                Hero[] heroes;
                heroes = gson.fromJson(jsonReader(addressOfHero), Hero[].class);
                return heroes;
            case HERO_BUFF:
                buffDetails = gson.fromJson(jsonReader(addressOfHeroBuff), BuffDetail[].class);
                return buffDetails;
            case ITEM:
                Item[] items;
                items = gson.fromJson(jsonReader(addressOfItem), Item[].class);
                return items;
            case ITEM_BUFF:
                buffDetails = gson.fromJson(jsonReader(addressOfItemBuff), BuffDetail[].class);
                return buffDetails;
            case MINION:
                Minion[] minions;
                minions = gson.fromJson(jsonReader(addressOfMinion), Minion[].class);
                return minions;
            case MINION_BUFF:
                buffDetails = gson.fromJson(jsonReader(addressOfMinionBuff), BuffDetail[].class);
                return buffDetails;
            case SPELL:
                Spell[] spells;
                spells = gson.fromJson(jsonReader(addressOfSpell), Spell[].class);
                return spells;
        }
        return null;
    }

    private static void addBuffsToSpell() {
        for (Spell spell : spells) {
            for (BuffDetail spellBuff : spellBuff) {
                spellBuff.init();
                if (spell.getId().equals(spellBuff.getId())) {
                    spell.addBuff(spellBuff);
                }
            }
        }
    }

    private static void addBuffsToMinion(Minion[] minions, BuffDetail[] buffDetails) {
        for (Minion minion : minions) {
            minion.init();
            for (BuffDetail minionBuff : buffDetails) {
                minionBuff.init();
                if (minion.getId().equals(minionBuff.getId())) {
                    minion.addSpecialPowerBuff(minionBuff);
                }
            }
        }
    }

    private static void addBuffsToItem() {
        for (Item item : items) {
            item.init();
            for (BuffDetail itemBuff : itemBuff) {
                itemBuff.init();
                if (item.getId().equals(itemBuff.getId()))
                    item.addBuff(itemBuff);
                if (Integer.parseInt(item.getId()) == -Integer.parseInt(itemBuff.getId()))
                    item.setSpecialSituationBuff(itemBuff);
            }
        }
    }


    public static Spell[] getSpells() {
        return Arrays.copyOf(spells, spells.length);
    }

    public static Hero[] getHeroes() {
        return Arrays.copyOf(heroes, heroes.length);
    }

    public static Item[] getAllItems() {
        return Arrays.copyOf(items, items.length);
    }

    public static Minion[] getMinions() {
        return Arrays.copyOf(minions, minions.length);
    }

    public static ArrayList<Item> getCollectAbleItems() {
        ArrayList<Item> toReturn = new ArrayList<>();
        for (int i = 0; i < getAllItems().length; i++) {
            if (getAllItems()[i].getPrice() == 0)
                toReturn.add(getAllItems()[i]);
        }
        return toReturn;
    }

    public static Item[] getItems() {
        Item[] items = new Item[getAllItems().length - getCollectAbleItems().size()];
        int counter = 0;
        for (int i = 0; i < getAllItems().length; i++) {
            if (getAllItems()[i].getPrice() != 0) {
                items[counter] = getAllItems()[i];
                counter++;
            }
        }
        return Arrays.copyOf(items, items.length);
    }
}
