package GameGround;


import CardCollections.Hand;
import Data.GameData;
import Data.Player;
import effects.*;

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
        this.board.getCells()[2][8].setCard(this.playerOne.getCopyMainDeck().getHero());
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

    public String selectCardOrItem(String cardID) {
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
        for (int i = 0; i < whoseTurn().getCollectAbleItems().size(); i++) {
            if (whoseTurn().getCollectAbleItems().get(i).getId().equals(cardID)) {
                this.selectedItem = whoseTurn().getCollectAbleItems().get(i);
                return "item successfully selected";
            }
        }
        return "invalid card\\item id";
    }

    public String movingCard(int x, int y) {
        if (this.selectedCard == null || this.selectedCard instanceof Spell)
            return "first you have to select a card";
        if (x > 9 || y > 5)
            return "invalid target out of range";
        Minion minion = (Minion) this.selectedCard;
        Cell cellFirst = getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate());
        Cell cellDestination = getCellFromBoard(x, y);

        if (cellDestination.getCard() != null || Cell.distance(cellFirst, cellDestination) > minion.getDistanceCanMove())
            return "invalid target";
        if (!minion.CanMove())
            return "this card cant move";
        cellDestination.setCard(minion);
        minion.setCanMove(false);
        cellFirst.setCard(null);
        minion.setCoordinate(x, y);
        if (cellDestination.getItem() != null) {
            whoseTurn().addItemToCollectAbleItems(cellDestination.getItem());
            cellDestination.setItem(null);
        }
        return "ok";
    }

    public String attackCombo(String opponentCardID, String... cardIDs) {
        for (String cardID : cardIDs) {
            Card card = returnCardFromBoard(cardID, whoseTurn());
            attack(opponentCardID, true, (Minion) card);
        }
        Minion opponentCard = (Minion) returnCardFromBoard(opponentCardID, theOtherPlayer());
        Minion firstMinion = (Minion) returnCardFromBoard(cardIDs[0], whoseTurn());
        opponentCard.counterAttack(firstMinion);
        return null;
    }

    public String insertingCardFromHand(String cardName, int x, int y) {
        Card card = whoseTurn().getCardFromHand(cardName);
        Cell cell = getCellFromBoard(x, y);

        if (card == null)
            return "invalid card name";
        if (x > 9 || y > 5)
            return "invalid target";

        if (cell == null || cell.getCard() != null)
            return "invalid target ";

        if (!board.isCoordinateAvailable(cell, whoseTurn(), this))
            return "invalid target";

        if (cell.getItem() != null) {
            whoseTurn().addItemToCollectAbleItems(cell.getItem());
            cell.setItem(null);
        }

        if (card instanceof Minion) {
            if (whoseTurn().getMana() < ((Minion) card).getManaPoint())
                return "You don′t have enough mana";
            this.selectedCard = card;
            if (((Minion) card).getAttackType().equals(AttackType.ON_SPAWN))
                ((Minion) card).useSpecialPower(card);
            card.setUserName(whoseTurn().getUserName());
            whoseTurn().lessMana(((Minion) card).getManaPoint());
            ((Minion) card).setCoordinate(x, y);
            whoseTurn().removeCardFromHand(card);
            cell.setCard(card);
            return "ok";

        } else if (card instanceof Spell) {

            if (whoseTurn().getMana() < ((Spell) card).getManaPoint())
                return "you don't have enough mana";
        }
        return "ok";
    }

    public String showCollectAble() {
        StringBuilder toShow = new StringBuilder();
        ArrayList<Item> toPrint = whoseTurn().getCollectAbleItems();
        if (toPrint.size() == 0) {
            toShow.append("you dont have item right know ");
        } else {
            for (int i = 0; i < toPrint.size(); i++) {
                toShow.append(i).append(": ").append(toPrint.get(i).getName()).append("\n");
            }
        }
        return toShow.toString();
    }

    public Hand showHand() {
        return whoseTurn().getHand();
    }

    public String showInfoOFItem() {
        if (this.selectedCard == null) {
            return "no item selected yet";
        }
        return "name: " + this.selectedItem.getName() + " -> " + this.selectedItem.getDesc();
    }

    public String showNextCard() {
        return whoseTurn().getNextCard().getName() + " " + whoseTurn().getNextCard().getDesc();
    }

    public String showCardInfoFromGraveYard(String cardID) {
        for (int i = 0; i < whoseTurn().getGraveYard().size(); i++) {
            if (whoseTurn().getGraveYard().get(i).getId().equals(cardID)) {
                return whoseTurn().getGraveYard().get(i).getName() + " " + whoseTurn().getGraveYard().get(i).getDesc();
            }
        }
        return "this card doesn't exist in the grave yard";
    }

    public String showCardsOfGraveYard() {
        StringBuilder toPrint = new StringBuilder();
        if (whoseTurn().getGraveYard().size() == 0)
            return "grave card is empty";
        for (int i = 0; i < whoseTurn().getGraveYard().size(); i++) {
            toPrint.append(whoseTurn().getGraveYard().get(i).getName()).append(" ").append(whoseTurn().getGraveYard().get(i).getDesc()).append("\n");
        }
        return toPrint.toString();
    }

    public String attack(String opponentCardId, boolean isComboAttack, Minion comboAttacker) {
        Minion attacker;
        if (isComboAttack)
            attacker = comboAttacker;
        else
            attacker = (Minion) selectedCard;
        if (attacker == null)
            return "you have to select card at first";

        Minion minion = (Minion) returnCardFromBoard(opponentCardId, theOtherPlayer());
        if (minion == null)
            return "invalid card id";

        if (!attacker.getCanAttack())
            return "this card with " + attacker.getId() + " can't attack";

        Cell cellDestination = this.board.getCells()[minion.getXCoordinate() - 1][minion.getYCoordinate() - 1];
        Cell cellFirst = this.board.getCells()[(attacker).getXCoordinate() - 1][attacker.getYCoordinate() - 1];
        if (Cell.distance(cellDestination, cellFirst) > (attacker).getAttackRange())
            return "opponent minion is unavailable for attack";
        attacker.attack(minion);
        minion.counterAttack(attacker);
        return attacker.getName() + " attacked to " + minion.getName();
    }

    public String useSpecialPower(int x, int y) {
        Cell cell = this.board.getCells()[x - 1][y - 1];
        whoseTurn().getMainDeck().getHero().useSpecialPower(cell);
        return null;
    }

    Card returnCardFromBoard(String id, Player player) {
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                if (this.board.getCells()[i][j].getCard().getId().equals(id)) {
                    if (cardIsMine(this.board.getCells()[i][j].getCard(), player))
                        return this.board.getCells()[i][j].getCard();
                }
            }
        }
        return null;
    }

    public ArrayList<Minion> minionsArroundCell(int x, int y) {
        ArrayList<Minion> minions = new ArrayList<>();
        for (int i = x - 2; i <= x; i++) {
            for (int j = y - 2; j <= y; j++) {
                if (i == x - 1 && j == y - 1)
                    continue;
                Cell cell = getCellFromBoard(i + 1, j + 1);
                if (cell.getCard() != null && cell.getCard() instanceof Minion)
                    minions.add((Minion) cell.getCard());
            }
        }
        return minions;
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

    public boolean cardIsMine(Card card, Player player) {
        return card.getUserName().equals(player.getUserName());
    }

    public void endTurn() {
        this.turn++;
        if (this.turn >= 15) {
            this.playerOne.addMana(-this.playerOne.getMana() + 9);
            this.playerTwo.addMana(-this.playerTwo.getMana() + 9);
        } else {
            if (this.turn % 2 == 0)
                this.playerTwo.addMana(this.playerTwo.getPreviousMana() + 1);
            else
                this.playerOne.addMana(this.playerOne.getPreviousMana() + 1);

            this.playerOne.setPreviousMana(this.playerOne.getMana());
            this.playerTwo.setPreviousMana(this.playerTwo.getMana());
        }
        // minions reset
        // effect time in buff
        // time of holding flag
        //
        // this part depend on Game Mode
    }

    public static Battle getCurrentBattle() {
        return currentBattle;
    }

    public Cell getCellFromBoard(int x, int y) {
        return this.board.getCells()[x - 1][y - 1];
    }

    public ArrayList<Minion> getAllMinion() {
        ArrayList<Minion> allMinion = new ArrayList<>();
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                Cell cell = this.board.getCells()[i][j];
                if (cell != null)
                    allMinion.add((Minion) cell.getCard());
            }
        }
        return allMinion;
    }

    public ArrayList<Minion> returnMinionsInColumn(int x, int y) {
        ArrayList<Minion> toReturn = new ArrayList<>();
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                if (i == x - 1 && j != y - 1 && getCellFromBoard(i + 1, j + 1).getCard() != null)
                    toReturn.add((Minion) getCellFromBoard(i, j).getCard());
            }
        }
        return toReturn;
    }

    public ArrayList<Minion> returnMinionsWhichDistance(int x, int y) {
        ArrayList<Minion> toReturn = new ArrayList<>();
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                if (Cell.distance(getCellFromBoard(x, y), getCellFromBoard(i + 1, j + 1)) <= 2 && getCellFromBoard(i + 1, j + 1).getCard() != null)
                    toReturn.add((Minion) getCellFromBoard(i + 1, j + 1).getCard());
            }
        }
        return toReturn;
    }

    public Minion returnRandomMinion(int x, int y) {
        int xR = new Random().nextInt() % 10;
        int yR = new Random().nextInt() % 6;

        while (xR == x && yR == y || getCellFromBoard(xR, yR).getCard() == null || getCellFromBoard(xR, yR).getCard() instanceof Spell) {
            xR = new Random().nextInt() % 10;
            yR = new Random().nextInt() % 6;
        }
        return (Minion) getCellFromBoard(xR, yR).getCard();
    }

    public Minion closestRandomMinion(int x, int y) {
        ArrayList<Minion> nearestMinions = new ArrayList<>();
        int distance = Integer.MAX_VALUE;
        Cell cellFirst = getCellFromBoard(x, y);
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                Cell cell = getCellFromBoard(i + 1, j + 1);
                if (cell.getCard() == null || (x == i + 1 && y == j + 1))
                    continue;
                if (Cell.distance(cell, cellFirst) < distance)
                    distance = Cell.distance(cell, cellFirst);
            }
        }
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                Cell cell = getCellFromBoard(i + 1, j + 1);
                if (Cell.distance(cell, cellFirst) == distance && cell.getCard() != null)
                    nearestMinions.add((Minion) cell.getCard());
            }
        }
        int r = new Random().nextInt() % nearestMinions.size();
        return nearestMinions.get(r);
    }
}
