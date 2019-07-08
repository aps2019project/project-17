package Client;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionDetail {
    public static int PORT;
    public static String IP = "localhost";
    static {
        try {
            FileReader fileReader = new FileReader("connection.config");
            StringBuilder stringBuilder = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1)
                stringBuilder.append((char) c);
            Pattern pattern = Pattern.compile("port: (?<port>\\d+)");
            Matcher matcher = pattern.matcher(stringBuilder.toString());
            int port=0;
            if (matcher.find())
                port = Integer.parseInt(matcher.group("port"));
//            else
//                port = 8080;
            PORT = port;
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}