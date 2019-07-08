package Client;

import com.google.gson.Gson;

import java.io.EOFException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Client.Client.getChatDetails;
import static Client.Client.getCommands;

public class Reader implements Runnable {
    private SocketDetail socketDetail;
    private boolean condition = true;

    public Reader(SocketDetail socketDetail) {
        this.socketDetail = socketDetail;
    }

    private void read() {
        try {
            Gson gson = new Gson();
            String data = (String) socketDetail.objectInputStream.readObject();
            System.err.println("read " + data);
            Message message = gson.fromJson(data, Message.class);
            Pattern patternForChat = Pattern.compile("sender: (?<sender>\\w+) message (?<message>(\\w| )+)");
            Matcher matcherForChat = patternForChat.matcher(message.getData());
            if (matcherForChat.matches()) {
                ChatDetail chatDetail = new ChatDetail(matcherForChat.group("message"), matcherForChat.group("sender"));
                getChatDetails().add(chatDetail);
                ChatRoom.updateScrollPane();
                return;
            }
            getCommands().put(message);
        } catch (EOFException e) {
            try {
                socketDetail.socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (condition)
            read();
    }

    public void setConditionFalse() {
        this.condition = false;
    }
}
