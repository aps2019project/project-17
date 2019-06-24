package Data;

import CardCollections.Deck;
import Cards.Hero;
import Cards.Item;
import Cards.Minion;
import Cards.Spell;
import com.google.gson.Gson;
import controller.InstanceType;

import java.io.*;
import java.util.ArrayList;

import static InstanceMaker.CardMaker.addEffectToCard;
import static InstanceMaker.CardMaker.addEffectToItem;

public class Save {
    private final static String accountAddress = "duelyst//src//Data//Accounts.txt";
    public static final String address = "duelyst//src//InstanceMaker//";
    public static final String addressOfHero = address + "Hero.json";
    public static final String addressOfItem = address + "Item.json";
    public static final String addressOfMinion = address + "Minion.json";
    public static final String addressOfSpell = address + "Spell.json";

    static void saveAccount(Account account) {
        Gson gson = new Gson();
        String address = "Accounts//" + account.getUserName() + ".json";
        try {
            FileWriter fileWriter = new FileWriter(address);
            gson.toJson(account, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            FileWriter accountWriter = new FileWriter(accountAddress, true);
            accountWriter.append(",");
            accountWriter.append(account.getUserName());
            accountWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Account> loadAccounts() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(accountAddress);
            int c;
            while ((c = fileReader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            String[] userNames = stringBuilder.toString().split(",");
            for (String userName : userNames) {
                if (!userName.equals(""))
                    accounts.add(loadAccount("Accounts//" + userName + ".json"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private static Account loadAccount(String address) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonReader(address), Account.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void exportDeck(Deck deck) {
        Gson gson = new Gson();
        try {
            FileWriter fileWriter = new FileWriter(deck.getName() + ".json");
            gson.toJson(deck, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Deck importDeck(String address) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonReader(address), Deck.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static void writeInJson(String address, String data) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(address));
        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static String jsonReader(String fileAddress) throws IOException {
        FileReader fileReader = new FileReader(fileAddress);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        StringBuilder jsonString = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }
        fileReader.close();
        return jsonString.toString();
    }

    public static Object[] loadInstance(InstanceType instanceType) throws IOException {
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
}
