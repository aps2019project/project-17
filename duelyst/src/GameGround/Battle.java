package GameGround;


import Appearance.ExceptionEndGame;
import CardCollections.Hand;
import Cards.Card;
import Cards.Item;
import Cards.Minion;
import Cards.Spell;
import Client.*;
import Data.AI;
import Data.GameData;
import Data.MatchState;
import Data.Player;
import Effects.enums.MinionType;
import Effects.enums.SpecialSituation;
import InstanceMaker.CardMaker;
import controller.GameController;
import Effects.enums.AttackType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Battle {
    Player playerOne;
    Player playerTwo;
    Board board;
    private int turn;
    GameMode gameMode;
    Card selectedCard;
    private Item selectedItem;
    GameData gameDataPlayerOne;
    GameData gameDataPlayerTwo;
    static Battle currentBattle;
    protected int price;
    private static String situationOfGame;

    public Battle(Player playerOne, Player playerTwo, GameMode gameMode) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = new Board();
        this.turn = 1;
        this.gameMode = gameMode;
        this.selectedCard = null;
        this.selectedItem = null;
        setHeroesInTheirPosition();
        setItems();
        situationOfGame = "";
    }

    public String setItems() {
        StringBuilder toReturn = new StringBuilder();
        ArrayList<Item> items = CardMaker.getCollectAbleItems();

        for (Item item : items) {
            if (item.getName().equalsIgnoreCase("noosh daroo")) {
                while (true) {
                    int x = new Random().nextInt(5);
                    int y = new Random().nextInt(9);
                    Cell cell = getCellFromBoard(x + 1, y + 1);
                    if (x >= 0 && y >= 0 && x < 5 && y < 9 && cell != null && cell.getCard() == null && !cell.hasFlag() && cell.getItem() == null) {
                        cell.setItem(item);
                        break;
                    }
                }
            }
            if (item.getName().equalsIgnoreCase("exir")) {
                while (true) {
                    int y = new Random().nextInt(9);
                    int x = new Random().nextInt(5);
                    Cell cell = getCellFromBoard(x + 1, y + 1);
                    if (x >= 0 && y >= 0 && x < 5 && y < 9 && cell != null && cell.getCard() == null && cell.getItem() == null && !cell.hasFlag()) {
                        cell.setItem(item);
                        break;
                    }
                }
            }
            if (item.getName().equalsIgnoreCase("majoone mana")) {
                while (true) {
                    int x = new Random().nextInt(5);
                    int y = new Random().nextInt(9);
                    Cell cell = getCellFromBoard(x + 1, y + 1);
                    if (x >= 0 && y >= 0 && x < 5 && y < 9 && cell != null && !cell.hasFlag() && cell.getItem() == null && cell.getCard() == null) {
                        cell.setItem(item);
                        break;
                    }
                }
            }
        }
        return toReturn.toString();
    }

    private void setHeroesInTheirPosition() {
        playerOne.getMainDeck().getHero().setUserName(playerOne.getUserName());
        playerTwo.getMainDeck().getHero().setUserName(playerTwo.getUserName());
        playerOne.getMainDeck().getHero().init();
        playerTwo.getMainDeck().getHero().init();
        playerOne.getMainDeck().getHero().setCoordinate(3, 1);
        playerTwo.getMainDeck().getHero().setCoordinate(3, 9);
        this.board.getCells()[2][0].setCard(this.playerOne.getMainDeck().getHero());
        this.board.getCells()[2][8].setCard(this.playerTwo.getMainDeck().getHero());
    }

    public StringBuilder showGameInfo() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("Player One mana: ").append(playerOne.getMana()).append(" player two mana: ").append(playerTwo.getMana()).append("\n").append("turn:").append(turn).append("\n");
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
        if (whoseTurn().getCollectAbleItems().size() != 0) {
            for (int i = 0; i < whoseTurn().getCollectAbleItems().size(); i++) {
                if (whoseTurn().getCollectAbleItems().get(i).getId().equals(cardItemID)) {
                    this.selectedItem = whoseTurn().getCollectAbleItems().get(i);
                    return "item successfully selected";
                }
            }
        }
        return "invalid card\\item id";
    }

    public String movingCard(int x, int y) throws ExceptionEndGame {
        if (this.selectedCard == null || this.selectedCard instanceof Spell)
            return "first you have to select a card";
        if (x > 5 || y > 9)
            return "invalid target out of range";
        Minion minion = (Minion) this.selectedCard;
        Cell cellFirst = getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate());
        Cell cellDestination = getCellFromBoard(x, y);

        if (cellDestination.getCard() != null || Cell.distance(cellFirst, cellDestination) > minion.getDistanceCanMove())
            return "invalid target";
        if (!minion.isCanMove())
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
        return "ok";
    }

    public String attackCombo(String opponentCardID, String... cardIDs) throws ExceptionEndGame {
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

    public String insertingCardFromHand(String cardName, int x, int y) throws ExceptionEndGame {
        Card card = whoseTurn().getCardFromHand(cardName);
        Cell cell = getCellFromBoard(x, y);

        if (card == null)
            return "can't  find  your  selected  card  :((((";

        if (x > 5 || y > 9)
            return "invalid target - out of range";

        if (cell == null)
            return "invalid target  - there is a issue with your selected cell";

        if (card instanceof Minion) {
            if (cell.getCard() != null)
                return "invalid target - your selected cell already has a card!";
            if (!board.isCoordinateAvailable(this, whoseTurn(), x, y))
                return "invalid  target  -  your  selected  cell  is  too  far  !";
            if (whoseTurn().getMana() < ((Minion) card).getManaPoint())
                return "You  don?t  have  enough  mana  to  insert  this  card";
            ((Minion) card).setCoordinate(x, y);
            if (((Minion) card).getAttackType().equals(AttackType.ON_SPAWN))
                ((Minion) card).useSpecialPower(((Minion) card).getXCoordinate(), ((Minion) card).getYCoordinate());
            if (cell.getItem() != null) {
                whoseTurn().addItemToCollectAbleItems(cell.getItem());
                cell.setItem(null);
            }
            card.setUserName(whoseTurn().getUserName());
            ((Minion) card).init();
            whoseTurn().lessMana(((Minion) card).getManaPoint());
            cell.setCard(card);
            if (((Minion) card).getAttackType().equals(AttackType.ON_SPAWN))
                ((Minion) card).useSpecialPower(x, y);
            if (whoseTurn().getSpecialSituation().equals(SpecialSituation.PUTT_IN_GROUND))
                whoseTurn().useSpecialSituationBuff();
            check();
            return "ok";

        } else if (card instanceof Spell) {
            if (whoseTurn().getMana() < ((Spell) card).getManaPoint())
                return "you don't have enough mana";
            // TODO: 2019-06-22
            ((Spell) card).action(x, y);
            this.whoseTurn().removeCardFromHand(card);
            check();
            this.whoseTurn().lessMana(((Spell) card).getManaPoint());
            return "spell successfully inserted";
        }
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
                toShow.append(i + 1).append(": ").append(toPrint.get(i).getName()).append(" ").append(toPrint.get(i).getDesc()).append("\n");
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

    public String attack(String opponentCardId, boolean isComboAttack, Minion comboAttacker) throws ExceptionEndGame {
        Minion attacker;
        if (isComboAttack)
            attacker = comboAttacker;
        else
            attacker = (Minion) selectedCard;
        if (attacker == null)
            return "you have to select card at first";

        Minion minion = (Minion) returnCardFromBoard(opponentCardId, theOtherPlayer());

        if (minion == null)
            minion = (Minion) returnCardFromBoard(opponentCardId, whoseTurn());

        if (minion == null)
            return "can't find card";

        if (!attacker.getCanAttack())
            return "this  card  doesnt  ability  to  attack  at  this  time";

        Cell cellDestination = this.board.getCells()[minion.getXCoordinate() - 1][minion.getYCoordinate() - 1];
        Cell cellFirst = this.board.getCells()[attacker.getXCoordinate() - 1][attacker.getYCoordinate() - 1];
        switch (attacker.getMinionType()) {
            case MELEE:
                if (!permissionForMeeleAttack(cellFirst, cellDestination))
                    return "type of attacker is MELEE, so opponent minion is invalid";
                break;
            case RANGED:
                if (Cell.distance(cellDestination, cellFirst) < 2)
                    return "type of attacker is RANGED, so opponent minion is invalid  ( distance is close )  -  ATTACK RANGE: " + attacker.getAttackRange();
                if (Cell.distance(cellDestination, cellFirst) > attacker.getAttackRange())
                    return "type of attacker is RANGED, so opponent minion is invalid  ( distance is far )  -  ATTACK RANGE: " + attacker.getAttackRange();
                break;
            case HYBRID:
                break;
        }
        // TODO: 2019-06-24
        System.out.println(attacker.getName() + " health attacker before attack :" + attacker.getHealthPoint());
        System.out.println(minion.getName() + " health defender before attack :" + minion.getHealthPoint());
        attacker.attack(minion);
        minion.counterAttack(attacker);
        System.out.println(attacker.getName() + " health attacker after attack :" + attacker.getHealthPoint());
        System.out.println(minion.getName() + " health defender after attack :" + minion.getHealthPoint());
        check();
        attacker.setCanAttack(false);
        return attacker.getName() + " attacked to " + minion.getName();
    }

    public String useSpecialPower(int x, int y) throws ExceptionEndGame {
        if (whoseTurn().getMainDeck().getHero().getManaPoint() > whoseTurn().getMana())
            return "you don't have enough mana";

        whoseTurn().getMainDeck().getHero().useSpecialPower(x, y);
        check();
        whoseTurn().lessMana(whoseTurn().getMainDeck().getHero().getManaPoint());
        return "special power successfully used";
    }

    public String useItem(int x, int y) throws ExceptionEndGame {
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

    public ArrayList<Cell> getCellsAroundCell(int x, int y) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = x - 2; i <= x; i++) {
            for (int j = y - 2; j <= y; j++) {
                if (i == x - 1 && j == y - 1)
                    continue;
                if (i < 0 || i > 4 || j < 0 || j > 8)
                    continue;
                Cell cell = getCellFromBoard(i + 1, j + 1);
                cells.add(cell);
            }
        }
        return cells;
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
        if (card == null || card.getUserName() == null)
            return false;
        return card.getUserName().equals(player.getUserName());
    }

    public void endTurn() throws ExceptionEndGame {
    }

    private void logicEndTurn() throws ExceptionEndGame {
        this.turn++;
        this.selectedCard = null;
        this.selectedItem = null;
        playerTwo.getMainDeck().getHero().resetMinion(turn);
        playerOne.getMainDeck().getHero().resetMinion(turn);
        playerOne.addCardToHand();
        playerTwo.addCardToHand();
        if (this.turn >= 15) {
            this.playerOne.setMana(9);
            this.playerTwo.setMana(9);
        } else {
            if (this.turn % 2 == 0) {
                this.playerTwo.setMana(this.playerTwo.getPreviousMana() + 1);
                this.playerTwo.setPreviousMana(this.playerTwo.getMana());
            } else if (this.turn % 2 == 1) {
                this.playerOne.setMana(this.playerOne.getPreviousMana() + 1);
                this.playerOne.setPreviousMana(this.playerOne.getMana());
            }
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
        whoseTurn().passTurn();
        if (currentBattle != null)
            currentBattle.check();
        for (int i = 0; i < getAllMinion().size(); i++) {
            getAllMinion().get(i).resetMinion(turn);
        }
    }

    public static Battle getCurrentBattle() {
        return currentBattle;
    }

    public Cell getCellFromBoard(int x, int y) {
        return this.board.getCells()[x - 1][y - 1];
    }

    public ArrayList<Cell> getAllCells() {
        ArrayList<Cell> toReturn = new ArrayList<>();
        for (Cell[] cells : board.getCells()) {
            toReturn.addAll(Arrays.asList(cells));
        }
        return toReturn;
    }

    public ArrayList<Minion> getAllMinion() {
        ArrayList<Minion> minions = new ArrayList<>();
        minions.addAll(minionsOfCurrentPlayer(playerOne));
        minions.addAll(minionsOfCurrentPlayer(playerTwo));
        return minions;
    }

    public ArrayList<Cell> returnCellsInColumn(int x, int y) {
        ArrayList<Cell> toReturn = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (getCellFromBoard(x, i + 1).getCard() != null)
                toReturn.add(getCellFromBoard(x, i + 1));
        }
        return toReturn;
    }

    public ArrayList<Cell> getCellsWhichDistance(int x, int y) {
        ArrayList<Cell> toReturn = new ArrayList<>();
        for (int i = 0; i < this.board.getCells().length; i++) {
            for (int j = 0; j < this.board.getCells()[i].length; j++) {
                if (Cell.distance(getCellFromBoard(x, y), getCellFromBoard(i + 1, j + 1)) <= 2 && getCellFromBoard(i + 1, j + 1).getCard() != null)
                    toReturn.add(getCellFromBoard(i + 1, j + 1));
            }
        }
        return toReturn;
    }

    public Minion returnRandomMinion(int x, int y) {
        int xR = new Random().nextInt() % 6;
        int yR = new Random().nextInt() % 10;

        while (xR <= 0 || yR <= 0 || getCellFromBoard(xR, yR).getCard() == null || getCellFromBoard(xR, yR).getCard() instanceof Spell) {
            xR = new Random().nextInt() % 6;
            yR = new Random().nextInt() % 10;
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
                if (i > 4 || j > 8 || i < 0 || j < 0)
                    continue;
                cells.add(getCellFromBoard(i + 1, j + 1));
            }
        }
        return cells;
    }

    public ArrayList<Cell> getMinionsCube(int x, int y) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i > 4 || j > 8 || i < 0 || j < 0)
                    continue;
                cells.add(getCellFromBoard(i + 1, j + 1));
            }
        }
        return cells;
    }

    public Minion targetForAttackAI() {
        if (selectedCard == null)
            return null;
        int x = ((Minion) selectedCard).getXCoordinate() - 1;
        int y = ((Minion) selectedCard).getYCoordinate() - 1;
        int radius;
        int shift;
        if (((Minion) selectedCard).getMinionType().equals(MinionType.MELEE)) {
            radius = 1;
            shift = 0;
        } else if (((Minion) selectedCard).getMinionType().equals(MinionType.RANGED)) {
            radius = ((Minion) selectedCard).getAttackRange() / 2;
            shift = 2;
        } else {
            radius = ((Minion) selectedCard).getAttackRange() / 2;
            shift = 0;
        }
        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                if (i > 4 || j > 8 || i < 0 || j < 0)
                    continue;
                Cell cell = getCellFromBoard(i + 1, j + 1);
                if (cell.getCard() == null)
                    continue;
                if (cell.getCard().getUserName().equals(theOtherPlayer().getUserName()) && Cell.distance(cell, getCellFromBoard(x + 1, y = 1)) > shift)
                    return (Minion) cell.getCard();
            }
        }
        return null;
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
        if (playerOneEndedTheGame()) return;
        playerTwoEndTheGame();
    }

    private void playerTwoEndTheGame() {
        if (whoseTurn().equals(playerTwo)) {
            gameDataPlayerOne.setMatchState(MatchState.WIN);
            if (gameMode.equals(GameMode.MULTI_PLAYER))
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
            situationOfGame = playerTwo.getUserName() + " loses from " + playerOne.getUserName().concat(" and win ".concat(Integer.toString(this.price)));
            currentBattle = null;
        }
    }

    private boolean playerOneEndedTheGame() {
        if (whoseTurn().equals(playerOne)) {
            gameDataPlayerOne.setMatchState(MatchState.LOSE);
            if (gameMode.equals(GameMode.MULTI_PLAYER)) {
                gameDataPlayerTwo.setMatchState(MatchState.WIN);
            }
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
            situationOfGame = playerOne.getUserName() + " loses from " + playerTwo.getUserName().concat(" and win ".concat(Integer.toString(this.price)));
            currentBattle = null;
            return true;
        }
        return false;
    }

    void check() throws ExceptionEndGame {
        currentBattle.deletedDeadMinions();
        currentBattle.endGame();
    }

    public void endGame() throws ExceptionEndGame {
    }

    public String deletedDeadMinions() {
        return "ok";
    }

    public static String getSituationOfGame() {
        return situationOfGame;
    }

    public Board getBoard() {
        return board;
    }

    void playerOneWon() throws ExceptionEndGame {
        gameDataPlayerOne.setMatchState(MatchState.WIN);
        if (gameMode.equals(GameMode.MULTI_PLAYER))
            gameDataPlayerTwo.setMatchState(MatchState.LOSE);
        loginOfEnding(playerOne, gameDataPlayerOne, playerTwo, gameDataPlayerTwo);
        Client.send(new Message(playerOne.getUserName().trim() + " win earn " + price));
        Client.send(new Message(playerTwo.getUserName().trim() + " lose"));
        currentBattle = null;
        throw new ExceptionEndGame(situationOfGame);
    }

    void playerTwoWon() throws ExceptionEndGame {
        if (gameMode.equals(GameMode.MULTI_PLAYER))
            gameDataPlayerTwo.setMatchState(MatchState.WIN);
        gameDataPlayerOne.setMatchState(MatchState.LOSE);
        Client.send(new Message(playerTwo.getUserName().trim() + " win earn " + price));
        Client.send(new Message(playerOne.getUserName().trim() + " lose"));
        loginOfEnding(playerTwo, gameDataPlayerTwo, playerOne, gameDataPlayerOne);
        currentBattle = null;
        throw new ExceptionEndGame(situationOfGame);
    }

    private void loginOfEnding(Player playerOne, GameData gameDataPlayerOne, Player playerTwo, GameData gameDataPlayerTwo) {
        for (int i = 0; i < GameController.getAccounts().size(); i++) {
            System.out.println(price);
            if (GameController.getAccounts().get(i).getUserName().equals(playerOne.getUserName())) {
                GameController.getAccounts().get(i).changeDaric(price);
                GameController.getAccounts().get(i).incrementNumbOfWins();
                GameController.getAccounts().get(i).addGamaData(gameDataPlayerOne);
                Client.send(new Message(playerOne.getUserName().trim() + " win earn " + price));
                continue;
            }
            if (GameController.getAccounts().get(i).getUserName().equals(playerTwo.getUserName())) {
                GameController.getAccounts().get(i).incrementNumbOfLose();
                GameController.getAccounts().get(i).addGamaData(gameDataPlayerTwo);
                Client.send(new Message(playerTwo.getUserName().trim() + " lose"));
            }
        }
        situationOfGame = playerOne.getUserName() + " win from " + playerTwo.getUserName() + " and earn " + currentBattle.price;
    }

    void endingTurn() throws ExceptionEndGame {
        switch (gameMode) {
            case SINGLE_PLAYER:
                logicEndTurn();
                if (playerTwo instanceof AI)
                    ((AI) playerTwo).actionTurn();
                logicEndTurn();
                break;
            case MULTI_PLAYER:
                logicEndTurn();
                break;
        }
    }

    public boolean setSelectedCard(Cell cell) {
        Card card = cell.getCard();
        if (card == null)
            return false;
        if (!cardIsMine(card, whoseTurn()))
            return false;
        this.selectedCard = card;
        return true;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setSelectedCardNull() {
        this.selectedCard = null;
    }

    private boolean permissionForMeeleAttack(Cell first, Cell destination) {
        if (Cell.distance(first, destination) > 2)
            return false;
        if (Cell.distance(first, destination) <= 1)
            return true;
        if (Cell.distance(first, destination) == 2)
            return (first.getRow() != destination.getRow()) && (first.getCol() != destination.getCol());

        return false;
    }

    public int getTurn() {
        return turn;
    }

    public void setSelectedItem(Item item) {
        this.selectedItem = item;
    }

    public static void setCurrentBattleNull() {
        currentBattle = null;
    }
}
