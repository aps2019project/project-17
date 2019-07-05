import io.joshworks.restclient.http.Unirest;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionDataBaseDetail {
    public static final String BASE_ADDRESS;
    public static int PORT;
    public static final String INIT;
    public static final String PUT;
    public static final String GET_ALL_KEYS;
    public static final String GET;
    public static final String GET_ALL_VALUES;
    public final static String DEL;
    public final static String ACCOUNT_DB = "account_data_base";
    public final static String LOGGED_IN_DB = "logged_in_data_base";

    static {
        try {
            FileReader fileReader = new FileReader("duelyst/Server/connection.config");
            StringBuilder stringBuilder = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1)
                stringBuilder.append((char) c);
            Pattern pattern = Pattern.compile("port: (?<port>\\d+)");
            Matcher matcher = pattern.matcher(stringBuilder.toString());
            int port;
            if (matcher.find())
                port = Integer.parseInt(matcher.group("port"));
            else
                port = 8080;
            PORT = port;
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE_ADDRESS = "http://127.0.0.1:" + "8080" + "/";
        INIT = BASE_ADDRESS + "init_DB";
        PUT = BASE_ADDRESS + "put";
        GET_ALL_KEYS = BASE_ADDRESS + "get_all_keys";
        GET = BASE_ADDRESS + "get";
        GET_ALL_VALUES = BASE_ADDRESS + "get_all_values";
        DEL = BASE_ADDRESS + "del_from_DB";
    }

    public static void initializeServer(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        System.out.println(Unirest.post(ConnectionDataBaseDetail.INIT).fields(map).asString().getStatus());
    }
}
