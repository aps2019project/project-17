package view;

import CardCollections.Shop;

public class ShopView extends View {

    public static void showCollection() {

    }

    public static void showShopProducts(Shop shop) {

    }

    public static void shopHelp() {
        System.out.println("command for exit ->  \"exit\"");
        System.out.println("command for show collection  ->  \"show collection\"");
        System.out.println("command for searching in the shop  ->  \"search [item name | card name]\"");
        System.out.println("command for search in collection  ->  \"search collection [item name | card name]\"");
        System.out.println("command for buy  ->  \"buy [card name | item name]\"");
        System.out.println("command for sell  ->  \"sell [card id | item id]\"");
        System.out.println("command for shoe  ->  \"show\"");
        System.out.println("help");
    }
}
