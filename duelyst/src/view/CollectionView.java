package view;

import Data.Account;
import CardCollections.*;
import effects.*;

public class CollectionView extends View {

    public static void showUserCollection(Account account) {
        System.out.print("Heroes:");
        if (account.getCollection().getCollectionHeros() != null) {
            int counter = 1;
            for (Hero hero : account.getCollection().getCollectionHeros()) {
                System.out.println();
                System.out.print(counter+" : ");
                hero.show();
                counter++;
            }
        }
        System.out.print("Items:");
        if(account.getCollection().getItems()!=null){
            int counter=1;
            for (Item item:account.getCollection().getItems()) {
                System.out.println();
                System.out.println(counter+" : ");
                item.show();
                counter++;
            }
        }
        System.out.print("Cards:");
        if(account.getCollection().getCards()!=null){
            int counter=1;
            for (Spell spell:account.getCollection().getCollectionSpells()) {
                System.out.println();
                System.out.print(counter+" : Type : Spell - ");
                spell.show();
                counter++;
            }
            for (Minion minion:account.getCollection().getCollectionMinion()) {
                System.out.println();
                System.out.println(counter+" : Type : Minion - ");
                minion.show();
                counter++;
            }
        }

    }

    public static void showAllDecks(Account account) {
        int counter=1;
        for (Deck deck:account.getCollection().getDecks()) {
            System.out.println(counter+" : "+deck.getName());
            showDeck(deck);
            counter++;
        }
    }

    public static void showDeck(Deck deck) {
        System.out.print("Heroes:");
        if (deck.getHero() != null) {
            System.out.println();
            System.out.print("1 : ");
            deck.getHero().show();
        }
        System.out.print("Items :");
        if (deck.getItem() != null) {
            System.out.println();
            System.out.print("1 : ");
            deck.getItem().show();
        }
        System.out.println("Cards : ");
        int counter = 1;
        for (Card card : deck.getCards()) {
            System.out.print(counter + " : ");
            if (card instanceof Spell) {
                System.out.print("Type : Spell - ");
                card.show();
            } else if (card instanceof Minion) {
                System.out.print("Type : Minion - ");
                card.show();
            }
            counter++;
        }

    }

    public static void collectionHelp() {
        System.out.println("exit");
        System.out.println("show");
        System.out.println("search [card name|item name]");
        System.out.println("accountSave");
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
