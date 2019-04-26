package view;

import Data.Account;
import CardCollections.*;

public class CollectionView extends View {

    public static void showUserCollection(Account account) {

    }

    public static void showAllDecks(Account account) {

    }

    public static void showDeck(Deck deck) {

    }

    public static void collectionHelp() {
        System.out.println("exit");
        System.out.println("show");
        System.out.println("search [card name|item name]");
        System.out.println("save");
        System.out.println("create deck [deck name]");
        System.out.println("delete deck [deck name]");
        System.out.println("add [card id | hero id] from deck [deck name]");
        System.out.println("validate deck [deck name]");
        System.out.println("select deck [deck name]");
        System.out.println("show all decks");
        System.out.println("show deck [deck name]");
        System.out.println("help");
    }
}
