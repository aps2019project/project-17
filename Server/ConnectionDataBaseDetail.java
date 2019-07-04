package Server;


import io.joshworks.restclient.http.HttpResponse;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionDataBaseDetail {
    public static final String BASE_ADDRESS;
    public static int PORT;
    public static final String INIT;
    public static final String PUT;
    public static final String GET_ALL_KEYS;
    public static HttpResponse<String> response = null;

    static {
        try {
            FileReader fileReader = new FileReader("Server/connection.config");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE_ADDRESS = "http://127.0.0.1:" + PORT + "/";
        INIT = BASE_ADDRESS + "init_DB";
        PUT = BASE_ADDRESS + "put";
        GET_ALL_KEYS = BASE_ADDRESS + "get_all_keys";
    }
}
