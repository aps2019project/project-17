package Data;

import Cards.Hero;
import Cards.Item;
import Cards.Minion;
import Cards.Spell;
import com.google.gson.Gson;
import controller.InstanceType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static InstanceMaker.CardMaker.addEffectToCard;
import static InstanceMaker.CardMaker.addEffectToItem;

public class Save {
    private final static String accountAddress = "duelyst//src//Data//Account.json";
    public static final String address = "duelyst//src//InstanceMaker//";
    public static final String addressOfHero = address + "Hero.json";
    public static final String addressOfItem = address + "Item.json";
    public static final String addressOfMinion = address + "Minion.json";
    public static final String addressOfSpell = address + "Spell.json";

    public static void save(Object object, InstanceType instanceType) throws IOException {
        Gson gson = new Gson();
        String address = "";
        ArrayList<Object> data = new ArrayList<>();
        switch (instanceType) {
            case ACCOUNT:
                Account[] accounts = (Account[]) loadInstance(InstanceType.ACCOUNT);
                if (accounts != null)
                    data.addAll(Arrays.asList(accounts));
                data.add(object);
                address = accountAddress;
                break;
        }
        try {
            FileWriter fileWriter = new FileWriter(address);
            gson.toJson(data, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static String save(Object object) throws IllegalAccessException {
//        StringBuilder stringBuilder = new StringBuilder("{\n");
//        Class accountClass = object.getClass();
//        Field[] fields = accountClass.getDeclaredFields();
//        ArrayList<String> strings = new ArrayList<>();
//        int counter = 0;
//        for (Field field : fields) {
//            field.setAccessible(true);
//            if (field.get(object) != null && !field.get(object).toString().contains(".")) {
//                if (field.get(object) instanceof String || field.get(object) instanceof Enum)
//                    strings.add(counter, "\"" + field.getName() + "\": \"" + field.get(object) + "\"");
//                else
//                    strings.add(counter, "\"" + field.getName() + "\": " + field.get(object).toString());
//                counter++;
//            } else if (field.get(object) != null) {
//                String s = "\"" + field.getName() + "\": ";
//                s += save(field.get(object));
//                strings.add(s);
//                counter++;
//            }
//        }
//        for (int i = 0; i < strings.size(); i++) {
//            stringBuilder.append(strings.get(i));
//            if (i != strings.size() - 1)
//                stringBuilder.append(",\n");
//            else
//                stringBuilder.append("\n");
//        }
//        stringBuilder.append("}");
//        return stringBuilder.toString();
//    }


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
            case ACCOUNT:
                Account[] accounts;
                accounts = gson.fromJson(jsonReader(accountAddress), Account[].class);
                return accounts;
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
