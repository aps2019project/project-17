package controller;

import com.google.gson.Gson;
import effects.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public static void creation() {
        try {
            spells = (Spell[]) creatingInstance(InstanceType.SPELL);
            spellBuff = (BuffDetail[]) creatingInstance(InstanceType.BUFF);
            heroes = (Hero[]) creatingInstance(InstanceType.HERO);
            heroBuff = (BuffDetail[]) creatingInstance(InstanceType.HERO_BUFF);
            items = (Item[]) creatingInstance(InstanceType.ITEM);
            itemBuff = (BuffDetail[]) creatingInstance(InstanceType.ITEM_BUFF);
            minions = (Minion[]) creatingInstance(InstanceType.MINION);
            minionBuff = (BuffDetail[]) creatingInstance(InstanceType.MINION_BUFF);

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

    private void addBuffsToSpell() {
        for (Spell spell : spells) {
            for (BuffDetail spellBuff : spellBuff) {
                if (spell.getId().equals(spellBuff.getId())) {
                    spell.addBuff(spellBuff);
                }
            }
        }
    }
}
