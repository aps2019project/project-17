package Appearance;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FontAppearance {
    private static InputStream inputStream;

    static {
        try {
            inputStream = new FileInputStream("Phosphate.ttf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static final Font FONT_BUTTON = Font.loadFont(inputStream,  Screen.getPrimary().getVisualBounds().getWidth() / 72);
    public static final Font FONT_CURRENT_BUTTON = Font.font("Phosphate", Screen.getPrimary().getVisualBounds().getWidth() / 65.45);
    public static final Font FONT_ERRORS = Font.font("Phosphate", Screen.getPrimary().getVisualBounds().getWidth() / 84.71);
    public static final Font FONT_SHOP_BUTTONS = Font.font("Phosphate", FontWeight.BOLD, Screen.getPrimary().getVisualBounds().getWidth() / 72);
    public static final Font FONT_SHOW_CARD_DATA = Font.font("Phosphate", FontWeight.BOLD, Screen.getPrimary().getVisualBounds().getWidth() / 102.86);
    public static final Font FONT_SHOW_AP = Font.font("Phosphate", FontWeight.BOLD, Screen.getPrimary().getVisualBounds().getWidth() / 72);
    public static final Font FONT_MONEY_VALUE = Font.font("Phosphate", FontWeight.BOLD, Screen.getPrimary().getVisualBounds().getWidth() / 72);
    public static final Font FONT_SEARCH_SHOP = Font.font("Phosphate", Screen.getPrimary().getVisualBounds().getWidth() / 96);
    public static final Font FONT_NOT_FOUND = Font.font("Phosphate", Screen.getPrimary().getVisualBounds().getWidth() / 120);
    public static final Font FONT_CREATE_DECK = Font.font("Phosphate", Screen.getPrimary().getVisualBounds().getWidth() / 74);
    public static final Font FONT_INFORMATION_CARDS_BATTLE = Font.font("Phosphate", 15);
    public static final Font FONT_INFORMATION_ITEM = Font.font("Phosphate", 10);
    public static final Font FONT_SHOWING_TURN = Font.font("Phosphate", 30);
    public static final Font FONT_ENDING_GAME = Font.font("Phosphate", 65);
}
