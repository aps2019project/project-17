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

        if (this.selectedCard == null)
            return "first you have to select a card";
        Minion minion = (Minion) this.selectedCard;

        Cell cellFirst = this.board.getCells()[minion.getXCoordinate() - 1][minion.getYCoordinate() - 1];
        Cell cellDestination = this.board.getCells()[x - 1][y - 1];

        if (cellDestination.getCard() != null || Cell.distance(cellFirst, cellDestination) > minion.getDistanceCanMove())
            return "invalid target";
        if (!minion.isCanMove())
            return "this card cant move";
        cellDestination.setCard(this.selectedCard);
        cellFirst.setCard(null);
        minion.setCanMove(false);
        minion.setCoordinate(x, y);
        if (cellDestination.getItem() != null)
            whoseTurn().addItemToCollectAbleItems(cellDestination.getItem());
        // if cell has buff ?!
        return this.selectedCard.getId() + " moved to " + x + " - " + y;
    }

    @Override
    public String insertingCardFromHand(String cardName, int x, int y) {
        Card card = whoseTurn().getCardFromHand(cardName);
        Cell cell = board.getCells()[x - 1][y - 1];

        if (card == null)
            return "invalid card name";
        if (card instanceof Minion) {
            if (cell == null || cell.getCard() != null)
                return "invalid target ";
            if (whoseTurn().getMana() < ((Minion) card).getManaPoint())
                return "You don′t have enough mana";
            if (!board.isCoordinateAvailable(cell, whoseTurn(), this))
                return "invalid target";
            card.setUserName(whoseTurn().getUserName());
            whoseTurn().lessMana(((Minion) card).getManaPoint());
            cell.setCard(card);
            ((Minion) card).setCoordinate(x, y);
            whoseTurn().removeCardFromHand(card);
            this.selectedCard = card;
            return "card successfully inserted ";
        }
        // spell
        return "card successfully inserted";
    }
}
