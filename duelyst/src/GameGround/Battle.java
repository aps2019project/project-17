package GameGround;


import CardCollections.Hand;
import Data.GameData;
import Data.Player;
import effects.Card;
import effects.Item;
import effects.Minion;

import java.util.ArrayList;
import java.util.Random;

public class Battle {
    protected Player playerOne;
    protected Player playerTwo;
    protected Board board;
    protected int turn;
    protected GameMode gameMode;
    protected Card selectedCard;
    protected Item selectedItem;
    protected BattleType battleType;
    protected GameData gameDataPlayerOne;
    protected GameData gameDataPlayerTwo;
    protected static Battle currentBattle;

    public Battle(Player playerOne, Player playerTwo, GameMode gameMode, BattleType battleType) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = new Board();
        this.turn = 1;
        this.gameMode = gameMode;
        this.selectedCard = null;
        this.selectedItem = null;
        this.battleType = battleType;
        setHeroesInTheirPosition();
    }

    private void setHeroesInTheirPosition() {
        int random = new Random().nextInt();
        if (random % 2 == 0) {
            this.board.getCells()[2][0].setCard(this.playerOne.getCopyMainDeck().getHero());
            this.board.getCells()[2][8].setCard(this.playerTwo.getCopyMainDeck().getHero());
            return;
        }
        this.board.getCells()[2][0].setCard(this.playerTwo.getCopyMainDeck().getHero());
        this.board.getCells()[2][9].setCard(this.playerOne.getCopyMainDeck().getHero());
    }

    public StringBuilder showGameInfo() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("Player One mana: ").append(playerOne.getMana()).append(" player two mana: ").append(playerTwo.getMana()).append("\n");
        return toReturn;
    }

    public ArrayList<Minion> showMyMinions() {
        return showMinions(whoseTurn());
    }

    public ArrayList<Minion> showOpponentMinion() {
        return showMinions(theOtherPlayer());
    }

    private ArrayList<Minion> showMinions(Player player) {
        ArrayList<Minion> toReturn = new ArrayList<>();

        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                if (this.board.getCells()[i][j].getCard() == null)
                    continue;
                Minion minion = (Minion) this.board.getCells()[i][j].getCard();
                if (cardIsMine(minion, player))
                    toReturn.add(minion);
            }
        }
        return toReturn;
    }

    public Card returnCard(String cardID) {
        if (whoseTurn().getMainDeck().getHero().getId().equals(cardID))
            return whoseTurn().getMainDeck().getHero();

        for (int i = 0; i < whoseTurn().getMainDeck().getCards().size(); i++) {
            Card card = whoseTurn().getMainDeck().getCards().get(i);
            if (card.getId().equals(cardID))
                return card;
        }
        return null;
    }

    public String selectCard(String cardID) {
        for (int i = 0; i < board.getCells().length; i++) {
            for (int j = 0; j < board.getCells()[i].length; j++) {
                Card card = board.getCells()[i][j].getCard();
                if (card == null)
                    continue;
                if (card.getId().equals(cardID)) {
                    if (!cardIsMine(card, whoseTurn()))
                        return "this card doesnt belong to you";
                    this.selectedCard = card;
                    return "card successfully selected";
                }
            }
        }
        return "invalid card id";
    }

    public String movingCard(int x, int y) {
        Minion minion = (Minion) this.selectedCard;

        int x0 = minion.getXCoordinate();
        int y0 = minion.getYCoordinate();

        Cell cell = board.getCells()[x - 1][y - 1];
        Cell correctCell = board.getCells()[x0 - 1][y0 - 1];

        int distance = Cell.distance(cell, correctCell);

        if (distance > minion.getDistanceCanMove())
            return "invalid target";
        if (cell.getCard() != null)
            return "invalid target";

        if (!minion.CanMove())
            return "minion can't move yet";

        cell.setCard(this.selectedCard);
        correctCell.setCard(null);
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
        return "";
    }

    public Hand showHand() {
        return whoseTurn().getHand();
    }

    public String attack(String opponentCardId) {

        return "attack successfully done";
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

    public Player whoseTurn() {
        if (this.turn % 2 == 1)
            return this.playerOne;
        return this.playerTwo;
    }

    public Player theOtherPlayer() {
        Player player = playerOne;

        if (whoseTurn().equals(playerOne))
            player = playerTwo;
        return player;
    }

    boolean cardIsMine(Card card, Player player) {
        return card.getUserName().equals(player.getUserName());
    }

    public static Battle getCurrentBattle() {
        return currentBattle;
    }

    public void endTurn() {
    }
}
