package effects;

public class Card {
    protected String name;
    protected String id;
    protected int price;
    protected String userName;
    protected String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Card(String name, String id, int price) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.userName = null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static boolean equals(Card card, Card card1) {
        return card.getId().equals(card1.getId());
    }

    public void show() {
    }
}
