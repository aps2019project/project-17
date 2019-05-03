package Data;

import CardCollections.Deck;

public class AI extends Player {
    private static Player currentAIPlayer;

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
}
