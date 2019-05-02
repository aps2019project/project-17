package view;

import CardCollections.Hand;
import Data.Player;
import GameGround.*;
import effects.*;

import java.util.ArrayList;

public class BattleView extends View {

    public static void showGameInfo(Battle battle) {
        System.out.println(battle.showGameInfo());
    }

    public static void showMyMinions(Battle battle) {
        ArrayList<Minion> minions = battle.showMyMinions();
        for (Minion minion : minions) {
            System.out.printf("%s : %s, health:  %d, location : [%d,%d], power : [%d]", minion.getId(), minion.getName(), minion.getHealthPoint(), minion.getxCoordinate(), minion.getyCoordinate(), minion.getAttackPower());
        }

    }

    public static void showOpponentMinions(Battle battle) {
        ArrayList<Minion> minions = battle.showOpponentMinion();
        for (Minion minion : minions) {
            System.out.printf("%s : %s, health:  %d, location : [%d,%d], power : [%d]", minion.getId(), minion.getName(), minion.getHealthPoint(), minion.getxCoordinate(), minion.getyCoordinate(), minion.getAttackPower());
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
            System.out.printf("name: %s, type: %s", card.getName(), type);
        }
    }

    public static void showCollectables() {

    }

    public static void showInfo() {
/**
 * it depends on the item that has been selected in the previous command(check doc page 20)
 */
    }

    public static void showNextCard() {

    }

    public static void battleHelp() {

    }


}
