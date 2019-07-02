import Appearance.FontAppearance;
import Effects.CellEffects.FireCell;
import Effects.CellEffects.HolyCell;
import Effects.CellEffects.Poison;
import Effects.MinionEffects.*;
import Effects.Player.ChangeMana;
import Effects.SpecialSituationBuff;
import Effects.enums.*;
import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
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
import java.io.IOException;

public class CustomBuffWindow {
    private Group root = new Group();
    private Scene customCardScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
    private Rectangle buildButton;
    private Text buildTxt = new Text("Build");
    private Text title = new Text("Buff");
    private ChoiceBox<BuffType> buffType = new ChoiceBox<>();
    private Text id;
    private TextField startTime = new TextField();
    private TextField endTime = new TextField();
    private ChoiceBox<Boolean> isContinues = new ChoiceBox<>();
    private ChoiceBox<TargetRange> targetRange = new ChoiceBox<>();
    private ChoiceBox<TargetType> targetType = new ChoiceBox<>();
    private ChoiceBox<BuffType> antiBuffType = new ChoiceBox<>();
    private TextField changeHealthValue = new TextField();
    private TextField changePowerValue = new TextField();
    private TextField holyState = new TextField();
    private TextField changeMana = new TextField();
    private ChoiceBox<TargetDetail> targetDetail = new ChoiceBox<>();
    private ChoiceBox<SpecialSituation> specialSituation = new ChoiceBox<>();
    private Text idTxt = new Text("id");
    private Text startTimeTxt = new Text("start time");
    private Text endTimeTxt = new Text("end time");
    private Text isContinuesTxt = new Text("is continues");
    private Text targetRangeTxt = new Text("target Range");
    private Text targetTypeTxt = new Text("target type");
    private Text targetDetailTxt = new Text("target detail");
    private Text buffTypeTxt = new Text("buff Type");
    private Text antiBuffTxt = new Text("anti buff");
    private Text changeHealthTxt = new Text("change health value");
    private Text changePowerTxt = new Text("change power value");
    private Text holyTxt = new Text("holy buff state");
    private Text changeManaTxt = new Text("change mana");
    private Text specialSituationTxt = new Text("special situation");
    private StackPane titleStackPane;

