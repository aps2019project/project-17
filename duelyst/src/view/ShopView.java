package view;

import CardCollections.Shop;

public class ShopView extends View {

    public static void showCollection() {

    }

    public static void showShopProducts(Shop shop) {

    }

    public static void shopHelp() {
        System.out.println("exit");
        System.out.println("show collection");
        System.out.println("search [item name | card name]");
        System.out.println("search collection [item name | card name]");
        System.out.println("buy [card name | item name]");
        System.out.println("sell [card id | item id]");
        System.out.println("show");
        System.out.println("help");
    }
}
