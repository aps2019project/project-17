package GameGround;


import Data.Player;
import effects.Card;
import effects.Item;

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
}
