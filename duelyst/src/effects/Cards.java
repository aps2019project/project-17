package effects;

public class Cards {
    private String name;
    private String id;
    private int price;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }


    public Cards(String name, String id, int price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }
}
