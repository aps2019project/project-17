package view;

import Data.Account;
import Cards.Hero;
import Cards.Item;
import Cards.Minion;
import Cards.Spell;

public class ShopView extends View {

    public static void showAllProducts(Account account) {
        System.out.print("Heroes:");
        if (account.getShop().getShopHeroes() != null) {
            int counter = 1;
            for (Hero hero : account.getShop().getShopHeroes()) {
                System.out.println();
                System.out.print(counter + " : ");
                hero.show();
                System.out.println("Buy Cost : " + hero.getPrice());
                counter++;
            }
        }
        System.out.println();
        System.out.print("Items:");
        if (account.getShop().getItemsInShop() != null) {
            int counter = 1;
            for (Item item : account.getShop().getItemsInShop()) {
                System.out.println();
                System.out.println(counter + " : ");
                item.show();
                System.out.println("Buy Cost : " + item.getPrice());
                counter++;
            }
        }
        System.out.println();
        System.out.print("Cards:");
        if (account.getShop().getShopMinions() != null) {
            int counter = 1;
            for (Spell spell : account.getShop().getShopSpells()) {
                System.out.println();
                System.out.print(counter + " : Type : Spell - ");
                spell.show();
                System.out.println("Buy Cost : " + spell.getPrice());
                counter++;
            }
            for (Minion minion : account.getShop().getShopMinions()) {
                System.out.println();
                System.out.println(counter + " : Type : Minion - ");
                minion.show();
                System.out.println("Buy Cost : " + minion.getPrice());
                counter++;
            }
        }
        System.out.println();
    }

    public static void shopHelp() {
        System.out.println("command for exit  ->   \"exit\"");
        System.out.println("command for show collection   ->   \"show collection\"");
        System.out.println("command for searching in the shop   ->   \"search [item name | card name]\"");
        System.out.println("command for search in collection   ->   \"search collection [item name | card name]\"");
        System.out.println("command for buy   ->   \"buy [card name | item name]\"");
        System.out.println("command for sell   ->   \"sell [card id | item id]\"");
        System.out.println("command for shoe   ->   \"show\"");
        System.out.println("help");
    }
}
