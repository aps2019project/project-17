package Data;

import Appearance.ExceptionEndGame;
import CardCollections.Deck;
import GameGround.Battle;
import Cards.Minion;

import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {
    private static AI currentAIPlayer;
    private static AI AIModeKH;
    private static AI AIModeCF;
    private static AI AIModeHF;
    private static ArrayList<String> results = new ArrayList<>();

    public static void initializeAIStory() {
        AIModeKH = new AI("AI Mode KH", Deck.getDeckKillHero());
        AIModeHF = new AI("AI MODE HF", Deck.getDeckHoldFlag());
        AIModeCF = new AI("AI MODE CF", Deck.getDeckCaptureFlag());
    }

    public AI(String userName, Deck deck) {
        super(userName, deck);
        currentAIPlayer = this;
        currentAIPlayer.isPlayerReadyForBattle();
    }

    public static void setAiPlayer(MODE mode) {
        switch (mode) {
            case KH:
                currentAIPlayer = AIModeKH;
                break;
            case HF:
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

    public void actionTurn() throws ExceptionEndGame {
        // TODO: 2019-06-22
        results = new ArrayList<>();
        if (Battle.getCurrentBattle() == null)
            return;
        StringBuilder toReturn = new StringBuilder();
        Battle battle = Battle.getCurrentBattle();
        for (int i = 0; i < 6; i++) {
            int r = Math.abs(new Random().nextInt() % 3);
            switch (r) {
                case 0:
                    attackAI(toReturn, battle);
//                    insertAI(toReturn, battle);
                    break;
                case 1:
                    attackAI(toReturn, battle);
//                    moveAI(toReturn, battle);
                case 2:
                    attackAI(toReturn, battle);
            }
//            switch (r) {
//                default:
//                    if (Battle.getCurrentBattle() == null)
//                        return;
//                case 0:
//                    insertAI(toReturn, battle);
//                    break;
//                case 1:
//                    insertAI(toReturn, battle);
//                    break;
//                case 2:// TODO: 2019-06-14
////                    attackAI(toReturn, battle);
//                    insertAI(toReturn, battle);
//                    break;
//                case 3:
////                    specialPowerAI(toReturn, battle);
//                    insertAI(toReturn, battle);
//                    break;
//                case 4:
////                    useItemAI(toReturn, battle);
//                    insertAI(toReturn, battle);
//                    break;
//            }
        }
        System.out.println(toReturn);
    }

    private void useItemAI(StringBuilder toReturn, Battle battle) {
        if (battle.getSelectedItem() == null) {
            if (currentAIPlayer.getCollectAbleItems().size() == 0)
                return;
            int r1 = Math.abs(new Random().nextInt() % currentAIPlayer.getCollectAbleItems().size());
            battle.selectCardOrItem(currentAIPlayer.getCollectAbleItems().get(r1).getId());
            toReturn.append("AI selected item").append(currentAIPlayer.getCollectAbleItems().get(r1).getName()).append("\n");
        }
        int x = Math.abs(new Random().nextInt() % 5) + 1;
        int y = Math.abs(new Random().nextInt() % 9) + 1;
//        battle.useItem(x, y);
        toReturn.append("item decided to use ").append(battle.getSelectedItem().getName()).append(" in ").append(x).append(" - ").append(y).append("\n");
    }

    private void specialPowerAI(StringBuilder toReturn, Battle battle) {
        int x;
        int y;
        x = Math.abs(new Random().nextInt() % 5) + 1;
        y = Math.abs(new Random().nextInt() % 9) + 1;
//        battle.useSpecialPower(x, y);
        toReturn.append("AI decided to use special power\n");
    }

    private void attackAI(StringBuilder toReturn, Battle battle) throws ExceptionEndGame {
        Minion minion = selectCard(toReturn);
        battle.setSelectedCard(battle.getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate()));
        String s;
        for (int i = 0; i < 5; i++) {
            Minion minionTarget = battle.targetForAttackAI();
            if (minionTarget == null){
                minion = selectCard(toReturn);
                battle.setSelectedCard(battle.getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate()));
                continue;
            }
            s = battle.attack(minionTarget.getId(), false, null);
            System.out.println(s);
            if (s.contains("attacked")) {
                results.add("attack " + battle.getSelectedCard().getName() + " " + minionTarget.getXCoordinate() + " " + minionTarget.getYCoordinate());
                toReturn.append("AI decided to attack ").append(minionTarget.getName()).append(" and ".concat(s).concat("\n"));
                return;
            }
            minion = selectCard(toReturn);
            battle.setSelectedCard(battle.getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate()));
        }
        toReturn.append("AI decided to attack but failed!\n");
    }

    private void insertAI(StringBuilder toReturn, Battle battle) throws ExceptionEndGame {
        int randomToChoose = 0;
        int x = 3;
        int y = 8;
        String s;
        if (battle.getSelectedCard() == null) {
            Minion selectedMinion = selectCard(toReturn);
            battle.setSelectedCard(Battle.getCurrentBattle().getCellFromBoard(selectedMinion.getXCoordinate(), selectedMinion.getYCoordinate()));
        }
        for (int i = 0; i < 5; i++) {
            if (currentAIPlayer.getHand().getCards().size() <= 0)
                break;
            Minion selectedMinion = selectCard(toReturn);
            if (selectedMinion == null)
                return;
            battle.setSelectedCard(Battle.getCurrentBattle().getCellFromBoard(selectedMinion.getXCoordinate(), selectedMinion.getYCoordinate()));
            String name = currentAIPlayer.getHand().getCards().get(randomToChoose).getName();
            s = battle.insertingCardFromHand(currentAIPlayer.getHand().getCards().get(randomToChoose).getName(), x, y);
            if (s.contains("successfully")) {
                results.add("insert " + name);
                toReturn.append("AI decided to insert ").append(name).append(" in ").append(x).append(" - ").append(y).append(" and then ".concat(s).concat("\n"));
                return;
            }
            randomToChoose = Math.abs(new Random().nextInt((currentAIPlayer).getHand().getCards().size()));
            while (randomToChoose >= currentAIPlayer.getHand().getCards().size())
                randomToChoose = Math.abs(new Random().nextInt((currentAIPlayer).getHand().getCards().size()));
            x = Math.abs((Math.abs(((Minion) battle.getSelectedCard()).getXCoordinate() + new Random().nextInt(3))) % 4) + 1;
            y = Math.abs((Math.abs(((Minion) battle.getSelectedCard()).getYCoordinate() + new Random().nextInt(3))) % 8) + 1;
        }
        toReturn.append("AI decided to insert a card but failed!\n");
    }

    private void moveAI(StringBuilder toReturn, Battle battle) throws ExceptionEndGame {
        int x, x0;
        int y, y0;
        String s;
        Minion selectedMinion = selectCard(toReturn);
        battle.setSelectedCard(battle.getCellFromBoard(selectedMinion.getXCoordinate(), selectedMinion.getYCoordinate()));
        for (int i = 0; i < 5; i++) {
            battle.setSelectedCard(battle.getCellFromBoard(selectedMinion.getXCoordinate(), selectedMinion.getYCoordinate()));
            x0 = ((Minion) battle.getSelectedCard()).getXCoordinate();
            y0 = ((Minion) battle.getSelectedCard()).getYCoordinate();
            y = (Math.abs(((Minion) battle.getSelectedCard()).getYCoordinate() + new Random().nextInt() % 3)) % 8 + 1;
            x = (Math.abs(((Minion) battle.getSelectedCard()).getXCoordinate() + new Random().nextInt() % 3)) % 4 + 1;
            s = battle.movingCard(x, y);
            if (s.contains("moved")) {
                results.add("moved " + Battle.getCurrentBattle().getSelectedCard().getName().concat(" -> ") + x0 + " " + y0 + " " + x + " " + y);
                toReturn.append("AI decided to move ").append(battle.getSelectedCard().getName()).append(" into ").append(x).append(" - ").append(y).append(" and ".concat(s).concat("\n"));
                return;
            }
            selectedMinion = selectCard(toReturn);
        }
        toReturn.append("AI decided to move a card but failed!\n");
    }

    private Minion selectCard(StringBuilder stringBuilder) {
        Random random = new Random();
        int r = Math.abs(random.nextInt() % Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).size());
        while (Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).get(r) == null)
            r = Math.abs(random.nextInt() % Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).size());
        stringBuilder.append("AI select card ").append(Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).get(r).getName()).append("\n");
        return Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).get(r);
    }

    public static ArrayList<String> getResults() {
        return results;
    }
}
