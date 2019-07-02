import Appearance.FontAppearance;
import Cards.Hero;
import Cards.Minion;
import Effects.enums.AttackType;
import Effects.enums.MinionType;
import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class CustomHeroWindow {

    private Group root = new Group();
    private Scene customHeroScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
    private TextField name = new TextField();
    private TextField id = new TextField();
    private TextField price = new TextField();
    private TextField manaPoint = new TextField();
    private TextField healthPoint = new TextField();
    private TextField attackPower = new TextField();
    private TextField attackRange = new TextField();
    private TextField distanceCanMove = new TextField();
    private TextField maxRangeToInput = new TextField();
    private TextField desc = new TextField();
    private TextField coolDown = new TextField();
    private Text nameTxt = new Text("Name");
    private Text idTxt = new Text("ID");
    private Text priceTxt = new Text("Price");
    private Text manaPointTxt = new Text("ManaPoint");
    private Text healthPointTxt = new Text("HealthPoint");
    private Text attackPowerTxt = new Text("AttackPower");
    private Text attackRangeTxt = new Text("AttackRange");
    private Text distanceCanMoveTxt = new Text("Distance Can Move");
    private Text maxRangeToInputTxt = new Text("Max Range To Input");
    private Text descTxt = new Text("Description");
    private Text attackTypeTxt = new Text("AttackType");
    private Text minionTypeTxt = new Text("MinionType");
    private Text coolDownTxt = new Text("CoolDown");
    private ChoiceBox<String> attackType = new ChoiceBox<>();
    private ChoiceBox<String> minionType = new ChoiceBox<>();
    private Rectangle buildButton;
    private Text buildTxt = new Text("Build");

    CustomHeroWindow(String type) {
        setNodesView();
        setBackGround();
        setBuildButton();
        locate();
        if (type.equals("hero")) {
            locateCoolDown();
        }
        handleEvents(type);
        disPlay();
    }

    private void setNodesView() {
        setWidth(name, id, price, manaPoint, healthPoint);
        setWidth(attackPower, attackRange, distanceCanMove, maxRangeToInput, desc);
        desc.setPromptText("Optional");
        nameTxt.setFill(Color.WHITE);
        idTxt.setFill(Color.WHITE);
        priceTxt.setFill(Color.WHITE);
        manaPointTxt.setFill(Color.WHITE);
        healthPointTxt.setFill(Color.WHITE);
        attackPowerTxt.setFill(Color.WHITE);
        attackRangeTxt.setFill(Color.WHITE);
        distanceCanMoveTxt.setFill(Color.WHITE);
        maxRangeToInputTxt.setFill(Color.WHITE);
        descTxt.setFill(Color.WHITE);
        attackTypeTxt.setFill(Color.WHITE);
        minionTypeTxt.setFill(Color.WHITE);
        coolDownTxt.setFill(Color.WHITE);
        nameTxt.setFont(FontAppearance.FONT_ERRORS);
        idTxt.setFont(FontAppearance.FONT_ERRORS);
        priceTxt.setFont(FontAppearance.FONT_ERRORS);
        manaPointTxt.setFont(FontAppearance.FONT_ERRORS);
        healthPointTxt.setFont(FontAppearance.FONT_ERRORS);
        attackPowerTxt.setFont(FontAppearance.FONT_ERRORS);
        attackRangeTxt.setFont(FontAppearance.FONT_ERRORS);
        distanceCanMoveTxt.setFont(FontAppearance.FONT_ERRORS);
        maxRangeToInputTxt.setFont(FontAppearance.FONT_ERRORS);
        descTxt.setFont(FontAppearance.FONT_ERRORS);
        attackTypeTxt.setFont(FontAppearance.FONT_ERRORS);
        minionTypeTxt.setFont(FontAppearance.FONT_ERRORS);
        coolDownTxt.setFont(FontAppearance.FONT_ERRORS);

    }

    private void setBackGround() {
        try {
            ImageView imageOfBackGround = new ImageView(new Image(new FileInputStream("backGround0.jpg")));
            imageOfBackGround.fitWidthProperty().bind(customHeroScene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(customHeroScene.heightProperty());
            imageOfBackGround.setOpacity(0.8);
            root.getChildren().add(imageOfBackGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setBuildButton() {
        buildButton = new Rectangle(Main.WIDTH_OF_WINDOW / 7.58, Main.HEIGHT_OF_WINDOW / 11.12);
        try {
            buildButton.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            StackPane stackPane = new StackPane(buildButton, buildTxt);
            stackPane.setLayoutY(Main.HEIGHT_OF_WINDOW * 8.5 / 10);
            stackPane.setLayoutX(Main.WIDTH_OF_WINDOW / 2.3);
            root.getChildren().addAll(stackPane);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        buildTxt.setFont(FontAppearance.FONT_BUTTON);
        buildTxt.setFill(Color.WHITE);
    }


    private void locate() {
        VBox vBox0 = new VBox(nameTxt, name);
        vBox0.setAlignment(Pos.CENTER);
        vBox0.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        VBox vBox1 = new VBox(idTxt, id);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(Main.HEIGHT_OF_WINDOW / 51);
        VBox vBox2 = new VBox(priceTxt, price);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setSpacing(Main.HEIGHT_OF_WINDOW / 51);
        VBox vBox3 = new VBox(manaPointTxt, manaPoint);
        vBox3.setAlignment(Pos.CENTER);
        vBox3.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        VBox vBox4 = new VBox(healthPointTxt, healthPoint);
        vBox4.setAlignment(Pos.CENTER);
        vBox4.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        HBox hBox = new HBox(vBox0, vBox1, vBox2, vBox3, vBox4);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(Main.WIDTH_OF_WINDOW / 15);
        hBox.setLayoutY(Main.HEIGHT_OF_WINDOW / 7);
        hBox.setLayoutX(Main.WIDTH_OF_WINDOW / 15);
        root.getChildren().add(hBox);


        VBox vBox5 = new VBox(attackPowerTxt, attackPower);
        VBox vBox6 = new VBox(attackRangeTxt, attackRange);
        VBox vBox7 = new VBox(distanceCanMoveTxt, distanceCanMove);
        VBox vBox9 = new VBox(maxRangeToInputTxt, maxRangeToInput);
        VBox vBox8 = new VBox(descTxt, desc);
        vBox5.setAlignment(Pos.CENTER);
        vBox6.setAlignment(Pos.CENTER);
        vBox7.setAlignment(Pos.CENTER);
        vBox8.setAlignment(Pos.CENTER);
        vBox9.setAlignment(Pos.CENTER);
        vBox5.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        vBox6.setSpacing(Main.HEIGHT_OF_WINDOW / 51);
        vBox7.setSpacing(Main.HEIGHT_OF_WINDOW / 51);
        vBox8.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        vBox9.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        HBox hBox1 = new HBox(vBox5, vBox6, vBox7, vBox9, vBox8);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(Main.WIDTH_OF_WINDOW / 15);
        hBox1.setLayoutX(Main.WIDTH_OF_WINDOW / 15);
        hBox1.setLayoutY(Main.HEIGHT_OF_WINDOW / 3);
        root.getChildren().add(hBox1);

        attackType.getItems().add("1.ON_SPAWN");
        attackType.getItems().add("2.PASSIVE");
        attackType.getItems().add("3.ON_DEATH");
        attackType.getItems().add("4.ON_ATTACK");
        attackType.getItems().add("5.ON_DEFEND");
        attackType.getItems().add("6.ON_TURN");
        attackType.getItems().add("7.COMBO");
        attackType.getItems().add("8.NONE");
        VBox vBox10 = new VBox(attackTypeTxt, attackType);
        vBox10.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        vBox10.setAlignment(Pos.CENTER);
        minionType.getItems().add("1.Melee");
        minionType.getItems().add("2.Ranged");
        minionType.getItems().add("3.Hybrid");
        VBox vBox11 = new VBox(minionTypeTxt, minionType);
        vBox11.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        vBox11.setAlignment(Pos.CENTER);
        HBox hBox2 = new HBox(vBox10, vBox11);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(Main.WIDTH_OF_WINDOW / 4);
        hBox2.setLayoutX(Main.WIDTH_OF_WINDOW / 3.5);
        hBox2.setLayoutY(Main.HEIGHT_OF_WINDOW / 2);
        root.getChildren().add(hBox2);


    }

    private void locateCoolDown() {
        VBox vBox = new VBox(coolDownTxt, coolDown);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        vBox.setLayoutY(Main.HEIGHT_OF_WINDOW / 1.5);
        vBox.setLayoutX(Main.WIDTH_OF_WINDOW / 2.3);
        root.getChildren().add(vBox);

    }

    private void handleEvents(String type) {
        buildButton.setOnMouseClicked(event -> action(type));
        buildTxt.setOnMouseClicked(event -> action(type));
    }

    private void action(String type) {
        if (!checkValid()) {
            String nameInput = name.getText();
            String idInput = id.getText();
            String attackTypeInput = attackType.getValue().substring(0, 1);
            AttackType attackTypeT = attackTypeSwitch(attackTypeInput);
            String minionTypeInput = minionType.getValue().substring(0, 1);
            MinionType minionTypeT = minionTypeSwitch(minionTypeInput);
            try {
                int priceInput = Integer.parseInt(price.getText());
                int manaPointInput = Integer.parseInt(manaPoint.getText());
                int healthPointInput = Integer.parseInt(healthPoint.getText());
                int attackPowerInput = Integer.parseInt(attackPower.getText());
                int attackRangeInput = Integer.parseInt(attackRange.getText());
                int distanceCanMoveInput = Integer.parseInt(distanceCanMove.getText());
                int maxRangeTInputInput = Integer.parseInt(maxRangeToInput.getText());
                String descInput = desc.getText();
                if (type.equals("hero")) {
                    int coolDownInput = Integer.parseInt(coolDown.getText());
                    GameController.saveMinion(new Hero(nameInput, idInput, priceInput, manaPointInput, healthPointInput, attackPowerInput, minionTypeT, attackRangeInput, distanceCanMoveInput, maxRangeTInputInput, attackTypeT, coolDownInput, descInput));
                } else {
                    GameController.saveMinion(new Minion(nameInput, idInput, priceInput, manaPointInput, healthPointInput, attackPowerInput, minionTypeT, attackRangeInput, distanceCanMoveInput, maxRangeTInputInput, attackTypeT, descInput));
                }
            } catch (Exception e) {
                ErrorOnBattle.display("Please input valid fields!");
            }
            new MainMenu();
        } else {
            ErrorOnBattle.display("Please Fill all fields!");
        }
    }

    private MinionType minionTypeSwitch(String minionTypeInput) {
        MinionType minionType = null;
        switch (minionTypeInput) {
            case "1":
                minionType = MinionType.MELEE;
                break;
            case "2":
                minionType = MinionType.RANGED;
                break;
            case "3":
                minionType = MinionType.HYBRID;
                break;
        }
        return minionType;
    }

    private AttackType attackTypeSwitch(String attackTypeString) {
        AttackType attackType = null;
        switch (attackTypeString) {
            case "1":
                attackType = AttackType.ON_SPAWN;
                break;
            case "2":
                attackType = AttackType.PASSIVE;
                break;
            case "3":
                attackType = AttackType.ON_DEATH;
                break;
            case "4":
                attackType = AttackType.ON_ATTACK;
                break;
            case "5":
                attackType = AttackType.ON_DEFEND;
                break;
            case "6":
                attackType = AttackType.ON_TURN;
                break;
            case "7":
                attackType = AttackType.COMBO;
                break;
            case "8":
                attackType = AttackType.NONE;
                break;
        }
        return attackType;
    }

    private boolean checkValid() {
        return !this.name.getText().equals("")
                && !this.price.getText().equals("")
                && !this.id.getText().equals("")
                && !this.manaPoint.getText().equals("")
                && !this.healthPoint.getText().equals("")
                && !this.attackPower.getText().equals("")
                && !this.attackRange.getText().equals("")
                && distanceCanMove.getText().equals("")
                && maxRangeToInput.getText().equals("")
                ;
    }

    private void disPlay() {
        Main.getWindow().setScene(customHeroScene);
    }

    private void setWidth(TextField attackPower, TextField attackRange, TextField distanceCanMove, TextField maxRangeToInput, TextField desc) {
        attackPower.setMinWidth(Main.WIDTH_OF_WINDOW / 10);
        attackRange.setMinWidth(Main.WIDTH_OF_WINDOW / 10);
        distanceCanMove.setMinWidth(Main.WIDTH_OF_WINDOW / 10);
        maxRangeToInput.setMinWidth(Main.WIDTH_OF_WINDOW / 10);
        desc.setMinWidth(Main.WIDTH_OF_WINDOW / 10);
    }

}
