package Data;

import CardCollections.Deck;
import GameGround.Battle;

import java.util.Random;

public class AI extends Player {
    private static Player currentAIPlayer;
    private static Player AIModeKH;
    private static Player AIModeCF;
    private static Player AIModeHF;

    public static void initializeAIStory() {
        AIModeKH = new Player("AI Mode KH", Deck.getDeckKillHero());
        AIModeHF = new Player("AI MODE HF", Deck.getDeckHoldFlag());
        AIModeCF = new Player("AI MODE CF", Deck.getDeckCaptureFlag());
    }

    public AI(String userName, Deck deck) {
        super(userName, deck);
        currentAIPlayer = this;
    }

    public static void setAiPlayer(MODE mode) {
        initializeAIStory();
        switch (mode) {
            case KH:
                currentAIPlayer = AIModeKH;
                return;
            case Hf:
                currentAIPlayer = AIModeHF;
                return;
            case CF:
                currentAIPlayer = AIModeCF;
        }
    }

    public static Player getCurrentAIPlayer() {
        return currentAIPlayer;
    }

    public static void setCurrentAIPlayer(Player currentAIPlayer) {
        AI.currentAIPlayer = currentAIPlayer;
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

    public void action() {
        int counters = new Random().nextInt() % 5 + 1;
        Battle battle = Battle.getCurrentBattle();
        for (int i = 0; i < counters; i++) {
            int r = new Random().nextInt() % 7;
            switch (r) {
                case 0:
                    int randomToChoose = new Random().nextInt() % (currentAIPlayer).getMainDeck().getCards().size();
                    battle.selectCardOrItem(currentAIPlayer.getMainDeck().getCards().get(randomToChoose).getId());
                    break;
                case 1:
                    int x = new Random().nextInt() % 9;
                    int y = new Random().nextInt() % 5;
                    if (battle.getSelectedCard() == null)
                        continue;
                    battle.movingCard(x, y);
                    break;
                case 2:
                    randomToChoose = new Random().nextInt() % (currentAIPlayer).getMainDeck().getCards().size();
                    x = new Random().nextInt() % 9;
                    y = new Random().nextInt() % 5;
                    battle.insertingCardFromHand(currentAIPlayer.getMainDeck().getCards().get(randomToChoose).getName(), x, y);
                    break;
                case 3:
                    randomToChoose = new Random().nextInt() % (battle.theOtherPlayer().getMainDeck().getCards().size());
                    battle.attack(battle.theOtherPlayer().getMainDeck().getCards().get(randomToChoose).getId(), false, null);
                    break;
                case 4:
                    break;
                case 5:
                    x = new Random().nextInt() % 9;
                    y = new Random().nextInt() % 5;
                    battle.useSpecialPower(x, y);
                    break;
                case 6:
                    x = new Random().nextInt() % 9;
                    y = new Random().nextInt() % 5;
                    if (battle.getSelectedItem() != null)
                        battle.getSelectedItem().action(x, y);
                    break;
            }
        }
        battle.endTurn();
    }
}
