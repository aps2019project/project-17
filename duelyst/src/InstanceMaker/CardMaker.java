package InstanceMaker;

import Cards.Hero;
import Cards.Item;
import Cards.Minion;
import Cards.Spell;
import Effects.Effect;
import com.google.gson.Gson;
import controller.InstanceType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CardMaker {
    public static final String addressOfHero = "duelyst//src//InstanceMaker//Hero.json";
    public static final String addressOfItem = "duelyst//src//InstanceMaker//Item.json";
    public static final String addressOfMinion = "duelyst//src//InstanceMaker//Minion.json";
    public static final String addressOfSpell = "duelyst//src//InstanceMaker//Spell.json";
    private static Spell[] spells;
    private static Effect[] spellEffects;
    private static Minion[] minions;
    private static Effect[] minionsSpecialPower;
    private static Hero[] heroes;
    private static Effect[] heroesSpecialPower;
    private static Item[] items;
    private static Effect[] itemEffects;

    static void creation() {
        try {
            spells = (Spell[]) loadInstance(InstanceType.SPELL);
            heroes = (Hero[]) loadInstance(InstanceType.HERO);
            items = (Item[]) loadInstance(InstanceType.ITEM);
            minions = (Minion[]) loadInstance(InstanceType.MINION);
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

    private static Object[] loadInstance(InstanceType instanceType) throws IOException {
        Gson gson = new Gson();

        switch (instanceType) {
            case HERO:
                Hero[] heroes;
                heroes = gson.fromJson(jsonReader(addressOfHero), Hero[].class);
                return heroes;
            case ITEM:
                Item[] items;
                items = gson.fromJson(jsonReader(addressOfItem), Item[].class);
                return items;
            case MINION:
                Minion[] minions;
                minions = gson.fromJson(jsonReader(addressOfMinion), Minion[].class);
                return minions;
            case SPELL:
                Spell[] spells;
                spells = gson.fromJson(jsonReader(addressOfSpell), Spell[].class);
                return spells;
        }
        return null;
    }

    public static void saveMinion(Minion newMinion, Boolean isHero) throws IOException {
        Minion[] loadedMinions = (Minion[]) loadInstance(InstanceType.MINION);
        assert loadedMinions != null;
        ArrayList<Minion> minions = new ArrayList<>(Arrays.asList(loadedMinions));
        minions.add(newMinion);
        StringBuilder stringBuilder = new StringBuilder("[\n");
        for (Minion minion : minions) {
            stringBuilder.append("{");
            stringBuilder.append("\"name\": \"").append(minion.getName()).append("\",\n");
            stringBuilder.append("\"id\": \"").append(minion.getId()).append("\",\n");
            stringBuilder.append("\"price\": \"").append(minion.getPrice()).append("\",\n");
            stringBuilder.append("\"manaPoint\": \"").append(minion.getManaPoint()).append("\",\n");
            stringBuilder.append("\"healthPoint\": \"").append(minion.getHealthPoint()).append("\",\n");
            stringBuilder.append("\"attackPoint\": \"").append(minion.getAttackPoint()).append("\",\n");
            stringBuilder.append("\"minionType\": \"").append(minion.getMinionType()).append("\",\n");
            stringBuilder.append("\"attackRange\": \"").append(minion.getAttackRange()).append("\",\n");
            stringBuilder.append("\"distanceCanMove\": \"").append(minion.getDistanceCanMove()).append("\",\n");
            stringBuilder.append("\"maxRangeToInput\": \"").append(minion.getMaxRangeToInput()).append("\",\n");
            stringBuilder.append("\"attackType\": \"").append(minion.getAttackType()).append("\",\n");
            if (isHero)
                stringBuilder.append("\"coolDown\": \"").append(((Hero) minion).getCoolDown()).append("\",\n");
            stringBuilder.append("\"desc\": \"").append(minion.getDesc()).append("\"\n");
            if (minions.indexOf(minion) != minions.size() - 1)
                stringBuilder.append("},\n");
            else
                stringBuilder.append("}");
        }
        stringBuilder.append("]");
        BufferedWriter bufferedWriter;
        if (isHero)
            bufferedWriter = new BufferedWriter(new FileWriter(addressOfHero));
        else
            bufferedWriter = new BufferedWriter(new FileWriter(addressOfMinion));
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void saveItem(Item newItem) throws IOException {
        Item[] loadedItems = (Item[]) loadInstance(InstanceType.ITEM);
        assert loadedItems != null;
        ArrayList<Item> items = new ArrayList<>(Arrays.asList(loadedItems));
        items.add(newItem);
        StringBuilder stringBuilder = new StringBuilder("[");
        for (Item item : items) {
            stringBuilder.append("{");
            stringBuilder.append("\"name\": \"").append(item.getName()).append("\",\n");
            stringBuilder.append("\"id\": \"").append(item.getId()).append("\",\n");
            stringBuilder.append("\"price\": \"").append(item.getPrice()).append("\",\n");
            stringBuilder.append("\"desc\": \"").append(item.getDesc()).append("\"\n");
            if (items.indexOf(item) != items.size() - 1)
                stringBuilder.append("},\n");
            else
                stringBuilder.append("}");
        }
        stringBuilder.append("]");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(addressOfItem));
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void saveSpell(Spell newSpell) throws IOException {
        Spell[] loadedItems = (Spell[]) loadInstance(InstanceType.SPELL);
        assert loadedItems != null;
        ArrayList<Spell> spells = new ArrayList<>(Arrays.asList(loadedItems));
        spells.add(newSpell);
        StringBuilder stringBuilder = new StringBuilder("[");
        for (Spell spell : spells) {
            stringBuilder.append("{");
            stringBuilder.append("\"name\": \"").append(spell.getName()).append("\",\n");
            stringBuilder.append("\"id\": \"").append(spell.getId()).append("\",\n");
            stringBuilder.append("\"price\": \"").append(spell.getPrice()).append("\",\n");
            stringBuilder.append("\"manaPoint\": \"").append(spell.getManaPoint()).append("\",\n");
            stringBuilder.append("\"desc\": \"").append(spell.getDesc()).append("\"\n");
            if (spells.indexOf(spell) != spells.size() - 1)
                stringBuilder.append("},\n");
            else
                stringBuilder.append("}");
        }
        stringBuilder.append("]");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(addressOfSpell));
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
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
