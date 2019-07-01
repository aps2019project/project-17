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
        results = new ArrayList<>();
        if (Battle.getCurrentBattle() == null)
            return;
        StringBuilder toReturn = new StringBuilder();
        Battle battle = Battle.getCurrentBattle();
        int randomToChoiceWay = new Random().nextInt(12);
        for (int i = 0; i < 5; i++)
            insertAI(toReturn, battle);

        switch (randomToChoiceWay) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                // in this case we want to attack + insert !
                for (int i = 0; i < 5; i++)
                    attackAI(toReturn, battle);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                // in this case we want to move + insert !
                for (int i = 0; i < 5; i++)
                    moveAI(toReturn, battle);
                break;
        }
        System.out.println(toReturn);
    }

    private void useItemAI(StringBuilder toReturn, Battle battle) throws ExceptionEndGame {
        if (battle.getSelectedItem() == null) {
            if (currentAIPlayer.getCollectAbleItems().size() == 0)
                return;
            int r1 = Math.abs(new Random().nextInt() % currentAIPlayer.getCollectAbleItems().size());
            battle.selectCardOrItem(currentAIPlayer.getCollectAbleItems().get(r1).getId());
            toReturn.append("AI selected item").append(currentAIPlayer.getCollectAbleItems().get(r1).getName()).append("\n");
        }
        int x = Math.abs(new Random().nextInt() % 5) + 1;
        int y = Math.abs(new Random().nextInt() % 9) + 1;
        battle.useItem(x, y);
        toReturn.append("item decided to use ").append(battle.getSelectedItem().getName()).append(" in ").append(x).append(" - ").append(y).append("\n");
    }

    private void specialPowerAI(StringBuilder toReturn, Battle battle) throws ExceptionEndGame {
        int x;
        int y;
        x = Math.abs(new Random().nextInt() % 5) + 1;
        y = Math.abs(new Random().nextInt() % 9) + 1;
        battle.useSpecialPower(x, y);
        toReturn.append("AI decided to use special power\n");
    }

    private void attackAI(StringBuilder toReturn, Battle battle) throws ExceptionEndGame {
        Minion minion = selectCard();
        if (minion == null)
            return;
        battle.setSelectedCard(battle.getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate()));
        String s;
        for (int i = 0; i < 5; i++) {
            Minion minionTarget = battle.targetForAttackAI();
            if (minionTarget == null) {
                minion = selectCard();
                if (minion == null)
                    return;
                battle.setSelectedCard(battle.getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate()));
                continue;
            }
            s = battle.attack(minionTarget.getId(), false, null);
            if (s.contains("attacked")) {
                results.add("attack " + minion.getName() + " - " + minionTarget.getName() + " - " + minionTarget.getXCoordinate() + " " + minionTarget.getYCoordinate());
                toReturn.append("AI decided to attack ").append(minionTarget.getName()).append(" and ".concat(s).concat("\n"));
                return;
            }
            minion = selectCard();
            if (minion == null)
                return;
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
            Minion selectedMinion = selectCard();
            if (selectedMinion == null)
                return;
            battle.setSelectedCard(Battle.getCurrentBattle().getCellFromBoard(selectedMinion.getXCoordinate(), selectedMinion.getYCoordinate()));
        }
        for (int i = 0; i < 5; i++) {
            if (currentAIPlayer.getHand().getCards().size() <= 0)
                break;
            Minion selectedMinion = selectCard();
            if (selectedMinion == null)
                return;
            battle.setSelectedCard(Battle.getCurrentBattle().getCellFromBoard(selectedMinion.getXCoordinate(), selectedMinion.getYCoordinate()));
            String name = currentAIPlayer.getHand().getCards().get(randomToChoose).getName();
            s = battle.insertingCardFromHand(currentAIPlayer.getHand().getCards().get(randomToChoose).getName(), x, y);
            if (s.contains("successfully")) {
                results.add("insert " + name.trim().toLowerCase());
                toReturn.append("AI decided to insert ").append(name).append(" in ").append(x).append(" - ").append(y).append(" and then ".concat(s).concat("\n"));
                if (currentAIPlayer.getHand().getCards().size() == 0)
                    return;
                while (randomToChoose >= currentAIPlayer.getHand().getCards().size())
                    randomToChoose = Math.abs(new Random().nextInt((currentAIPlayer).getHand().getCards().size()));
                continue;
            }
            if (currentAIPlayer.getHand().getCards().size() <= 0)
                return;
            randomToChoose = Math.abs(new Random().nextInt((currentAIPlayer).getHand().getCards().size()));
            while (randomToChoose >= currentAIPlayer.getHand().getCards().size())
                randomToChoose = Math.abs(new Random().nextInt((currentAIPlayer).getHand().getCards().size()));
            selectedMinion = selectCard();
            if (selectedMinion == null)
                return;
            battle.setSelectedCard(Battle.getCurrentBattle().getCellFromBoard(selectedMinion.getXCoordinate(), selectedMinion.getYCoordinate()));
            x = Math.abs((Math.abs(((Minion) battle.getSelectedCard()).getXCoordinate() + new Random().nextInt(2))) % 4);
            y = Math.abs((Math.abs(((Minion) battle.getSelectedCard()).getYCoordinate() + new Random().nextInt(2))) % 8);
            if (x == 0)
                x++;
            if (y == 0)
                y++;
        }
        toReturn.append("AI decided to insert a card but failed!\n");
    }

    private void moveAI(StringBuilder toReturn, Battle battle) throws ExceptionEndGame {
        int x, x0;
        int y, y0;
        String s;
        Minion selectedMinion = selectCard();
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
            selectedMinion = selectCard();
        }
        toReturn.append("AI decided to move a card but failed!\n");
    }

    private Minion selectCard() {
        Random random = new Random();
        if (Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).size() == 0)
            return null;
        int r = Math.abs(random.nextInt() % Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).size());
        while (Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).get(r) == null)
            r = Math.abs(random.nextInt() % Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).size());
        return Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).get(r);
    }

    public static ArrayList<String> getResults() {
        return results;
    }
}
