package view;

import CardCollections.Hand;
import Cards.Card;
import Cards.Hero;
import Cards.Minion;
import Cards.Spell;
import Data.AI;
import Data.Player;
import GameGround.*;
import Effects.enums.AttackType;

import java.util.ArrayList;
import java.util.Random;

class BattleView extends View {

    static void showBattleMenu() {
        System.out.println("Please Select the type you want to play (1/2):");
        System.out.println("1.Single Player");
        System.out.println("2.Multi player");
    }

    static void showGameStateMenu() {
        System.out.println("please choose game type:");
        System.out.println("1.story");
        System.out.println("2.custom game");
    }

    static void showStoryMode() {
        AI.initializeAIStory();
        System.out.println("1.Kill Hero : " + AI.getAIModeKH().getMainDeck().getHero().getName());
        System.out.println("2.Hold Flag : " + AI.getAIModeHF().getMainDeck().getHero().getName());
        System.out.println("3.Capture Flag : " + AI.getAIModeCF().getMainDeck().getHero().getName());
    }

    static void showCustomMode() {
        System.out.println("1.Kill Hero(KH)");
        System.out.println("2.Hold Flag(HF)");
        System.out.println("3.Capture Flag(CF)");
    }

    static void showGameInfo(Battle battle) {
        System.out.println(battle.showGameInfo());
    }

    static void showMyMinions(Battle battle) {
        ArrayList<Minion> minions = battle.showMyMinions();
        if (minions == null || minions.size() == 0) {
            System.out.println("there isn't any player from you in cell");
            return;
        }
        for (Minion minion : minions) {
            System.out.printf("%s : %s, health:  %d, location : [%d,%d], power : [%d]\n", minion.getId(), minion.getName(), minion.getHealthPoint(), minion.getXCoordinate(), minion.getYCoordinate(), minion.getAttackPoint());
        }
    }

    static void showOpponentMinions(Battle battle) {
        ArrayList<Minion> minions = battle.showOpponentMinion();
        if (minions == null || minions.size() == 0) {
            System.out.println("there isn't any player from that player in cell");
            return;
        }
        for (Minion minion : minions) {
            System.out.printf("%s : %s, health:  %d, location : [%d,%d], power : [%d], mana: [%d]\n", minion.getId(), minion.getName(), minion.getHealthPoint(), minion.getXCoordinate(), minion.getYCoordinate(), minion.getAttackPoint(), minion.getManaPoint());
        }
    }

    static void showCardInfo(Battle battle, String cardID) {
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
            System.out.printf("Name: %s\nHP: %d  AP: %d  MP: %d\nRange: %d\nCombo-Ability: %s\nCost: %d\nDesc: %s", card.getName(), ((Minion) card).getHealthPoint(), ((Minion) card).getAttackPoint(), ((Minion) card).getManaPoint(), ((Minion) card).getAttackRange(), attackTpe, card.getPrice(), card.getDesc());
            return;
        }
        if (card instanceof Spell) {
            System.out.println("Spell:");
            System.out.printf("Name: %s\nMP: %d\nCost: %d\nDesc: %s", card.getName(), ((Spell) card).getManaPoint(), card.getPrice(), card.getDesc());
        }
    }

    static void showHand(Battle battle) {
        Hand hand = battle.showHand();
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card card = hand.getCards().get(i);
            String type = "Spell";
            int mana = 2;
            if (card instanceof Minion) {
                type = "Minion";
                mana = ((Minion) card).getManaPoint();
            } else if (card instanceof Spell){
                type = "Spell";
                mana = ((Spell) card).getManaPoint();
            }
            System.out.printf("name: %s, type: %s , mana: %d\n", card.getName(), type, mana);
        }
        Card card = battle.whoseTurn().getNextCard();
        if (card == null)
            return;
        String type = "Minion";
        if (card instanceof Spell)
            type = "Spell";
        System.out.printf("next card ->  name : %s , type : %s\n", card.getName(), type);
    }

    static void showCollectAbles() {
        System.out.println(Battle.getCurrentBattle().showCollectAble());
    }

    static String showInfo(Battle battle) {
        return battle.showInfoOFItem();
    }

    static void showNextCard() {
        System.out.println(Battle.getCurrentBattle().showNextCard());
    }

    static void battleHelp() {
        Player player = Battle.getCurrentBattle().whoseTurn();
        int n = new Random().nextInt() % player.getHand().getCards().size();
        while (n < 0) {
            n = new Random().nextInt() % player.getHand().getCards().size();
        }
        System.out.println("insert " + player.getHand().getCards().get(n).getName());
    }

    static void setItems() {
        System.out.println(Battle.getCurrentBattle().setItems());
    }
}
