package Appearance;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class FontAppearance {
    public static final Font FONT_BUTTON = Font.font("Phosphate", FontWeight.BOLD, Screen.getPrimary().getVisualBounds().getWidth() / 72);
    public static final Font FONT_CURRENT_BUTTON = Font.font("Phosphate", Screen.getPrimary().getVisualBounds().getWidth() / 65.45);
    public static final Font FONT_ERRORS = Font.font("Phosphate", Screen.getPrimary().getVisualBounds().getWidth() / 84.71);
    public static final Font FONT_SHOP_BUTTONS = Font.font("Phosphate", FontWeight.BOLD, Screen.getPrimary().getVisualBounds().getWidth() / 72);
    public static final Font FONT_SHOW_CARD_DATA = Font.font("Phosphate", FontWeight.BOLD, Screen.getPrimary().getVisualBounds().getWidth() / 102.86);
    public static final Font FONT_SHOW_AP = Font.font("Phosphate", FontWeight.BOLD, Screen.getPrimary().getVisualBounds().getWidth() / 72);
    public static final Font FONT_MONEY_VALUE = Font.font("Phosphate", FontWeight.BOLD, Screen.getPrimary().getVisualBounds().getWidth() / 72);
    public static final Font FONT_SEARCH_SHOP = Font.font("Phosphate", Screen.getPrimary().getVisualBounds().getWidth() / 96);
    public static final Font FONT_NOT_FOUND = Font.font("Phosphate", Screen.getPrimary().getVisualBounds().getWidth() / 120);
}
