import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import Appearance.MinionAppearance;
import Data.Account;
import controller.GameController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {

    static final double WIDTH_OF_WINDOW = Screen.getPrimary().getVisualBounds().getWidth();
    static final double HEIGHT_OF_WINDOW = Screen.getPrimary().getVisualBounds().getHeight();

    private static VBox root = new VBox();
    private static Stage window;
    private static TextField enterUserName = new TextField();
    private static PasswordField enterPassWord = new PasswordField();
    private static ImageView imageOfBackGround;
    private static ImageView help;
    private static Text loginMenu = new Text("login");
    private static Text signUpMenu = new Text("sign up");
    private static Text leaderBoardMenu = new Text("leader board");
    private static Text currentButton = new Text("login");
    private static Text invalidUserName = new Text("invalid username");
    private static Text invalidPassWord = new Text("password is wrong");
    private static Text currentChoice;
    private static Scene sceneFirstMenu = new Scene(root, WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);
    private static Rectangle helpPoPUp = new Rectangle(WIDTH_OF_WINDOW / 8, HEIGHT_OF_WINDOW / 6, Color.rgb(255, 255, 255, 0.5));
    private static Rectangle backGroundFill = new Rectangle(WIDTH_OF_WINDOW / 2.5, HEIGHT_OF_WINDOW / 2.25);
    private static Rectangle outBox = new Rectangle(WIDTH_OF_WINDOW / 7.2, HEIGHT_OF_WINDOW / 16.68);


    static {
        try {
            Image helpIcon = new Image(new FileInputStream("help_icon_main_menu.png"));
            help = new ImageView(helpIcon);
            help.setFitHeight(50);
            help.setFitWidth(50);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        backGroundFill.setFill(ColorAppearance.BACKGROUND_FILL_COLOR);
        backGroundFill.setOpacity(0.45);
        outBox.setOpacity(0.15);
        outBox.setFill(Color.DARKRED);
        enterUserName.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() / 4);
        enterPassWord.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() / 4);
        enterUserName.setPromptText("username");
        enterPassWord.setPromptText("password");
        currentChoice = loginMenu;
    }

    static {
        try {
            Image image = new Image(new FileInputStream("primary_background.jpg"));
            imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(sceneFirstMenu.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(sceneFirstMenu.heightProperty());
            root.getChildren().add(imageOfBackGround);
            root.getChildren().addAll(backGroundFill, leaderBoardMenu, signUpMenu, loginMenu, enterUserName, invalidUserName, enterPassWord, invalidPassWord, outBox, currentButton, help);
            root.setAlignment(Pos.BASELINE_CENTER);
            invalidPassWord.setVisible(false);
            invalidUserName.setVisible(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        VBox.setMargin(imageOfBackGround, new Insets(0, 0, 0, 0));
        VBox.setMargin(backGroundFill, new Insets(-HEIGHT_OF_WINDOW / 2.7, 0, -HEIGHT_OF_WINDOW / 2.7, HEIGHT_OF_WINDOW / 1.2));
        VBox.setMargin(leaderBoardMenu, new Insets(-HEIGHT_OF_WINDOW / 4.63, 0, -HEIGHT_OF_WINDOW / 4.63, WIDTH_OF_WINDOW / 1.32));
        VBox.setMargin(signUpMenu, new Insets(-HEIGHT_OF_WINDOW / 64.15, 0, -HEIGHT_OF_WINDOW / 64.15, WIDTH_OF_WINDOW / 2.06));
        VBox.setMargin(loginMenu, new Insets(-HEIGHT_OF_WINDOW / 64.15, 0, -HEIGHT_OF_WINDOW / 64.15, WIDTH_OF_WINDOW / 5.24));
        VBox.setMargin(enterUserName, new Insets(HEIGHT_OF_WINDOW / 16.68, 0, HEIGHT_OF_WINDOW / 16.68, WIDTH_OF_WINDOW / 2.06));
        VBox.setMargin(invalidUserName, new Insets(HEIGHT_OF_WINDOW / 417, 0, HEIGHT_OF_WINDOW / 417, WIDTH_OF_WINDOW / 2.06));
        VBox.setMargin(enterPassWord, new Insets(-HEIGHT_OF_WINDOW / 834, 0, -HEIGHT_OF_WINDOW / 834, WIDTH_OF_WINDOW / 2.06));
        VBox.setMargin(invalidPassWord, new Insets(HEIGHT_OF_WINDOW / 166.8, 0, HEIGHT_OF_WINDOW / 166.8, WIDTH_OF_WINDOW / 2.06));
        VBox.setMargin(outBox, new Insets(HEIGHT_OF_WINDOW / 41.7, 0, HEIGHT_OF_WINDOW / 41.7, WIDTH_OF_WINDOW / 2.06));
        VBox.setMargin(currentButton, new Insets(-HEIGHT_OF_WINDOW / 37.9, 0, -HEIGHT_OF_WINDOW / 37.9, WIDTH_OF_WINDOW / 2.06));
        VBox.setMargin(help, new Insets(HEIGHT_OF_WINDOW / 10, 0, HEIGHT_OF_WINDOW / 10, 8 * WIDTH_OF_WINDOW / 9));
        VBox.setMargin(helpPoPUp, new Insets(-HEIGHT_OF_WINDOW / 8, 0, -HEIGHT_OF_WINDOW / 8, 4 * WIDTH_OF_WINDOW / 5));
    }

    static {
        loginMenu.setFont(FontAppearance.FONT_BUTTON);
        signUpMenu.setFont(FontAppearance.FONT_BUTTON);
        leaderBoardMenu.setFont(FontAppearance.FONT_BUTTON);
        loginMenu.setFill(ColorAppearance.CURRENT_MENU_BUTTON);
        signUpMenu.setFill(Color.WHITE);
        leaderBoardMenu.setFill(Color.WHITE);
        currentButton.setFont(FontAppearance.FONT_CURRENT_BUTTON);
        currentButton.setFill(Color.WHITE);
        invalidPassWord.setFont(FontAppearance.FONT_ERRORS);
        invalidUserName.setFont(FontAppearance.FONT_ERRORS);
        invalidPassWord.setFill(Color.RED);
        invalidUserName.setFill(Color.RED);
        helpPoPUp.setArcWidth(20);
        helpPoPUp.setArcHeight(20);
        helpPoPUp.setStroke(Color.WHITE);
        Image img;
        try {
            img = new Image(new FileInputStream("AccountHelp.png"));
            helpPoPUp.setFill(new ImagePattern(img));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            sceneFirstMenu.setCursor(new ImageCursor(new Image(new FileInputStream("mouse_icon.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        signUpMenu.setOpacity(0.75);
        leaderBoardMenu.setOpacity(0.75);
    }

    static {
        GameController.getAccounts().add(new Account("a", "1"));
    }


    public static void main(String[] args) {
//        GameController.main();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setScene(sceneFirstMenu);
        window.show();
        handleEvents();
    }

    private static void handleEvents() {
        handleMouse();
        handleClick();
    }

    private static void handleMouse() {
        currentButton.setOnMouseEntered(e -> outBox.setOpacity(0.55));
        loginMenu.setOnMouseEntered(e -> {
            if (currentChoice != loginMenu)
                loginMenu.setOpacity(1);
        });
        signUpMenu.setOnMouseEntered(e -> {
            if (currentChoice != signUpMenu)
                signUpMenu.setOpacity(1);
        });
        leaderBoardMenu.setOnMouseEntered(e -> {
            if (currentButton != leaderBoardMenu)
                leaderBoardMenu.setOpacity(1);
        });
        loginMenu.setOnMouseExited(e -> {
            if (currentChoice != loginMenu)
                loginMenu.setOpacity(0.75);
        });
        signUpMenu.setOnMouseExited(e -> {
            if (currentChoice != signUpMenu)
                signUpMenu.setOpacity(0.75);
        });
        leaderBoardMenu.setOnMouseExited(e -> {
            if (currentChoice != leaderBoardMenu)
                leaderBoardMenu.setOpacity(0.75);
        });
        outBox.setOnMouseEntered(e -> outBox.setOpacity(0.55));
        outBox.setOnMouseExited(e -> outBox.setOpacity(0.15));
        outBox.setOnMouseClicked(e -> setActionButton());
        enterPassWord.setOnAction(e -> setActionButton());

        help.setOnMouseEntered(event -> root.getChildren().addAll(helpPoPUp));
        help.setOnMouseExited(event -> root.getChildren().removeAll(helpPoPUp));
    }

    private static void handleClick() {
        loginMenu.setOnMouseClicked(e -> loginMenuOnMouseClicked());

        signUpMenu.setOnMouseClicked(e -> signUpMenuOnMouseClicked());

        leaderBoardMenu.setOnMouseClicked(e -> {
            currentChoice.setOpacity(0.75);
            currentChoice.setFill(Color.WHITE);
            leaderBoardMenu.setFill(ColorAppearance.CURRENT_MENU_BUTTON);
            leaderBoardMenu.setOpacity(1);
            emptyFieldsErrors();
            currentChoice = leaderBoardMenu;
            leaderBoardAction();
        });

        enterUserName.setOnMouseClicked(e -> emptyErrors());
        enterPassWord.setOnMouseClicked(e -> emptyErrors());
        enterUserName.setOnKeyPressed(e -> emptyErrors());
        enterPassWord.setOnKeyPressed(e -> emptyErrors());
        currentButton.setOnMouseClicked(e -> setActionButton());
    }

    private static void signUpMenuOnMouseClicked() {
        currentChoice.setFill(Color.WHITE);
        currentChoice.setOpacity(0.75);
        signUpMenu.setFill(ColorAppearance.CURRENT_MENU_BUTTON);
        currentButton.setText("sign up");
        signUpMenu.setOpacity(1);
        emptyFieldsErrors();
        currentChoice = signUpMenu;
    }

    private static void loginMenuOnMouseClicked() {
        currentChoice.setFill(Color.WHITE);
        currentChoice.setOpacity(0.75);
        loginMenu.setFill(ColorAppearance.CURRENT_MENU_BUTTON);
        loginMenu.setOpacity(1);
        currentButton.setText("login");
        emptyFieldsErrors();
        currentChoice = loginMenu;
    }

    static void emptyFields() {
        enterUserName.setText("");
        enterPassWord.setText("");
    }

    private static void emptyFieldsErrors() {
        emptyFields();
        emptyErrors();
    }

    private static void emptyErrors() {
        invalidPassWord.setVisible(false);
        invalidUserName.setVisible(false);
    }

    private static void setActionButton() {
        if (currentButton.getText().equals("login")) {
            if (enterUserName.getText().equals("") || enterPassWord.getText().equals(""))
                return;
            loginLogic();
        } else if (currentButton.getText().equals("sign up")) {
            if (enterUserName.getText().equals("") || enterPassWord.getText().equals(""))
                return;
            signUpLogic();
        }
    }

    private static void loginLogic() {
        String username = enterUserName.getText();
        String passWord = enterPassWord.getText();

        String result = Account.login(username, passWord);
        System.out.println(result);
        if (result.contains("login successfully done")) {
            new MainMenu();
            return;
        }
        if (result.contains("your password is wrong")) {
            invalidPassWord.setText("password is wrong!");
            invalidPassWord.setFill(Color.RED);
            invalidPassWord.setVisible(true);
            return;
        }
        invalidUserName.setVisible(true);
    }

    private static void signUpLogic() {
        String username = enterUserName.getText();
        String passWord = enterPassWord.getText();

        String result = Account.addUser(username, passWord);
        System.out.println(result);
        if (result.contains("Account Successfully created")) {
            invalidPassWord.setText("account successfully created");
            invalidPassWord.setFill(Color.GREEN);
            invalidPassWord.setVisible(true);
            loginMenuOnMouseClicked();
            return;
        }
        invalidPassWord.setVisible(false);
        invalidUserName.setVisible(true);
    }

    private static void leaderBoardAction() {
        new LeaderBoardWindow();
    }

    static Stage getWindow() {
        return window;
    }

    static void backToMainMenu() {
        window.setScene(sceneFirstMenu);
    }
}
