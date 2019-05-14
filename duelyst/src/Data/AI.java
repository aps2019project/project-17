package Data;

import CardCollections.Deck;
import GameGround.Battle;
import effects.Minion;

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
        int counters = Math.abs(new Random().nextInt(1) + 1);
        Battle battle = Battle.getCurrentBattle();
        for (int i = 0; i < counters; i++) {
            int r = Math.abs(new Random().nextInt() % 5);
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
                case 4:
                    useItemAI(toReturn, battle);
                    break;
            }
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
        battle.useItem(x, y);
        toReturn.append("item decided to use ").append(battle.getSelectedItem().getName()).append(" in ").append(x).append(" - ").append(y).append("\n");
    }

    private void specialPowerAI(StringBuilder toReturn, Battle battle) {
        int x;
        int y;
        x = Math.abs(new Random().nextInt() % 5) + 1;
        y = Math.abs(new Random().nextInt() % 9) + 1;
        battle.useSpecialPower(x, y);
        toReturn.append("AI decided to use special power\n");
    }

    private void attackAI(StringBuilder toReturn, Battle battle) {
        if (battle.getSelectedCard() == null) {
            battle.selectCardOrItem(selectCard(toReturn).getId());
        }
        int randomToChoose = Math.abs(new Random().nextInt() % battle.minionsOfCurrentPlayer(battle.getPlayerOne()).size());
        String s;
        for (int i = 0; i < 2; i++) {
            s = battle.attack(battle.minionsOfCurrentPlayer(battle.getPlayerOne()).get(randomToChoose).getId(), false, null);
            if (s.contains("attacked")) {
                toReturn.append("AI decided to attack").append(battle.minionsOfCurrentPlayer(battle.theOtherPlayer()).get(randomToChoose).getName()).append("and ".concat(s).concat("\n"));
                return;
            }
            randomToChoose = Math.abs(new Random().nextInt() % battle.minionsOfCurrentPlayer(battle.theOtherPlayer()).size());
        }
        int x0 = ((Minion) battle.getSelectedCard()).getXCoordinate();
        int y0 = ((Minion) battle.getSelectedCard()).getYCoordinate();
        for (int i = 0; i < 2; i++) {
            int x = x0 + new Random().nextInt() % 2;
            int y = y0 + new Random().nextInt() % 2;
            if (battle.getCellFromBoard(x, y).getCard() == null || battle.getCellFromBoard(x, y).getCard().getUserName().equals(currentAIPlayer.getUserName()))
                continue;
            Minion minion = (Minion) battle.getCellFromBoard(x, y).getCard();
            s = battle.attack(minion.getId(), false, null);
            if (s.contains("attacked")) {
                toReturn.append("AI decided to attack").append(battle.minionsOfCurrentPlayer(battle.theOtherPlayer()).get(randomToChoose).getName()).append("and ".concat(s).concat("\n"));
                return;
            }
        }
        toReturn.append("AI decided to attack but failed!\n");
    }

    private void insertAI(StringBuilder toReturn, Battle battle) {
        int randomToChoose = 1;
        int x = 1;
        int y = 1;
        String s;
        if (battle.getSelectedCard() == null)
            battle.selectCardOrItem(selectCard(toReturn).getId());
        for (int i = 0; i < 2; i++) {
            s = battle.insertingCardFromHand(currentAIPlayer.getHand().getCards().get(randomToChoose).getName(), x, y);
            if (s.contains("successfully")) {
                toReturn.append("AI decided to insert ").append(currentAIPlayer.getHand().getCards().get(randomToChoose).getName()).append(" in ").append(x).append(" - ").append(y).append(" and then ".concat(s).concat("\n"));
                return;
            }
            randomToChoose = Math.abs(new Random().nextInt() % (currentAIPlayer).getHand().getCards().size());
            x = ((Minion) battle.getSelectedCard()).getXCoordinate() + new Random().nextInt() % 2;
            y = ((Minion) battle.getSelectedCard()).getYCoordinate() + new Random().nextInt() % 2;
        }
        toReturn.append("AI decided to insert a card but failed!\n");
    }

    private void moveAI(StringBuilder toReturn, Battle battle) {
        int x;
        int y;
        String s;
        if (battle.getSelectedCard() == null) {
            battle.selectCardOrItem(selectCard(toReturn).getId());
        }
        for (int i = 0; i < 2; i++) {
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

    private Minion selectCard(StringBuilder stringBuilder) {
        Random random = new Random();
        int r = Math.abs(random.nextInt() % Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).size());
        while (Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).get(r) == null)
            r = Math.abs(random.nextInt() % Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).size());
        stringBuilder.append("AI select card ").append(Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).get(r).getName()).append("\n");
        return Battle.getCurrentBattle().minionsOfCurrentPlayer(currentAIPlayer).get(r);
    }
}
