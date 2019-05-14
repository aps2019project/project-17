package Data;

import CardCollections.Deck;
import GameGround.Battle;

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
        int counters = new Random().nextInt(5) + 1;
        Battle battle = Battle.getCurrentBattle();
        for (int i = 0; i < counters; i++) {
            int r = new Random().nextInt() % 7;
            switch (r) {
                default:
                    if (Battle.getCurrentBattle() == null)
                        return;
                case 0:
                    int randomToChoose = new Random().nextInt() % (currentAIPlayer).getMainDeck().getCards().size();
                    while (randomToChoose < 0)
                        randomToChoose = new Random().nextInt() % (currentAIPlayer).getMainDeck().getCards().size();
                    battle.selectCardOrItem(currentAIPlayer.getMainDeck().getCards().get(randomToChoose).getId());
                    toReturn.append("AI decided to select a card\n");
                    break;
                case 1:
                    int x = new Random().nextInt() % 5;
                    int y = new Random().nextInt() % 9;
                    if (battle.getSelectedCard() == null)
                        continue;
                    while (x <= 0 || y < 0) {
                        x = new Random().nextInt() % 5;
                        y = new Random().nextInt() % 9;
                    }
                    battle.movingCard(x, y);
                    toReturn.append("AI decided to move a card\n");
                    break;
                case 2:
                    randomToChoose = new Random().nextInt() % (currentAIPlayer).getHand().getCards().size();
                    x = new Random().nextInt() % 5;
                    y = new Random().nextInt() % 9;
                    while (x <= 0 || y <= 0 || randomToChoose < 0) {
                        x = new Random().nextInt() % 5;
                        y = new Random().nextInt() % 9;
                        randomToChoose = new Random().nextInt() % (currentAIPlayer).getHand().getCards().size();
                    }
                    battle.insertingCardFromHand(currentAIPlayer.getHand().getCards().get(randomToChoose).getName(), x, y);
                    toReturn.append("AI decided to insert a card\n");
                    break;
                case 3:
                    randomToChoose = new Random().nextInt() % (battle.theOtherPlayer().getMainDeck().getCards().size());
                    while (randomToChoose < 0)
                        randomToChoose = new Random().nextInt() % (battle.theOtherPlayer().getMainDeck().getCards().size());
                    battle.attack(battle.theOtherPlayer().getMainDeck().getCards().get(randomToChoose).getId(), false, null);
                    toReturn.append("AI decided to attack to a card\n");
                    break;
                case 4:
//                    if (i % 2 == 0){
//                        battle.endingGame();
//                        toReturn.append("AI decided to end the game :)\n");
//                        return;
//                    }
                case 5:
                    x = new Random().nextInt() % 5;
                    y = new Random().nextInt() % 9;
                    while (x <= 0 || y <= 0) {
                        x = new Random().nextInt() % 5;
                        y = new Random().nextInt() % 9;
                    }
                    battle.useSpecialPower(x, y);
                    toReturn.append("AI decided to use special power\n");
                    break;
                case 6:
                    x = new Random().nextInt() % 9;
                    y = new Random().nextInt() % 5;
                    if (battle.getSelectedItem() != null)
                        battle.getSelectedItem().action(x, y);
                    toReturn.append("AI decided to select a card\\item\n");
                    break;
            }
        }
        System.out.println(toReturn);
    }
}
