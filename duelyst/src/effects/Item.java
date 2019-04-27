package effects;

public class Item {
    private String name;
    private String id;
    private String desc;
    private int price;

    public Item(String name, String id, int price) {
        this.name = name;
        this.id = id;
        this.price = price;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void showItem() {
        System.out.println("Name : " + this.name + " - Desc : " + this.desc);
    }

    public int getPrice() {
        return price;
    }
}
