package CardCollections;

public class Item {
    private String name;
    private int itemID;
    private String desc;

    public Item(String name, int itemID) {
        this.name = name;
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public int getItemID() {
        return itemID;
    }

    public String getDesc() {
        return desc;
    }
}
