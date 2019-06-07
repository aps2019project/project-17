package Data;

import InstanceMaker.CardMaker;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Save {
    private final static String address = "duelyst//src//Data//Account.json";

    public static String save(Object object) throws IllegalAccessException, InstantiationException, IOException {
        StringBuilder stringBuilder = new StringBuilder("{\n");
        Class accountClass = object.getClass();
        Field[] fields = accountClass.getDeclaredFields();
        ArrayList<String> strings = new ArrayList<>();
        int counter = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(object) != null && !field.get(object).toString().contains(".")) {
                if (field.get(object) instanceof String || field.get(object) instanceof Enum)
                    strings.add(counter, "\"" + field.getName() + "\": \"" + field.get(object) + "\"");
                else
                    strings.add(counter, "\"" + field.getName() + "\": " + field.get(object).toString());
                counter++;
            } else if (field.get(object) != null) {
                String s = "\"" + field.getName() + "\": ";
                s += save(field.get(object));
                strings.add(s);
                counter++;
            }
        }
        for (int i = 0; i < strings.size(); i++) {
            stringBuilder.append(strings.get(i));
            if (i != strings.size() - 1)
                stringBuilder.append(",\n");
            else
                stringBuilder.append("\n");
        }
        stringBuilder.append("}");
        writeInJson(address, stringBuilder.toString());
        return stringBuilder.toString();
    }


    public static Account[] loadAccount() throws IOException {
        Gson gson = new Gson();
        Account[] accounts;
        accounts = gson.fromJson(CardMaker.jsonReader(address), Account[].class);
        return accounts;

    }

    public static void writeInJson(String address, String data) throws IOException {
        BufferedWriter bufferedWriter;
        bufferedWriter = new BufferedWriter(new FileWriter(address));
        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();

    }

    public static void save(Player player) {

    }
}
