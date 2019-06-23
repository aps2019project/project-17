import Appearance.ColorAppearance;
import Appearance.ExceptionEndGame;
import Appearance.FontAppearance;
import Appearance.MinionAppearance;
import Cards.*;
import Data.AI;
import Data.Account;
import GameGround.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class BattleAppearance {
    private Group root;
    private Scene battleScene;
    private CellAppearance[][] board;
    private Rectangle[][] boardBackGround;
    private Rectangle[] shapeOfHealthHero = new Rectangle[2];
    private Text[] textsOfBattle;
    private Rectangle[] manaIconImage;
    private Rectangle endTurnButton;
    private Rectangle nextCardOpponent;
    private Rectangle graveYardButton;
    private HandAppearance handAppearance;
    private ItemAppearance itemAppearance;
    private ImageView nextCardOpponentImage;
    private ArrayList<MinionAppearance> minionAppearanceOfBattle = new ArrayList<>();
    private Cell currentSelectedCell;
    private static BattleAppearance currentBattleAppearance;

    public BattleAppearance() {
        currentBattleAppearance = this;
        primaryInitializes();
        setBackGround();
        secondaryInitializes();
        setStuffs();
        handleEvents();
        disPlay();
    }

    private void primaryInitializes() {
        this.root = new Group();
        this.battleScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
        this.board = new CellAppearance[5][9];
        this.boardBackGround = new Rectangle[5][9];
        this.textsOfBattle = new Text[]{new Text("End Turn"),
                new Text(Battle.getCurrentBattle().getPlayerOne().getUserName()),
                new Text(AI.getCurrentAIPlayer().getUserName()),
                new Text(Integer.toString(Battle.getCurrentBattle().getPlayerTwo().getMana()).concat("  /  9")),
                new Text(Integer.toString(Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getHealthPoint())),
                new Text((Integer.toString(Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().getHealthPoint())))
                , new Text("Grave Yard")};
        this.manaIconImage = new Rectangle[9];
        this.currentSelectedCell = null;
        this.nextCardOpponentImage = new ImageView();
    }

    private void setBackGround() {
        Image image;
        try {
            image = new Image(new FileInputStream("board5.png"));
            ImageView imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(battleScene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(battleScene.heightProperty());
            root.getChildren().add(imageOfBackGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void secondaryInitializes() {
        initBoardBG();
        initializeCells();
        initPlaceOfHeroes();
        locateNodes();
        initPlayersAppearance();
        initHand();
        initItemList();
        addCells();
    }

    private void initBoardBG() {
        for (int i = 0; i < boardBackGround.length; i++) {
            for (int j = 0; j < boardBackGround[i].length; j++) {
                boardBackGround[i][j] = new Rectangle(Main.WIDTH_OF_WINDOW / 17, Main.HEIGHT_OF_WINDOW / 10);
                boardBackGround[i][j].setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                boardBackGround[i][j].setOpacity(0.2);
                if (j == 0) {
                    if (i == 0) {
                        boardBackGround[i][j].setLayoutX(Main.WIDTH_OF_WINDOW / 4.6);
                        boardBackGround[i][j].setLayoutY(Main.HEIGHT_OF_WINDOW / 4);
                    } else {
                        boardBackGround[i][j].setLayoutX(boardBackGround[i - 1][j].getLayoutX());
                        boardBackGround[i][j].setLayoutY(boardBackGround[i - 1][j].getLayoutY() + Main.HEIGHT_OF_WINDOW / 9.2);
                    }
                } else {
                    boardBackGround[i][j].setLayoutX(boardBackGround[i][j - 1].getLayoutX() + Main.WIDTH_OF_WINDOW / 16.75);
                    boardBackGround[i][j].setLayoutY(boardBackGround[i][j - 1].getLayoutY());
                }
            }
            root.getChildren().addAll(boardBackGround[i]);
        }
    }

    private void initializeCells() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = new CellAppearance(Battle.getCurrentBattle().getBoard().getCells()[i][j]);

    }

    private void initPlaceOfHeroes() {
        Hero heroFirst = Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero();
        Hero heroSecond = Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero();
        MinionAppearance a = new MinionAppearance(heroFirst, heroFirst.getName(), root);
        MinionAppearance b = new MinionAppearance(heroSecond, heroSecond.getName(), root);
        minionAppearanceOfBattle.add(a);
        minionAppearanceOfBattle.add(b);
        a.add(false);
        a.setLocation(0.975 * board[2][0].getCellRectangle().getLayoutX(), 0.92 * board[2][0].getCellRectangle().getLayoutY());
        a.breathing();
        a.getImageView().setOpacity(1);
        a.setInHand(false);
        a.setInInBoard(true);
    }

    private void locateNodes() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].getCellRectangle().setLayoutX(boardBackGround[i][j].getLayoutX());
                board[i][j].getCellRectangle().setLayoutY(boardBackGround[i][j].getLayoutY());
            }
        }
    }

    private void initPlayersAppearance() {
        for (int i = 0; i < Battle.getCurrentBattle().getPlayerOne().getMainDeck().getCards().size(); i++) {
            Card card = Battle.getCurrentBattle().getPlayerOne().getMainDeck().getCards().get(i);
            if (card instanceof Minion)
                minionAppearanceOfBattle.add(new MinionAppearance((Minion) card, card.getName(), root));
        }
        for (int i = 0; i < Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getCards().size(); i++) {
            Card card = Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getCards().get(i);
            if (card instanceof Minion)
                minionAppearanceOfBattle.add(new MinionAppearance((Minion) card, card.getName(), root));
        }
    }

    private void initHand() {
        this.handAppearance = new HandAppearance(this.root);
    }

    private void initItemList() {
        Item[] itemsToBePassed = new Item[6];
//        if (Account.getLoginUser().getMainDeck().getItem() != null) {
//            itemsToBePassed[0] = Account.getLoginUser().getMainDeck().getItem();
//            for (int i = 0; i < Account.getLoginUser().getPlayer().getCollectAbleItems().size(); i++) {
//                itemsToBePassed[i + 1] = Account.getLoginUser().getPlayer().getCollectAbleItems().get(i);
//            }
//        } else {
        for (int i = 0; i < Account.getLoginUser().getPlayer().getCollectAbleItems().size(); i++) {
            itemsToBePassed[i] = Account.getLoginUser().getPlayer().getCollectAbleItems().get(i);
            //}
        }
        this.itemAppearance = new ItemAppearance(this.root, itemsToBePassed);
    }

    private void addCells() {
        for (CellAppearance[] cellAppearances : board)
            for (CellAppearance cellAppearance : cellAppearances)
                cellAppearance.add(root);
    }

    private void setStuffs() {
        setEndTurnButton();
        setImagesOfPlayers();
        setShapeOfHealthHero();
        setManaIcons();
        setAppearanceOfTexts();
        setShapeOfHealthHeroTexts();
        setAppearanceOfCells();
        setNextCardOpponent();
    }

    private void setEndTurnButton() {
        endTurnButton = new Rectangle(Main.WIDTH_OF_WINDOW / 7.58, Main.HEIGHT_OF_WINDOW / 11.12);
        graveYardButton = new Rectangle(Main.WIDTH_OF_WINDOW / 7.58, Main.HEIGHT_OF_WINDOW / 11.12);
        textsOfBattle[6].setFont(FontAppearance.FONT_BUTTON);
        textsOfBattle[6].setFill(Color.WHITE);
        try {
            endTurnButton.setFill(new ImagePattern(new Image(new FileInputStream("end_turn.png"))));
            StackPane stackPane0 = new StackPane(endTurnButton, textsOfBattle[0]);
            stackPane0.setLayoutX(Main.WIDTH_OF_WINDOW * 8 / 10);
            stackPane0.setLayoutY(Main.HEIGHT_OF_WINDOW * 8.5 / 10);
            root.getChildren().addAll(stackPane0);
            graveYardButton.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            StackPane stackPane = new StackPane(graveYardButton, textsOfBattle[6]);
            stackPane.setLayoutX(Main.WIDTH_OF_WINDOW * 8 / 10);
            stackPane.setLayoutY(Main.HEIGHT_OF_WINDOW * 7.5 / 10);
            stackPane.setMaxWidth(stackPane0.getMinWidth());
            stackPane.setMaxHeight(stackPane0.getMinHeight());
            root.getChildren().addAll(stackPane);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setImagesOfPlayers() {
        Rectangle rectangle1 = new Rectangle(Main.WIDTH_OF_WINDOW / 9, Main.HEIGHT_OF_WINDOW / 4.77);
        Rectangle rectangle2 = new Rectangle(Main.WIDTH_OF_WINDOW / 9, Main.HEIGHT_OF_WINDOW / 4.77);
        try {
            rectangle1.setFill(new ImagePattern(new Image(new FileInputStream("player1.png"))));
            rectangle2.setFill(new ImagePattern(new Image(new FileInputStream("player2.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        rectangle1.setLayoutX(Main.WIDTH_OF_WINDOW / 40);
        rectangle1.setLayoutY(Main.HEIGHT_OF_WINDOW / 200);
        rectangle2.setLayoutX(Main.WIDTH_OF_WINDOW * 8.5 / 10);
        rectangle2.setLayoutY(Main.HEIGHT_OF_WINDOW / 200);
        root.getChildren().addAll(rectangle1, rectangle2);
    }

    private void setShapeOfHealthHero() {
        shapeOfHealthHero[0] = new Rectangle(40, 40);
        shapeOfHealthHero[1] = new Rectangle(40, 40);
        try {
            for (int i = 0; i < shapeOfHealthHero.length; i++) {
                shapeOfHealthHero[i].setFill(new ImagePattern(new Image(new FileInputStream("hp_hero.png"))));
                if (i == 0) {
                    shapeOfHealthHero[0].setLayoutX(Main.WIDTH_OF_WINDOW / 14.85);
                    shapeOfHealthHero[0].setLayoutY(Main.HEIGHT_OF_WINDOW / 5.3);
                } else {
                    shapeOfHealthHero[1].setLayoutX(13.25 * Main.WIDTH_OF_WINDOW / 14.85);
                    shapeOfHealthHero[1].setLayoutY(Main.HEIGHT_OF_WINDOW / 5.3);
                }
            }
            root.getChildren().addAll(shapeOfHealthHero);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setManaIcons() {
        for (int i = 0; i < manaIconImage.length; i++) {
            try {
                manaIconImage[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 48, Main.HEIGHT_OF_WINDOW / 27.8);
                manaIconImage[i].setFill(new ImagePattern(new Image(new FileInputStream("icon_mana2.png"))));
                manaIconImage[i].setLayoutY(Main.HEIGHT_OF_WINDOW / 8.5);
                manaIconImage[i].setOpacity(0.3);
                manaIconImage[i].setLayoutX(Main.WIDTH_OF_WINDOW / 8 + (i * Main.WIDTH_OF_WINDOW / 48));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        root.getChildren().addAll(manaIconImage);
        setManaIconImageLights();
    }

    private void setAppearanceOfTexts() {
        for (int i = 0; i < 6; i++) {
            textsOfBattle[i].setFont(FontAppearance.FONT_BUTTON);
            if (i == 1 || i == 2 || i == 4 || i == 5)
                textsOfBattle[i].setFill(Color.WHITE);
        }
        textsOfBattle[0].setFill(Color.WHITE);
        textsOfBattle[1].setLayoutX(Main.WIDTH_OF_WINDOW / 7.78);
        textsOfBattle[1].setLayoutY(Main.HEIGHT_OF_WINDOW / 9.86);
        textsOfBattle[2].setLayoutX(Main.WIDTH_OF_WINDOW / 1.286);
        textsOfBattle[2].setLayoutY(Main.HEIGHT_OF_WINDOW / 9.86);
        textsOfBattle[3].setFill(ColorAppearance.BACKGROUND_DATA_CARDS);
        textsOfBattle[3].setLayoutX(12 * Main.WIDTH_OF_WINDOW / 14.85);
        textsOfBattle[3].setLayoutY(0.73 * Main.HEIGHT_OF_WINDOW / 5.3);
        textsOfBattle[4].setLayoutX(shapeOfHealthHero[0].getLayoutX() * 1.08);
        textsOfBattle[4].setLayoutY(shapeOfHealthHero[0].getLayoutY() * 1.18);
        textsOfBattle[5].setLayoutX(shapeOfHealthHero[1].getLayoutX() * 1.008);
        textsOfBattle[5].setLayoutY(shapeOfHealthHero[1].getLayoutY() * 1.18);
        root.getChildren().addAll(textsOfBattle[1], textsOfBattle[2], textsOfBattle[3], textsOfBattle[4], textsOfBattle[5]);
    }

    private void setShapeOfHealthHeroTexts() {
        textsOfBattle[4].setText(Integer.toString(Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getHealthPoint()));
        textsOfBattle[5].setText(Integer.toString(Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().getHealthPoint()));
    }

    public void setAppearanceOfCells() {
        if (Battle.getCurrentBattle() == null) {
            new MainMenu();
            return;
        }
        for (CellAppearance[] cellAppearances : board) {
            for (CellAppearance cellAppearance : cellAppearances) {
                cellAppearance.setCellAppearance();
            }
        }
        itemAppearance.delete(root);
        initItemList();
    }

    public void setNextCardOpponent() {
        final Rectangle templateOfNextCardOpponent = new Rectangle(Main.WIDTH_OF_WINDOW / 13, Main.HEIGHT_OF_WINDOW / 7);
        this.nextCardOpponent = new Rectangle(Main.WIDTH_OF_WINDOW / 26, Main.HEIGHT_OF_WINDOW / 14);
        try {
            this.nextCardOpponent.setFill(new ImagePattern(new Image(new FileInputStream("blank_template.png"))));
            templateOfNextCardOpponent.setFill(new ImagePattern(new Image(new FileInputStream("blank_template.png"))));
            this.nextCardOpponent.setLayoutX(Main.WIDTH_OF_WINDOW * 9.8 / 11);
            this.nextCardOpponent.setLayoutY(Main.HEIGHT_OF_WINDOW / 3.5);
            templateOfNextCardOpponent.setLayoutX(Main.WIDTH_OF_WINDOW * 9.4 / 11);
            templateOfNextCardOpponent.setLayoutY(Main.HEIGHT_OF_WINDOW / 4);
            this.root.getChildren().add(templateOfNextCardOpponent);
            this.root.getChildren().add(this.nextCardOpponent);
            setImageOfOpponentNextCard();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setImageOfOpponentNextCard() {
        this.nextCardOpponent.setVisible(false);
        if (this.nextCardOpponentImage != null)
            this.root.getChildren().remove(nextCardOpponentImage);
        if (Battle.getCurrentBattle().getPlayerTwo().getNextCard() instanceof Minion) {
            System.out.println("minion -> next card of opponent : " + Battle.getCurrentBattle().getPlayerTwo().getNextCard().getName());
            MinionAppearance minionAppearance = getMinionAppearanceOfBattle((Minion) Battle.getCurrentBattle().getPlayerTwo().getNextCard(), true);
            minionAppearance.add(true);
            minionAppearance.setLocation(Main.WIDTH_OF_WINDOW * 9.4 / 11, Main.HEIGHT_OF_WINDOW / 4);
            this.nextCardOpponentImage = minionAppearance.getImageView();
            minionAppearance.breathing();
        } else if (Battle.getCurrentBattle().getPlayerTwo().getNextCard() instanceof Spell) {
            System.out.println("spell -> next card of opponent : " + Battle.getCurrentBattle().getPlayerTwo().getNextCard().getName());
            try {
                this.nextCardOpponent.setVisible(true);
                this.nextCardOpponent.setFill(new ImagePattern(new Image(new FileInputStream("spell.gif"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setFlagsItemsAppearance() {
        for (CellAppearance[] cellAppearances : board) {
            for (CellAppearance cellAppearance : cellAppearances) {
                cellAppearance.checkFlagsItems();
            }
        }
        itemAppearance.delete(root);
        initItemList();
    }


    private void disPlay() {
        Main.getWindow().setScene(battleScene);
        setManaIconImageLights();
    }

    private void handleEvents() {
        textsOfBattle[0].setOnMouseClicked(e -> {
            try {
                Battle.getCurrentBattle().endTurn();
            } catch (ExceptionEndGame exceptionEndGame) {
                ErrorOnBattle.display(Battle.getSituationOfGame());
                new MainMenu();
            }
            AITurn();
            setManaIconImageLights();
            setImageOfOpponentNextCard();
//            setAppearanceOfCells();
            handAppearance.insert();
            handAppearance = new HandAppearance(root);
            itemAppearance.delete(root);
            initItemList();
            if (Battle.getCurrentBattle().whoseTurn().equals(Battle.getCurrentBattle().getPlayerOne()))
                System.out.println("its your turn ! action");
//            currentBattleAppearance = null;
//            Battle.getCurrentBattle().endingGame();
//            new MainMenu();
        });
        endTurnButton.setOnMouseClicked(e -> {// TODO: 6/13/2019 items must be handled here
            itemAppearance.delete(root);
            initItemList();
//            currentBattleAppearance = null;
//            Battle.getCurrentBattle().endingGame();
//            new MainMenu();
            try {
                Battle.getCurrentBattle().endTurn();
            } catch (ExceptionEndGame exceptionEndGame) {
                ErrorOnBattle.display(Battle.getSituationOfGame());
                new MainMenu();
            }
            AITurn();
            setManaIconImageLights();
            setImageOfOpponentNextCard();
//            setAppearanceOfCells();
            handAppearance.insert();
            handAppearance = new HandAppearance(root);
            if (Battle.getCurrentBattle().whoseTurn().equals(Battle.getCurrentBattle().getPlayerOne()))
                System.out.println("its your turn ! action");
        });

        textsOfBattle[6].setOnMouseClicked(event -> new GraveYardAppearance());

        graveYardButton.setOnMouseClicked(event -> new GraveYardAppearance());
    }

    private void AITurn() {
        if (AI.getResults() == null || AI.getResults().size() == 0)
            return;
        for (int i = 0; i < AI.getResults().size(); i++) {
            String[] commands = AI.getResults().get(i).split(" ");
            if (commands[0].equals("moved")) {
                StringBuilder nameSB = new StringBuilder();
                int index = 0;
                System.out.println(Arrays.toString(commands));
                for (int j = 1; j < commands.length; j++) {
                    if (!commands[j].equals("->"))
                        nameSB.append(commands[j].concat(" "));
                    else {
                        index = j;
                        break;
                    }
                }
                String name = nameSB.toString().trim();
                System.out.println(name);
                final int x0 = Integer.parseInt(commands[index + 1]);
                final int y0 = Integer.parseInt(commands[index + 2]);
                final int x = Integer.parseInt(commands[index + 3]);
                final int y = Integer.parseInt(commands[index + 4]);
                System.out.println(x0 + " " + y0 + " " + x + " " + y);
                Minion minion;
                MinionAppearance minionAppearance;
                minion = getMinionOfAiFromName(name, null);
                if (minion != null) {
                    minionAppearance = getMinionAppearanceOfBattle(minion, false);
                    if (minionAppearance != null) {
                        minionAppearance.setInHand(false);
                        minionAppearance.setInInBoard(true);
                        minionAppearance.add(false);
                        double xSource = board[x0 - 1][y0 - 1].getCellRectangle().getLayoutX();
                        double ySource = board[x0 - 1][y0 - 1].getCellRectangle().getLayoutY();
                        double xDestination = board[x - 1][y - 1].getCellRectangle().getLayoutX();
                        double yDestination = board[x - 1][y - 1].getCellRectangle().getLayoutY();
                        minionAppearance.setLocation(0.975 * xSource, 0.92 * ySource);
                        minionAppearance.move(0.975 * xDestination - minionAppearance.getImageView().getLayoutX(), 0.92 * yDestination - minionAppearance.getImageView().getLayoutY());
                    }
                }
            } else if (commands[0].equals("insert")) {
                StringBuilder nameSB = new StringBuilder();
                for (int j = 1; j < commands.length; j++) {
                    nameSB.append(commands[j].concat(" "));
                }
                String name = nameSB.toString().trim();
                Minion minion = null;
                minion = getMinionOfAiFromName(name, minion);
                if (minion != null) {
                    MinionAppearance minionAppearance = getMinionAppearanceOfBattle(minion, true);
                    if (minionAppearance != null) {
                        minionAppearance.setInHand(false);
                        minionAppearance.setInInBoard(true);
                        setAppearanceOfCells();
                        continue;
                    }
                }
            } else if (commands[0].equals("attack")) {
                StringBuilder nameSb = new StringBuilder();
                for (int j = 1; j < commands.length - 2; j++) {
                    nameSb.append(commands[j]);
                }
                String name = nameSb.toString().trim();
                int xDefender = Integer.parseInt(commands[commands.length - 2]);
                int yDefender = Integer.parseInt(commands[commands.length - 1]);
                Minion attacker = getMinionOfAiFromName(name, null);
                Minion defender = (Minion) Battle.getCurrentBattle().getCellFromBoard(xDefender, yDefender).getCard();
                if (attacker == null || defender == null)
                    continue;
                MinionAppearance minionAppearanceAttacker = getMinionAppearanceOfBattle(attacker, false);
                MinionAppearance minionAppearanceDefender = getMinionAppearanceOfBattle(defender, false);
                minionAppearanceAttacker.attack();
                minionAppearanceDefender.hit();
                if (defender.isCanCounterAttack()){
                    minionAppearanceAttacker.hit();
                    minionAppearanceDefender.attack();
                }
            }
            setFlagsItemsAppearance();
        }
    }

    private Minion getMinionOfAiFromName(String name, Minion minion) {
        for (int j = 0; j < Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerTwo()).size(); j++) {
            if (Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerTwo()).get(j).getName().equals(name)) {
                minion = Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerTwo()).get(j);
                break;
            }
        }
        return minion;
    }

    public HandAppearance getHandAppearance() {
        return handAppearance;
    }

    public ItemAppearance getItemAppearance(){
        return itemAppearance;
    }

    public static BattleAppearance getCurrentBattleAppearance() {
        return currentBattleAppearance;
    }

    public void setManaIconImageLights() {
        int mana = Account.getLoginUser().getPlayer().getMana();
        for (int i = 0; i < manaIconImage.length; i++) {
            if (i < mana)
                manaIconImage[i].setOpacity(1);
            else manaIconImage[i].setOpacity(0.3);
        }
    }

    public Group getRoot() {
        return root;
    }

    public CellAppearance[][] getBoard() {
        return board;
    }

    public Rectangle[][] getBoardBackGround() {
        return boardBackGround;
    }

    public MinionAppearance getMinionAppearanceOfBattle(Minion minion, boolean isHand) {
        for (MinionAppearance minionAppearance : minionAppearanceOfBattle) {
            if (minionAppearance.getMinion() == minion)
                if (minionAppearance.isInHand() == isHand)
                    return minionAppearance;

        }
        return null;
    }

    public void insert() {
        this.handAppearance = new HandAppearance(root);
    }

    public ArrayList<MinionAppearance> getMinionAppearanceOfBattle() {
        return minionAppearanceOfBattle;
    }

    public void setHandAppearance(HandAppearance handAppearance) {
        this.handAppearance = handAppearance;
    }

    public Cell getCurrentSelectedCell() {
        return currentSelectedCell;
    }

    public void setCurrentSelectedCell(Cell currentSelectedCell) {
        this.currentSelectedCell = currentSelectedCell;
    }
}