    public CustomBuffWindow(String id) {
        VBox vBox = new VBox();
        setBackGround();
        setBuildButton();
        setTitle();
        this.id = new Text(id);
        isContinues.getItems().addAll(Boolean.TRUE, Boolean.FALSE);
        targetRange.getItems().addAll(TargetRange.values());
        targetType.getItems().addAll(TargetType.values());
        buffType.getItems().addAll(BuffType.values());
        targetDetail.getItems().addAll(TargetDetail.values());
        specialSituation.getItems().addAll(SpecialSituation.values());
        setWidth(startTime, endTime, changeHealthValue, changePowerValue, changeMana, holyState);
        setFontStyle(startTimeTxt, endTimeTxt, this.id, idTxt, buffTypeTxt, isContinuesTxt, targetRangeTxt, targetTypeTxt, startTimeTxt, endTimeTxt, antiBuffTxt, changeHealthTxt, changePowerTxt, changeManaTxt, holyTxt, specialSituationTxt, targetDetailTxt);
        vBox.getChildren().addAll(addToHBox(buffTypeTxt, buffType, idTxt, this.id));
        vBox.getChildren().addAll(addToHBox(startTimeTxt, startTime, endTimeTxt, endTime));
        vBox.getChildren().addAll(addToHBox(isContinuesTxt, isContinues, targetRangeTxt, targetRange, targetTypeTxt, targetType, targetDetailTxt, targetDetail));
        vBox.relocate(0, titleStackPane.getHeight() + titleStackPane.getLayoutY() + Main.HEIGHT_OF_WINDOW / 10);
        vBox.setSpacing(Main.HEIGHT_OF_WINDOW / 10);
        buffType.setOnAction(event -> {
            vBox.getChildren().clear();
            vBox.getChildren().addAll(addToHBox(buffTypeTxt, buffType, idTxt, this.id));
            vBox.getChildren().addAll(addToHBox(startTimeTxt, startTime, endTimeTxt, endTime));
            vBox.getChildren().addAll(addToHBox(isContinuesTxt, isContinues, targetRangeTxt, targetRange, targetTypeTxt, targetType));
            vBox.relocate(0, titleStackPane.getHeight() + titleStackPane.getLayoutY() + Main.HEIGHT_OF_WINDOW / 10);
            vBox.setSpacing(Main.HEIGHT_OF_WINDOW / 10);
            if (buffType.getValue().equals(BuffType.ANTI))
                vBox.getChildren().addAll(addToHBox(antiBuffTxt, antiBuffType));
            if (buffType.getValue().equals(BuffType.POWER_BUFF) || buffType.getValue().equals(BuffType.WEAKNESS))
                vBox.getChildren().addAll(addToHBox(changeHealthTxt, changeHealthValue, changePowerTxt, changePowerValue));
            if (buffType.getValue().equals(BuffType.HOLY))
                vBox.getChildren().addAll(addToHBox(holyTxt, holyState));
            if (buffType.getValue().equals(BuffType.CHANGE_MANA))
                vBox.getChildren().addAll(addToHBox(changeManaTxt, changeMana));
            if (buffType.getValue().equals(BuffType.SPECIAL_SITUATION_BUFF))
                vBox.getChildren().addAll(addToHBox(specialSituationTxt, specialSituation));
        });
        buildButton.setOnMouseClicked(event -> {
            switch (buffType.getValue()) {
                case NONE:
                    break;
                case HOLY:
                    try {
                        GameController.saveEffect(new Holy(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue(), Integer.parseInt(holyState.getText())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case WEAKNESS:
                    try {
                        GameController.saveEffect(new ChangeProperties(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue(), Integer.parseInt(changeHealthValue.getText())
                                , Integer.parseInt(changePowerValue.getText()), true));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case POWER_BUFF:
                    try {
                        GameController.saveEffect(new ChangeProperties(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue(), Integer.parseInt(changeHealthValue.getText())
                                , Integer.parseInt(changePowerValue.getText()), false));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case STUN:
                    try {
                        GameController.saveEffect(new Stun(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case DISARM:
                    try {
                        GameController.saveEffect(new Disarm(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CLEAR:
                    try {
                        GameController.saveEffect(new Clear(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case FIRE_CELL:
                    try {
                        GameController.saveEffect(new FireCell(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case POISON:
                    try {
                        GameController.saveEffect(new Poison(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case HOLY_CELL:
                    try {
                        GameController.saveEffect(new HolyCell(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case ANTI:
                    try {
                        GameController.saveEffect(new Anti(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue(), antiBuffType.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHANGE_MANA:
                    try {
                        GameController.saveEffect(new ChangeMana(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue(), Integer.parseInt(changeMana.getText())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case SPECIAL_SITUATION_BUFF:
                    try {
                        GameController.saveEffect(new SpecialSituationBuff(Integer.parseInt(startTime.getText())
                                , Integer.parseInt(endTime.getText()), isContinues.getValue(), targetRange.getValue(),
                                targetType.getValue(), targetDetail.getValue(), specialSituation.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            new MainMenu();
        });
        root.getChildren().add(vBox);
        Main.getWindow().setScene(customCardScene);
    }

    private void setBackGround() {
        try {
            ImageView imageOfBackGround = new ImageView(new Image(new FileInputStream("background0.jpg")));
            imageOfBackGround.fitWidthProperty().bind(customCardScene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(customCardScene.heightProperty());
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

    private void setWidth(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setMinWidth(Main.WIDTH_OF_WINDOW / 10);
            textField.setMaxWidth(Main.WIDTH_OF_WINDOW / 10);
        }
    }

    private HBox addToHBox(Node... nodes) {
        HBox hBox = new HBox();
        for (Node node : nodes) {
            hBox.getChildren().add(node);
        }
        hBox.setSpacing(Main.WIDTH_OF_WINDOW / 30);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMinWidth(Main.WIDTH_OF_WINDOW);
        return hBox;
    }

    private void setFontStyle(Text... texts) {
        for (Text txt : texts) {
            txt.setFont(FontAppearance.FONT_SHOW_CARD_DATA);
            txt.setFill(Color.WHITE);
        }
    }

    private void setTitle() {
        try {
            ImageView imageView = new ImageView((new Image(new FileInputStream("card_glow_line.png"))));
            imageView.setFitWidth(Main.WIDTH_OF_WINDOW / 7.58);
            imageView.setFitHeight(Main.HEIGHT_OF_WINDOW / 11.12);
            title.setFont(FontAppearance.FONT_BUTTON);
            title.setFill(Color.WHITE);
            titleStackPane = new StackPane(imageView, title);
            titleStackPane.relocate(Main.WIDTH_OF_WINDOW / 2.3, Main.HEIGHT_OF_WINDOW / 10);
            root.getChildren().add(titleStackPane);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
