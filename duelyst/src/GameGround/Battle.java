package GameGround;


import CardCollections.Hand;
import Data.GameData;
import Data.Player;
import effects.Card;
import effects.Hero;
import effects.Item;
import effects.Minion;

import java.util.ArrayList;

public class Battle {
    private Player playerOne;
    private Player playerTwo;
    private Board board;
    private int turn;
    private GameMode gameMode;
    private Card selectedCard;
    private BattleType battleType;
    private Item selectedItem;
    private GameData gameData;

    public Battle(Player playerOne, Player playerTwo, Board board, GameMode gameMode, BattleType battleType) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = board;
        this.turn = 1;
        this.gameMode = gameMode;
        this.selectedCard = null;
        this.selectedItem = null;
        this.battleType = battleType;
    }

    public String showGameInfo() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("Player One mana: ").append(playerOne.getMana()).append(" player two mana: ").append(playerTwo.getMana()).append(" ");
        switch (battleType) {
            case KILL_HERO:
                toReturn.append("player One Hero health: ").append(playerOne.getMainDeck().getHero()).append(" ");
                toReturn.append("player Two Hero health: ").append(playerTwo.getMainDeck().getHero());
                break;
            case HOLDING_FLAG:
                if (whoHasFlag() == null)
                    toReturn.append("no one has flag yet");
                else
                    toReturn.append(whoHasFlag().getUserName()).append(" has flag");
                break;
            case CAPTURE_FLAG:

                for (int i = 0; i < this.board.getCells().length; i++) {
                    for (int j = 0; j < this.board.getCells()[i].length; j++) {
                        Card card = this.board.getCells()[i][j].getCard();
                        if (card == null)
                            continue;
                        if (((Minion) card).isHasFlag()) {
                            if (cardIsMine(card, playerOne)) {
                                toReturn.append(card.getName()).append(" from ").append(playerOne.getUserName()).append(" has flag");
                                continue;
                            }
                            toReturn.append(card.getName()).append(" from ").append(playerTwo.getUserName()).append(" has flag");
                        }
                    }
                }

                break;
        }
        return toReturn.toString();
    }

    private Player whoHasFlag() {
        if (playerOne.isPlayerHasFlag())
            return playerOne;
        if (playerTwo.isPlayerHasFlag())
            return playerTwo;
        return null;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Board getBoard() {
        return board;
    }

    public int getTurn() {
        return turn;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    private Player whoseTurn() {
        if (this.turn % 2 == 1)
            return this.playerOne;
        return this.playerTwo;
    }

    private Player theOtherPlayer() {
        Player player = playerOne;

        if (whoseTurn().equals(playerOne))
            player = playerTwo;
        return player;
    }

    private ArrayList<Minion> showMinions(Player player) {
        ArrayList<Minion> toReturn = new ArrayList<>();

        for (int i = 0; i < player.getMainDeck().getCards().size(); i++) {
            Card card = player.getMainDeck().getCards().get(i);
            if (((Minion) card).getXCoordinate() != 0 && ((Minion) card).getYCoordinate() != 0)
                toReturn.add((Minion) card);
        }

        toReturn.add(player.getMainDeck().getHero());
        return toReturn;
    }

    public ArrayList<Minion> showMyMinions() {
        return showMinions(whoseTurn());
    }

    public ArrayList<Minion> showOpponentMinion() {
        return showMinions(theOtherPlayer());
    }

    public Card returnCard(String cardID) {
        for (int i = 0; i < whoseTurn().getMainDeck().getCards().size(); i++) {
            Card card = whoseTurn().getMainDeck().getCards().get(i);
            if (card.getId().equals(cardID))
                return card;
            if (whoseTurn().getMainDeck().getHero().getId().equals(cardID))
                return whoseTurn().getMainDeck().getHero();
        }
        for (int i = 0; i < theOtherPlayer().getMainDeck().getCards().size(); i++) {
            Card card = theOtherPlayer().getMainDeck().getCards().get(i);
            if (card.getId().equals(cardID))
                return card;
        }
        return null;
    }

    public boolean cardIsMine(Card card, Player player) {
        if (card instanceof Hero) {
            return player.getMainDeck().getHero().getName().equals(card.getName());
        }
        for (int i = 0; i < player.getMainDeck().getCards().size(); i++) {
            Card card1 = player.getMainDeck().getCards().get(i);
            if (Card.equals(card, card1))
                return true;
        }
        return false;
    }

    public String selectCard(String cardID) {
        for (int i = 0; i < board.getCells().length; i++) {
            for (int j = 0; j < board.getCells()[i].length; j++) {
                Card card = board.getCells()[i][j].getCard();
                if (card == null || !card.getId().equals(cardID))
                    continue;
                if (cardIsMine(card, whoseTurn())) {
                    this.selectedCard = card;
                    return "card successfully added";
                }
            }
        }
        return "invalid card id";
    }

    public void endTurn() {
        this.selectedCard = null;
        playerOne.allMinionsReset();
        playerTwo.allMinionsReset();
        this.turn++;
        if (turn % 2 == 0 && turn <= 14)
            playerTwo.addMana(1);
        else if (turn % 2 == 1 && turn <= 14)
            playerOne.addMana(1);
        else {
            playerOne.lessMana(playerOne.getMana());
            playerOne.addMana(9);
            playerTwo.lessMana(playerTwo.getMana());
            playerTwo.addMana(9);

        }
    }

    public String movingCard(int x, int y) {
        Minion minion = (Minion) this.selectedCard;

        int x0 = minion.getXCoordinate();
        int y0 = minion.getYCoordinate();

        Cell cell = board.getCells()[x - 1][y - 1];
        Cell cell1 = board.getCells()[x0 - 1][y0 - 1];

        int distance = Cell.distance(cell, cell1);

        if (distance > minion.getDistanceCanMove())
            return "invalid target";
        if (cell.getCard() != null)
            return "invalid target";

        if (!minion.CanMove())
            return "minion can't move yet";

        cell.setCard(this.selectedCard);
        cell1.setCard(null);
        ((Minion) this.selectedCard).setCoordinate(x, y);
        if (cell.hasFlag()) {
            whoseTurn().changeNumberOfHoldingFlags(1);
            whoseTurn().setPlayerHasFlag(true);
            cell.setFlag(false);
        }
        if (cell.getItem() != null) {
            whoseTurn().addItemToCollectAbleItems(cell.getItem());
            cell.setItem(null);
        }
        minion.setCanMove(false);
        return this.selectedCard.getId() + " moved to " + x + " - " + y;
    }

    public String insertingCardFromHand(String cardName, int x, int y) {

        Card card = whoseTurn().getCardFromHand(cardName);
        Cell cell = board.getCells()[x - 1][y - 1];

        if (card == null)
            return "invalid card name";
        if (cell.getCard() != null)
            return "invalid target";

        if (whoseTurn().getMana() < ((Minion) card).getManaPoint())
            return "no enough mana";

        if (!board.isCoordinateAvailable((Minion) card, cell, whoseTurn(), this))
            return "invalid target";

        whoseTurn().lessMana(((Minion) card).getManaPoint());
        cell.setCard(card);
        ((Minion) card).setCoordinate(x, y);
        whoseTurn().removeCardFromHand(card);
        whoseTurn().addCardToHand();
        return "card successfully inserted";
    }

    public Hand showHand() {
        return whoseTurn().getHand();
    }

    public String attack() {

        return "attack successfully done";
    }
}
