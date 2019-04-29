package view;

import Data.Account;
import CardCollections.*;
import effects.Card;
import effects.Item;
import effects.Minion;
import effects.Spell;

public class CollectionView extends View {

    public static void showUserCollection(Account account) {
        System.out.println("Heroes:");

    }

    public static void showAllDecks(Account account) {

    }

    public static void showDeck(Deck deck) {
        System.out.print("Heroes:");
        if(deck.getHero()!=null){
            System.out.println();
            System.out.print("1 : ");
            deck.getHero().show();
        }
        System.out.print("Items :");
        if(deck.getItem()!=null){
            System.out.println();
            System.out.print("1 : ");
            deck.getItem().show();
        }
        System.out.println("Cards : ");
        int counter=1;
        for (Card card: deck.getCards()) {
            System.out.print(counter+" : ");
            if(card instanceof Minion){
                System.out.print("Type : Minion - ");
                card.show();//todo does it work correctly?
            }else if(card instanceof Spell){
                System.out.print("Type : Spell - ");
                card.show();//todo does it work correctly?
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
