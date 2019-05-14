package Data;

import CardCollections.Deck;
import GameGround.Battle;
import effects.Minion;

import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {
    private static AI currentAIPlayer;
    private static AI AIModeKH;
    private static AI AIModeCF;
    private static AI AIModeHF;

    public static void initializeAIStory() {
        AIModeKH = new AI("AI Mode KH", Deck.getDeckKillHero());
        AIModeHF = new AI("AI MODE HF", Deck.getDeckHoldFlag());
        AIModeCF = new AI("AI MODE CF", Deck.getDeckCaptureFlag());
    }

    public AI(String userName, Deck deck) {
        super(userName, deck);
        currentAIPlayer = this;
    }

    public static void setAiPlayer(MODE mode) {
        switch (mode) {
            case KH:
                currentAIPlayer = AIModeKH;
                break;
            case Hf:
                currentAIPlayer = AIModeHF;
                break;
            case CF:
                currentAIPlayer = AIModeCF;
        }
        currentAIPlayer.isPlayerReadyForBattle();
    }

    public static AI getCurrentAIPlayer() {
        return currentAIPlayer;
    }

    public static Player getAIModeKH() {
        return AIModeKH;
    }

    public static Player getAIModeCF() {
        return AIModeCF;
    }

    public static Player getAIModeHF() {
        return AIModeHF;
    }

    public void actionTurn() {
        if (Battle.getCurrentBattle() == null)
            return;
        StringBuilder toReturn = new StringBuilder();
        int counters = Math.abs(new Random().nextInt(3));
        Battle battle = Battle.getCurrentBattle();
        for (int i = 0; i < counters; i++) {
            int r = Math.abs(new Random().nextInt() % 4);
            switch (r) {
                default:
                    if (Battle.getCurrentBattle() == null)
                        return;
                case 0:
                    moveAI(toReturn, battle);
                    break;
                case 1:
                    insertAI(toReturn, battle);
                    break;
                case 2:
                    attackAI(toReturn, battle);
                    break;
                case 3:
                    specialPowerAI(toReturn, battle);
                    break;
            }
        }
        System.out.println(toReturn);
    }

    private void specialPowerAI(StringBuilder toReturn, Battle battle) {
        int x;
        int y;
        x = new Random().nextInt() % 5;
        y = new Random().nextInt() % 9;
        while (x <= 0 || y <= 0) {
            x = new Random().nextInt() % 5;
            y = new Random().nextInt() % 9;
        }
        battle.useSpecialPower(x, y);
        toReturn.append("AI decided to use special power\n");
    }

    private void attackAI(StringBuilder toReturn, Battle battle) {
        int randomToChoose = Math.abs(new Random().nextInt() % battle.minionsOfCurrentPlayer(battle.getPlayerOne()).size());
        String s;
        for (int i = 0; i < 4; i++) {
            s = battle.attack(battle.minionsOfCurrentPlayer(battle.getPlayerOne()).get(randomToChoose).getId(), false, null);
            if (s.contains("attacked")) {
                toReturn.append("AI decided to attack").append(battle.minionsOfCurrentPlayer(battle.theOtherPlayer()).get(randomToChoose).getName()).append("and ".concat(s).concat("\n"));
                return;
            }
            randomToChoose = Math.abs(new Random().nextInt() % battle.minionsOfCurrentPlayer(battle.theOtherPlayer()).size());
        }
        toReturn.append("AI decided to attack but failed!\n");
    }

    private void insertAI(StringBuilder toReturn, Battle battle) {
        int randomToChoose = 1;
        int x = 1;
        int y = 1;
        String s;
        for (int i = 0; i < 4; i++) {
            s = battle.insertingCardFromHand(currentAIPlayer.getHand().getCards().get(randomToChoose).getName(), x, y);
            if (s.contains("successfully")) {
                toReturn.append("AI decided to insert ").append(currentAIPlayer.getHand().getCards().get(randomToChoose).getName()).append(" in ").append(x).append(" - ").append(y).append(" and then ".concat(s).concat("\n"));
                return;
            }
            randomToChoose = Math.abs(new Random().nextInt() % (currentAIPlayer).getHand().getCards().size());
            x = Math.abs(new Random().nextInt() % 5) + 1;
            y = Math.abs(new Random().nextInt() % 9) + 1;
        }
        toReturn.append("AI decided to insert a card but failed!\n");
    }

    private void moveAI(StringBuilder toReturn, Battle battle) {
        int randomToChoose;
        int x;
        int y;
        String s;
        if (battle.getSelectedCard() == null) {
            ArrayList<Minion> minions = battle.minionsOfCurrentPlayer(currentAIPlayer);
            randomToChoose = Math.abs(new Random().nextInt() % minions.size());
            battle.selectCardOrItem(minions.get(randomToChoose).getId());
            toReturn.append("AI selected ").append(battle.getSelectedCard().getName()).append("\n");
        }
        for (int i = 0; i < 4; i++) {
            x = ((Minion) battle.getSelectedCard()).getXCoordinate() + new Random().nextInt() % 2;
            y = ((Minion) battle.getSelectedCard()).getYCoordinate() + new Random().nextInt() % 2;
            s = battle.movingCard(x, y);
            if (s.contains("moved")) {
                toReturn.append("AI decided to move ").append(battle.getSelectedCard().getName()).append(" into ").append(x).append(" - ").append(y).append(" and ".concat(s).concat("\n"));
                return;
            }
        }
        toReturn.append("AI decided to move a card but failed!\n");
    }
}
