package Data;

import CardCollections.Deck;

public class AI extends Player {
    private static Player currentAIPlayer;
    private static Player AIModeKH;
    private static Player AIModeCF;
    private static Player AIModeHF;

    public AI(String userName, Deck deck) {
        super(userName, deck);
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

    public void action(){
        
    }
}
