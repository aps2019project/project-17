package view;

import GameGround.Battle;
import controller.GameController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class GraveYardRequest {

    static boolean checkSyntaxOfShowCards(String command) {
        if (BattleRequest.comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForShowCards = Pattern.compile(StringsRq.SHOW_CARDS);
        Matcher matcher = patternForShowCards.matcher(command);
        if (matcher.matches()) {
            String result = GameController.showCardsOfGraveYard(Battle.getCurrentBattle());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfEnterGraveYard() {
        Request.changeMenuType(MenuType.GRAVE_YARD);
        return true;
    }

}
