package GameGround;


import CardCollections.Hand;
import Data.Account;
import Data.GameData;
import Data.MatchState;
import Data.Player;
import controller.GameController;
import controller.InstanceBuilder;
import effects.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    private SinglePlayerModes singlePlayerModes;
    protected int price;
    protected static String situationOfGame;

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
        setItems();
        situationOfGame = "";
    }

    private void setItems(){
        ArrayList<Item> items = InstanceBuilder.getCollectAbleItems();
        int n = new Random().nextInt() % items.size() / 3 ;
        while (n < 0)
            n = new Random().nextInt() % items.size() / 3;
        if (n == 0)
            return;
        for (int i = 0; i < n; i++) {
            int select = new Random().nextInt() % items.size();
            while (select < 0)
                select = new Random().nextInt() % items.size();
            int x = new Random().nextInt() % 9 + 1;
            int y = new Random().nextInt() % 5 + 1;
            while (x < 0 || y < 0){
                x = new Random().nextInt() % 9 + 1;
                y = new Random().nextInt() % 5 + 1;
            }
            Cell cell = getCellFromBoard(x, y);
            cell.setItem(items.get(select));
        }
    }

    private void setHeroesInTheirPosition() {
        int random = new Random().nextInt();
        if (random % 2 == 0) {
            playerOne.getMainDeck().getHero().setCoordinate(3, 1);
            playerTwo.getMainDeck().getHero().setCoordinate(3, 9);
            this.board.getCells()[2][0].setCard(this.playerOne.getCopyMainDeck().getHero());
            this.board.getCells()[2][8].setCard(this.playerTwo.getCopyMainDeck().getHero());
            return;
        }
        playerOne.getMainDeck().getHero().setCoordinate(3, 9);
        playerTwo.getMainDeck().getHero().setCoordinate(3, 1);
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

    public String selectCardOrItem(String cardItemID) {
        for (int i = 0; i < board.getCells().length; i++) {
            for (int j = 0; j < board.getCells()[i].length; j++) {
                Card card = board.getCells()[i][j].getCard();
                if (card == null)
                    continue;
                if (card.getId().equals(cardItemID)) {
                    if (!cardIsMine(card, whoseTurn()))
                        return "this card doesnt belong to you";
                    this.selectedCard = card;
                    return "card successfully selected";
                }
            }
        }
        if (whoseTurn().getCollectAbleItems().size() != 1) {
            for (int i = 0; i < whoseTurn().getCollectAbleItems().size(); i++) {
                if (whoseTurn().getCollectAbleItems().get(i).getId().equals(cardItemID)) {
                    this.selectedItem = whoseTurn().getCollectAbleItems().get(i);
                    return "item successfully selected";
                }
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
        cellFirst.exitCell();
        cellFirst.setCard(null);
        minion.setCoordinate(x, y);
        if (cellDestination.getItem() != null) {
            whoseTurn().addItemToCollectAbleItems(cellDestination.getItem());
            cellDestination.setItem(null);
        }
        cellDestination.enterCell();
        // if cell has buff ?!
        return "ok";
    }

    public String attackCombo(String opponentCardID, String... cardIDs) {
        Minion opponentCard = (Minion) returnCardFromBoard(opponentCardID, theOtherPlayer());
        if (opponentCard == null)
            return "invalid card ID";
        for (String cardID : cardIDs) {
            Card card = returnCardFromBoard(cardID, whoseTurn());
            if (card == null || !((Minion) card).getCanAttack() || !((Minion) card).getAttackType().equals(AttackType.COMBO))
                continue;
            attack(opponentCardID, true, (Minion) card);
        }
        Minion firstMinion = (Minion) returnCardFromBoard(cardIDs[0], whoseTurn());
        opponentCard.counterAttack(firstMinion);
        check();
        return "combo attack successfully done";
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
            if (whoseTurn().isPutInGroundAttackEnemyHero()){
                whoseTurn().action(x, y);
            }

            this.selectedCard = card;
            ((Minion) card).setCoordinate(x, y);
            if (((Minion) card).getAttackType().equals(AttackType.ON_SPAWN))
                ((Minion) card).useSpecialPower(((Minion) card).getXCoordinate(), ((Minion) card).getYCoordinate());
            card.setUserName(whoseTurn().getUserName());
            whoseTurn().lessMana(((Minion) card).getManaPoint());
            whoseTurn().removeCardFromHand(card);
            cell.setCard(card);
            cell.enterCell();
            check();
            if (((Minion) card).getAttackType().equals(AttackType.ON_SPAWN))
                ((Minion) card).useSpecialPower(x, y);
            return "ok";

        } else if (card instanceof Spell) {

            if (whoseTurn().getMana() < ((Spell) card).getManaPoint())
                return "you don't have enough mana";
            ((Spell) card).action(x, y);
            this.whoseTurn().removeCardFromHand(card);
            check();
            return "spell successfully inserted";
        }

        // if cell has buff ?!
        check();
        return "ok";
    }

    public String showCollectAble() {
        StringBuilder toShow = new StringBuilder();
        ArrayList<Item> toPrint = whoseTurn().getCollectAbleItems();
        if (toPrint.size() == 0) {
            toShow.append("you dont have item right know ");
        } else {
            for (int i = 0; i < toPrint.size(); i++) {
                toShow.append(i).append(": ").append(toPrint.get(i).getName()).append(" ").append(toPrint.get(i).getDesc()).append("\n");
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

    public String attack(String opponentCardId, boolean isComboAttack, Minion comboAttacker) {
        Minion attacker;
        if (isComboAttack)
            attacker = comboAttacker;
        else
            attacker = (Minion) selectedCard;
        if (attacker == null)
            return "you have to select card at first";

        Minion minion = (Minion) returnCardFromBoard(opponentCardId, whoseTurn());
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
        check();
        return attacker.getName() + " attacked to " + minion.getName();
    }

    public String useSpecialPower(int x, int y) {
        if (whoseTurn().getMainDeck().getHero().getManaPoint() > whoseTurn().getMana())
            return "you don't have enough mana";

        whoseTurn().getMainDeck().getHero().useSpecialPower(x, y);
        check();
        return "special power successfully used";
    }

    public String useItem(int x, int y) {
        if (selectedItem == null)
            return "at first you should select a item";
        this.selectedItem.action(x, y);
        check();
        return "item successfully used";

    }

    private Card returnCardFromBoard(String id, Player player) {
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                if (this.board.getCells()[i][j].getCard() == null)
                    continue;
                if (this.board.getCells()[i][j].getCard().getId().equals(id)) {
                    if (cardIsMine(this.board.getCells()[i][j].getCard(), player))
                        return this.board.getCells()[i][j].getCard();
                }
            }
        }
        return null;
    }

    public ArrayList<Minion> minionsAroundCell(int x, int y) {
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
        if (card == null)
            return true;
        // TODO: 2019-05-06
        if (card.getUserName() == null)
            return true;
        return card.getUserName().equals(player.getUserName());
    }

    public void endTurn() {
        this.turn++;
        this.selectedCard = null;
        this.selectedItem = null;
        playerTwo.getMainDeck().getHero().resetMinion();
        playerOne.getMainDeck().getHero().resetMinion();
        playerOne.addCardToHand();
        playerTwo.addCardToHand();
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
        for (Minion minion : getAllMinion()) {
            if (minion.getAttackType().equals(AttackType.PASSIVE))
                minion.useSpecialPower(minion.getXCoordinate(), minion.getYCoordinate());
            minion.passTurn();
        }
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                getCellFromBoard(i + 1, j + 1).passTurn();
            }
        }
        for (Item collectAbleItem : playerOne.getCollectAbleItems()) {
            collectAbleItem.passTurn();
        }
        for (Item collectAbleItem : playerTwo.getCollectAbleItems()) {
            collectAbleItem.passTurn();
        }
        for (int i = 0; i < getAllMinion().size(); i++) {
            getAllMinion().get(i).resetMinion();
        }
    }

    public static Battle getCurrentBattle() {
        return currentBattle;
    }

    public Cell getCellFromBoard(int x, int y) {
        return this.board.getCells()[x - 1][y - 1];
    }

    public ArrayList<Minion> getAllMinion() {
        ArrayList<Minion> minions = new ArrayList<>();
        minions.addAll(minionsOfCurrentPlayer(playerOne));
        minions.addAll(minionsOfCurrentPlayer(playerTwo));
        return minions;
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

    public ArrayList<Cell> getMinionsSquare(int x, int y) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = x - 1; i <= x; i++) {
            for (int j = y - 1; j <= y; j++) {
                cells.add(getCellFromBoard(i + 1, j + 1));
            }
        }
        return cells;
    }

    public ArrayList<Cell> getMinionsCube(int x, int y) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                cells.add(getCellFromBoard(i + i, j + 1));
            }
        }
        return cells;
    }

    protected void setPrice() {
    }

    public String GraveYard_showInfo(String cardID) {
        if (whoseTurn().getGraveYard().size() == 0)
            return "grave yard is empty";
        for (int i = 0; i < whoseTurn().getGraveYard().size(); i++) {
            if (whoseTurn().getGraveYard().get(i).getId().equals(cardID))
                return (whoseTurn().getGraveYard().get(i).getName() + " " + whoseTurn().getGraveYard().get(i).getDesc());
        }
        return "invalid card ID";
    }

    public String GraveYard_showCards() {
        StringBuilder toReturn = new StringBuilder();
        if (whoseTurn().getGraveYard().size() == 0)
            return "grave yard is empty";
        for (int i = 0; i < whoseTurn().getGraveYard().size(); i++) {
            Card card = whoseTurn().getGraveYard().get(i);
            toReturn.append(card.getName()).append(" ").append(card.getDesc()).append("\n");
        }
        return toReturn.toString();
    }

    public ArrayList<Minion> minionsOfCurrentPlayer(Player player) {
        ArrayList<Minion> toReturn = new ArrayList<>();
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                Minion minion = (Minion) getCellFromBoard(i + 1, j + 1).getCard();
                if (minion == null)
                    continue;
                if (cardIsMine(minion, player))
                    toReturn.add(minion);
            }
        }
        return toReturn;
    }

    public void endingGame() {
        if (whoseTurn().equals(playerOne)) {
            gameDataPlayerOne.setMatchState(MatchState.LOSE);
            gameDataPlayerTwo.setMatchState(MatchState.WIN);
            for (int i = 0; i < GameController.getAccounts().size(); i++) {
                if (GameController.getAccounts().get(i).getUserName().equals(playerTwo.getUserName())) {
                    GameController.getAccounts().get(i).incrementNumbOfWins();
                    GameController.getAccounts().get(i).changeDaric(currentBattle.price);
                    GameController.getAccounts().get(i).addGamaData(gameDataPlayerTwo);
                }
                if (GameController.getAccounts().get(i).getUserName().equals(playerOne.getUserName())) {
                    GameController.getAccounts().get(i).incrementNumbOfLose();
                    GameController.getAccounts().get(i).addGamaData(gameDataPlayerOne);
                }
            }
            situationOfGame = playerOne.getUserName() + " loses from " + playerTwo.getUserName();
            currentBattle = null;
            return;
        }
        if (whoseTurn().equals(playerTwo)) {
            gameDataPlayerOne.setMatchState(MatchState.WIN);
            gameDataPlayerTwo.setMatchState(MatchState.LOSE);
            for (int i = 0; i < GameController.getAccounts().size(); i++) {
                if (GameController.getAccounts().get(i).getUserName().equals(playerOne.getUserName())) {
                    GameController.getAccounts().get(i).incrementNumbOfWins();
                    GameController.getAccounts().get(i).addGamaData(gameDataPlayerOne);
                    GameController.getAccounts().get(i).changeDaric(currentBattle.price);
                }
                if (GameController.getAccounts().get(i).getUserName().equals(playerTwo.getUserName())) {
                    GameController.getAccounts().get(i).incrementNumbOfLose();
                    GameController.getAccounts().get(i).addGamaData(gameDataPlayerTwo);
                }
            }
            situationOfGame = playerTwo.getUserName() + " loses from " + playerOne.getUserName();
            currentBattle = null;
        }
    }

    public void check() {
        currentBattle.deletedDeadMinions();
        currentBattle.endGame();
    }

    public void endGame() {
    }

    public String deletedDeadMinions() {
        return "ok";
    }

    public static String getSituationOfGame() {
        return situationOfGame;
    }
}
