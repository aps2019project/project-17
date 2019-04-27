package GameGround;


import CardCollections.Hand;
import Data.Player;
import effects.Card;
import effects.Item;
import effects.Minion;

import java.util.ArrayList;

public class Battle {
    private Player playerOne;
    private Player playerTwo;
    private Board board;
    private Enum TurnEnum;
    private int turn;
    private Card selectedCard;
    private Item selectedItem;

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Board getBoard() {
        return board;
    }

    public Enum getTurnEnum() {
        return TurnEnum;
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
        if (this.turn % 2 == 0)
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
        }
        for (int i = 0; i < theOtherPlayer().getMainDeck().getCards().size(); i++) {
            Card card = theOtherPlayer().getMainDeck().getCards().get(i);
            if (card.getId().equals(cardID))
                return card;
        }
        return null;
    }

    private boolean cardIsMine(Card card, Player player) {
        for (int i = 0; i < player.getMainDeck().getCards().size(); i++) {
            Card card1 = player.getMainDeck().getCards().get(i);
            if (card.getId().equals(card1.getId()))
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

    public void nextTurn() {
        this.turn++;
        this.selectedCard = null;
    }

    public String movingCard(int x, int y) {
        Minion minion = (Minion) this.selectedCard;

        int x0 = minion.getXCoordinate();
        int y0 = minion.getYCoordinate();

        int distance = Cell.distance(x, y, x0, y0);
        Cell cell = board.getCells()[x - 1][y - 1];
        Cell cell1 = board.getCells()[x0 - 1][y0 - 1];

        if (distance > minion.getDistanceCanMove())
            return "invalid target";
        if (cell.getCard() != null)
            return "invalid target";

        cell.setCard(this.selectedCard);
        cell1.setCard(null);
        ((Minion) this.selectedCard).setCoordinate(x, y);
        return this.selectedCard.getId() + " moved to " + x + " " + y;
    }

    public String insertingCardFromHand(String cardName, int x, int y) {

        Player player = whoseTurn();
        Card card = whoseTurn().getCardFromHand(cardName);
        Cell cell = board.getCells()[x - 1][y - 1];

        if (card == null)
            return "invalid card name";
        if (cell.getCard() != null)
            return "invalid target";

        if (player.getMana() < ((Minion) card).getManaPoint())
            return "no enough mana";

        // TODO: max distance = 1 !

        player.lessMana(((Minion) card).getManaPoint());
        cell.setCard(card);
        ((Minion) card).setCoordinate(x, y);
        whoseTurn().removeCardFromHand(card);
        whoseTurn().addCardToHand();
        return "card successfully inserted";
    }

}
