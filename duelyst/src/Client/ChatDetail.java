package Client;

public class ChatDetail {
    private String message;
    private String sender;

    public ChatDetail(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}
