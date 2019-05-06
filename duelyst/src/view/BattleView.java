package view;

import CardCollections.Hand;
import Data.AI;
import GameGround.*;
import effects.*;

import java.util.ArrayList;

public class BattleView extends View {

    public static void showBattleMenu() {
        System.out.println("Please Select the type you want to play (1/2):");
        System.out.println("1.Single Player");
        System.out.println("2.Multi player");
    }

    public static void showGameStateMenu() {
        System.out.println("please choose game type:");
        System.out.println("1.story");
        System.out.println("2.custom game");
    }

    public static void showStoryMode() {
        AI.initializeAIStory();
        System.out.println("1.Kill Hero : " + AI.getAIModeKH().getMainDeck().getHero().getName());
        System.out.println("2.Hold Flag : " + AI.getAIModeHF().getMainDeck().getHero().getName());
        System.out.println("3.Capture Flag : " + AI.getAIModeCF().getMainDeck().getHero().getName());
    }

    public static void showCustomMode() {
        System.out.println("1.Kill Hero(KH)");
        System.out.println("2.Hold Flag(HF)");
        System.out.println("3.Capture Flag(CF)");
    }

    public static void showGameInfo(Battle battle) {
        System.out.println(battle.showGameInfo());
    }

    public static void showMyMinions(Battle battle) {
        ArrayList<Minion> minions = battle.showMyMinions();
        for (Minion minion : minions) {
            System.out.printf("%s : %s, health:  %d, location : [%d,%d], power : [%d]", minion.getId(), minion.getName(), minion.getHealthPoint(), minion.getXCoordinate(), minion.getYCoordinate(), minion.getAttackPower());
        }

    }

    public static void showOpponentMinions(Battle battle) {
        ArrayList<Minion> minions = battle.showOpponentMinion();
        for (Minion minion : minions) {
            System.out.printf("%s : %s, health:  %d, location : [%d,%d], power : [%d]", minion.getId(), minion.getName(), minion.getHealthPoint(), minion.getXCoordinate(), minion.getYCoordinate(), minion.getAttackPower());
        }
    }

    public static void showCardInfo(Battle battle, String cardID) {
        Card card = battle.returnCard(cardID);

        if (card instanceof Hero) {
            System.out.println("HERO:");
            System.out.printf("Name: %s\nCost : %d\nDesc : %s", card.getName(), card.getPrice(), card.getDesc());
            return;
        }
        if (card instanceof Minion) {
            String attackTpe = "Can't have this ability";

            if (((Minion) card).getAttackType().equals(AttackType.COMBO))
                attackTpe = "have this ability";
            System.out.println("Minion:");
            System.out.printf("Name: %s\nHP: %d  AP: %d  MP: %d\nRange: %d\nCombo-Ability: %s\nCost: %d\nDesc: %s", card.getName(), ((Minion) card).getHealthPoint(), ((Minion) card).getAttackPower(), ((Minion) card).getManaPoint(), ((Minion) card).getAttackRange(), attackTpe, card.getPrice(), card.getDesc());
            return;
        }
        if (card instanceof Spell) {
            System.out.println("Spell:");
            System.out.printf("Name: %s\nMP: %d\nCost: %d\nDesc: %s", card.getName(), ((Spell) card).getManaPoint(), card.getPrice(), card.getDesc());
        }
    }

    public static void showHand(Battle battle) {
        Hand hand = battle.showHand();
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card card = hand.getCards().get(i);
            String type;
            if (card instanceof Minion)
                type = "Minion";
            else type = "Spell";
            System.out.printf("name: %s, type: %s\n", card.getName(), type);
        }
        Card card = battle.whoseTurn().getNextCard();
        String type = "Minion";
        if (card instanceof Spell)
            type = "Spell";
        System.out.printf("next card ->  name : %s , type : %s", card.getName(), type);
    }

    public static void showCollectAbles() {
        System.out.println(Battle.getCurrentBattle().showCollectAble());
    }

    public static String showInfo(Battle battle) {
        return battle.showInfoOFItem();
    }

    public static void showNextCard() {
        System.out.println(Battle.getCurrentBattle().showNextCard());
    }

    public static void battleHelp() {
    }


}
