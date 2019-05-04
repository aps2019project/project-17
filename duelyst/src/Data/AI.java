package Data;

import CardCollections.Deck;

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
        initializeAIStory();
        currentAIPlayer = this;
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

    }
}
