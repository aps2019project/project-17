package InstanceMaker;

import Cards.*;
import Data.Account;
import Effects.CellEffects.FireCell;
import Effects.CellEffects.HolyCell;
import Effects.CellEffects.Poison;
import Effects.Effect;
import Effects.MinionEffects.*;
import Effects.Player.ChangeMana;
import Effects.SpecialSituationBuff;
import Effects.enums.BuffType;
import com.google.gson.Gson;
import controller.InstanceType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static Data.Save.jsonReader;
import static Data.Save.writeInJson;

public class CardMaker {
    private static final String address = "duelyst//src//InstanceMaker//";
    private static final String addressOfHero = address + "Hero.json";
    private static final String addressOfItem = address + "Item.json";
    private static final String addressOfMinion = address + "Minion.json";
    private static final String addressOfSpell = address + "Spell.json";
    private static Spell[] spells;
    private static Minion[] minions;
    private static Hero[] heroes;
    private static Item[] items;

    public static void creation() {
        try {
            spells = (Spell[]) loadInstance(InstanceType.SPELL);
            heroes = (Hero[]) loadInstance(InstanceType.HERO);
            items = (Item[]) loadInstance(InstanceType.ITEM);
            minions = (Minion[]) loadInstance(InstanceType.MINION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object[] loadInstance(InstanceType instanceType) throws IOException {
        Gson gson = new Gson();

        switch (instanceType) {
            case HERO:
                Hero[] heroes;
                heroes = gson.fromJson(jsonReader(addressOfHero), Hero[].class);
                addEffectToCard(heroes);
                return heroes;
            case ITEM:
                Item[] items;
                items = gson.fromJson(jsonReader(addressOfItem), Item[].class);
                addEffectToItem(items);
                return items;
            case MINION:
                Minion[] minions;
                minions = gson.fromJson(jsonReader(addressOfMinion), Minion[].class);
                addEffectToCard(minions);
                return minions;
            case SPELL:
                Spell[] spells;
                spells = gson.fromJson(jsonReader(addressOfSpell), Spell[].class);
                addEffectToCard(spells);
                return spells;

        }
        return null;
    }


    public static void saveMinion(Minion newMinion) throws IOException {
        Minion[] loadedMinions;
        if (newMinion instanceof Hero) {
            loadedMinions = (Minion[]) loadInstance(InstanceType.HERO);
        } else
            loadedMinions = (Minion[]) loadInstance(InstanceType.MINION);
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
            if (minion instanceof Hero)
                stringBuilder.append("\"coolDown\": \"").append(((Hero) minion).getCoolDown()).append("\",\n");
            stringBuilder.append("\"desc\": \"").append(minion.getDesc()).append("\"\n");
            if (minions.indexOf(minion) != minions.size() - 1)
                stringBuilder.append("},\n");
            else
                stringBuilder.append("}");
        }
        stringBuilder.append("]");
        if (newMinion instanceof Hero)
            writeInJson(addressOfHero, stringBuilder.toString());
        else
            writeInJson(addressOfMinion, stringBuilder.toString());
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
        writeInJson(addressOfItem, stringBuilder.toString());
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
        writeInJson(addressOfSpell, stringBuilder.toString());
    }

    private static Effect[] loadEffects(String address) throws IOException {
        Gson gson = new Gson();
        String[] strings = address.split("//");
        String[] s = strings[strings.length - 1].split("\\.");
        String className = s[0].toUpperCase();
        className = className.replaceFirst("CELL", "_CELL");
        className = className.replaceFirst("CHANGEPROPERTIES", "POWER_BUFF");
        className = className.replaceFirst("MANA", "_MANA");
        className = className.replaceFirst("SPECIALSITUATIONBUFF", "SPECIAL_SITUATION_BUFF");
        BuffType buffType = BuffType.valueOf(className);
        switch (buffType) {
            case HOLY:
                Holy[] holy = new Holy[1];
                return gson.fromJson(jsonReader(address), holy.getClass());
            case WEAKNESS:
            case POWER_BUFF:
                ChangeProperties[] changeProperties = new ChangeProperties[1];
                return gson.fromJson(jsonReader(address), changeProperties.getClass());
            case STUN:
                Stun[] stuns = new Stun[1];
                return gson.fromJson(jsonReader(address), stuns.getClass());
            case DISARM:
                Disarm[] disarms = new Disarm[1];
                return gson.fromJson(jsonReader(address), disarms.getClass());
            case CLEAR:
                Clear[] clears = new Clear[1];
                return gson.fromJson(jsonReader(address), clears.getClass());
            case FIRE_CELL:
                FireCell[] fireCells = new FireCell[1];
                return gson.fromJson(jsonReader(address), fireCells.getClass());
            case POISON:
                Poison[] poisons = new Poison[1];
                return gson.fromJson(jsonReader(address), poisons.getClass());
            case HOLY_CELL:
                HolyCell[] holyCells = new HolyCell[1];
                return gson.fromJson(jsonReader(address), holyCells.getClass());
            case ANTI:
                Anti[] antis = new Anti[1];
                return gson.fromJson(jsonReader(address), antis.getClass());
            case CHANGE_MANA:
                ChangeMana[] changeManas = new ChangeMana[1];
                return gson.fromJson(jsonReader(address), changeManas.getClass());
            case SPECIAL_SITUATION_BUFF:
                SpecialSituationBuff[] specialSituationBuffs = new SpecialSituationBuff[1];
                return gson.fromJson(jsonReader(address), specialSituationBuffs.getClass());
        }
        return null;
    }

    public static <T extends Effect> void saveEffect(T newEffect) throws IOException {
        String[] names = newEffect.getClass().toString().split("\\.");
        String name = names[names.length - 1];
        String address = CardMaker.address + name + ".json";
        T[] loadedEffects = (T[]) loadEffects(address);
        ArrayList<T> effects = new ArrayList<>();
        if (loadedEffects != null) {
            effects.addAll(Arrays.asList(loadedEffects));
        }
        for (T effect : effects) {
            System.out.println(effect.getClass().toString());
        }
        effects.add(newEffect);
        StringBuilder stringBuilder = new StringBuilder("[");
        for (T effect : effects) {
            stringBuilder.append("{");
            stringBuilder.append("\"id\": \"").append(effect.getId()).append("\",\n");
            stringBuilder.append("\"startTime\": ").append(effect.getStartTime()).append(",\n");
            stringBuilder.append("\"endTime\": ").append(effect.getEndTime()).append(",\n");
            stringBuilder.append("\"isContinues\": ").append(effect.isContinues()).append(",\n");
            stringBuilder.append("\"targetRange\": \"").append(effect.getTargetRange()).append("\",\n");
            stringBuilder.append("\"targetType\": \"").append(effect.getTargetType()).append("\",\n");
            if (effect instanceof Anti)
                stringBuilder.append("\"buffType\": \"").append(((Anti) effect).getBuffType()).append("\",\n");
            if (effect instanceof ChangeProperties) {
                stringBuilder.append("\"changeHealthValue\": ").append(((ChangeProperties) effect).getChangeHealthValue()).append(",\n");
                stringBuilder.append("\"changePowerValue\": ").append(((ChangeProperties) effect).getChangePowerValue()).append(",\n");
                stringBuilder.append("\"returnEffect\": ").append(((ChangeProperties) effect).isReturnEffect()).append(",\n");
            }
            if (effect instanceof Holy)
                stringBuilder.append("\"holyBuffState\": ").append(((Holy) effect).getHolyBuffState()).append(",\n");
            if (effect instanceof ChangeMana)
                stringBuilder.append("\"manaChangeValue\": ").append(((ChangeMana) effect).getManaChangeValue()).append(",\n");
            if (effect instanceof SpecialSituationBuff)
                stringBuilder.append("\"specialSituation\": \"").append(((SpecialSituationBuff) effect).getSpecialSituation()).append("\",\n");
            stringBuilder.append("\"targetDetail\": \"").append(effect.getTargetDetail()).append("\"\n");
            if (effects.indexOf(effect) != effects.size() - 1)
                stringBuilder.append("},\n");
            else
                stringBuilder.append("}");
        }
        stringBuilder.append("]");
        writeInJson(address, stringBuilder.toString());
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

    public static Card[] getAllCards() {
        creation();
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(Arrays.asList(getSpells()));
        cards.addAll(Arrays.asList(getMinions()));
        cards.addAll(Arrays.asList(getHeroes()));
        Card[] cardsArray = new Card[cards.size()];
        cardsArray = cards.toArray(cardsArray);
        return cardsArray;
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

    private static ArrayList<Effect> getAllEffect() throws IOException {
        String address;
        ArrayList<Effect> effects = new ArrayList<>();
        Effect[] loadedEffects;
        for (BuffType value : BuffType.values()) {
            loadedEffects = null;
            switch (value) {
                case HOLY:
                    address = CardMaker.address + "Holy.json";
                    break;
                case WEAKNESS:
                    continue;
                case POWER_BUFF:
                    address = CardMaker.address + "ChangeProperties.json";
                    break;
                case STUN:
                    address = CardMaker.address + "Stun.json";
                    break;
                case DISARM:
                    address = CardMaker.address + "Disarm.json";
                    break;
                case CLEAR:
                    address = CardMaker.address + "Clear.json";
                    break;
                case FIRE_CELL:
                    address = CardMaker.address + "FireCell.json";
                    break;
                case POISON:
                    address = CardMaker.address + "Poison.json";
                    break;
                case HOLY_CELL:
                    address = CardMaker.address + "HolyCell.json";
                    break;
                case ANTI:
                    address = CardMaker.address + "Anti.json";
                    break;
                case CHANGE_MANA:
                    address = CardMaker.address + "ChangeMana.json";
                    break;
                case SPECIAL_SITUATION_BUFF:
                    address = CardMaker.address + "SpecialSituationBuff.json";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }
            loadedEffects = loadEffects(address);
            if (loadedEffects != null)
                effects.addAll(Arrays.asList(loadedEffects));
        }
        return effects;
    }

    private static void addEffectToCard(Card[] cards) throws IOException {
        ArrayList<Effect> effects = getAllEffect();
        Effect specialSituation;
        for (Card card : cards) {
            for (Effect effect : effects) {
                if (!card.getId().equals(effect.getId()))
                    continue;
                specialSituation = null;
                for (Effect secondEffect : effects) {
                    if (effect instanceof SpecialSituationBuff && Integer.parseInt(effect.getId()) == -Integer.parseInt(secondEffect.getId()))
                        specialSituation = secondEffect;
                }
                if (specialSituation != null && card instanceof Minion)
                    ((Minion) card).addSpecialSituationBuff(specialSituation);
                if (card instanceof Minion)
                    ((Minion) card).addSpecialPower(effect);
                if (card instanceof Spell)
                    ((Spell) card).addEffect(effect);
            }
        }

    }

    private static void addEffectToItem(Item[] items) throws IOException {
        ArrayList<Effect> effects = getAllEffect();
        Effect specialSituation;
        for (Item item : items) {
            for (Effect effect : effects) {
                if (!item.getId().equals(effect.getId()))
                    continue;
                specialSituation = null;
                for (Effect secondEffect : effects) {
                    if (effect instanceof SpecialSituationBuff && Integer.parseInt(effect.getId()) == -Integer.parseInt(secondEffect.getId()))
                        specialSituation = secondEffect;
                }
                if (specialSituation != null)
                    item.addSpecialSituationBuff(specialSituation);
                item.addEffect(effect);
            }
        }

    }

    public static String returnSearch(String name) {
        Card card = Account.getLoginUser().getShop().getCardFromName(name);
        if (card != null)
            return card.getId();
        Item item = Account.getLoginUser().getShop().getItemFromName(name);
        if (item != null)
            return item.getId();
        return "-1";
    }


}

