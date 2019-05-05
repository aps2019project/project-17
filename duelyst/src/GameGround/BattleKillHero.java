package GameGround;

import Data.AI;
import Data.GameData;
import Data.Player;
import effects.Card;
import effects.Minion;
import effects.Spell;

public class BattleKillHero extends Battle {

    private SinglePlayerModes singlePlayerModes;

    public BattleKillHero(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo, GameMode.MULTI_PLAYER, BattleType.KILL_HERO);
        currentBattle = this;
        setGameData();
        //MULTI PLAYER
    }

    public BattleKillHero(Player playerOne, SinglePlayerModes singlePlayerModes) {
        super(playerOne, AI.getCurrentAIPlayer(), GameMode.SINGLE_PLAYER, BattleType.KILL_HERO);
        this.singlePlayerModes = singlePlayerModes;
        currentBattle = this;
        setGameData();
        // single Player -> in this case there is no different between custom and story because both of them pass a deck name! the currentAIPlayer should set in Controller!
    }

    private void setGameData() {
        switch (this.gameMode) {
            case SINGLE_PLAYER:
                gameDataPlayerTwo = null;
                switch (singlePlayerModes) {
                    case STORY:
                        gameDataPlayerOne = new GameData("mode one");
                        break;
                    case CUSTOM:
                        gameDataPlayerOne = new GameData("custom game ->  mode : kill hero");
                        break;
                }
                break;
            case MULTI_PLAYER:
                gameDataPlayerOne = new GameData(playerTwo.getUserName());
                gameDataPlayerTwo = new GameData(playerOne.getUserName());
        }
    }


    @Override
    public StringBuilder showGameInfo() {
        StringBuilder toPrint = super.showGameInfo();
        toPrint.append("hero of first player : ").append(playerOne.getMainDeck().getHero().getHealthPoint()).append("\n");
        toPrint.append("hero of second player : ").append(playerTwo.getMainDeck().getHero().getHealthPoint()).append("\n");
        return toPrint;
    }

    @Override
    public String movingCard(int x, int y) {

        String toReturnFormSuper = super.movingCard(x, y);
        if (!toReturnFormSuper.equals("ok"))
            return toReturnFormSuper;

        // if cell has buff ?!
        return this.selectedCard.getId() + " moved to " + x + " - " + y;
    }

    @Override
    public String insertingCardFromHand(String cardName, int x, int y) {
        String toReturnFromSuper = super.insertingCardFromHand(cardName, x, y);
        if (!toReturnFromSuper.equals("ok"))
            return toReturnFromSuper;
        Card card = whoseTurn().getCardFromHand(cardName);
        Cell cell = getCellFromBoard(x, y);
        // spell
        return "card successfully inserted";
    }

    @Override
    public String attack(String opponentCardId) {
        Card card = returnCardFromBoard(opponentCardId, theOtherPlayer());
        if (card == null)
            return "invalid card ID";
        Cell cellFirst = this.board.getCells()[((Minion) this.selectedCard).getXCoordinate() - 1][((Minion) this.selectedCard).getYCoordinate() - 1];
        Cell cellTarget = this.board.getCells()[((Minion) card).getXCoordinate() - 1][((Minion) card).getYCoordinate() - 1];

        if (Cell.distance(cellFirst, cellTarget) > ((Minion) this.selectedCard).getAttackRange())
            return "opponent minion in unavailable";

        if (!((Minion) selectedCard).getCanAttack())
            return "this minion with " + this.selectedCard.getId() + " can't attack";

        // if minion attack ! count it !
        return "attack from " + this.selectedCard.getName() + " to " + card.getName() + "successfully done";
    }
}
