package view;

import CardCollections.Hand;
import Data.Player;
import GameGround.*;

public class BattleView extends View {

    public static void showGameInfo(Battle battle) {

    }

    public static void showMyMinions(Battle battle) {
        Player me = battle.getPlayerOne();
    }

    public static void showOpponentMinions(Battle battle) {
        Player opponent = battle.getPlayerTwo();
    }

    //todo maybe this method needs input args
    public static void showCardInfo() {

    }

    public static void showHand(Hand hand) {

    }

    public static void showCollectables() {

    }

    public static void showInfo() {

    }

    public static void showNextCard() {

    }


}
