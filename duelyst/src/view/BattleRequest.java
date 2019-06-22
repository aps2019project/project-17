package view;

import GameGround.Battle;
import controller.GameController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class BattleRequest {

    static boolean comeOutOfTheGame() {
        return Battle.getCurrentBattle() == null;
    }

    static boolean checkSyntaxForGameInfo(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForGameInfo = Pattern.compile(StringsRq.GAME_INFO);
        Matcher matcher = patternForGameInfo.matcher(command);
        if (matcher.matches()) {
            BattleView.showGameInfo(Battle.getCurrentBattle());
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxForShowMyMinions(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForShowMyMinions = Pattern.compile(StringsRq.SHOW_MY_MINIONS);
        Matcher matcher = patternForShowMyMinions.matcher(command);
        if (matcher.matches()) {
            BattleView.showMyMinions(Battle.getCurrentBattle());
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfShowOpponentMinions(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForShowOpponentMinions = Pattern.compile(StringsRq.SHOW_OPPONENT_MINIONS);
        Matcher matcher = patternForShowOpponentMinions.matcher(command);
        if (matcher.matches()) {
            BattleView.showOpponentMinions(Battle.getCurrentBattle());
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfShowCardInfo(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForShowCardInfo = Pattern.compile(StringsRq.SHOW_CARD_INFO + " (?<cardId>[\\w+ ]+)");
        Matcher matcher = patternForShowCardInfo.matcher(command);
        if (matcher.matches()) {
            String cardId = matcher.group("cardId");
            BattleView.showCardInfo(Battle.getCurrentBattle(), cardId);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxForSelect(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForSelect = Pattern.compile(StringsRq.SELECT + " (?<cardId>[\\w+ ]+)");
        Matcher matcher = patternForSelect.matcher(command);
        if (matcher.matches()) {
            String cardId = matcher.group("cardId");
            String result = GameController.selectCardOrItem(cardId, Battle.getCurrentBattle());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfMoveTO(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForMoveTO = Pattern.compile(StringsRq.MOVE_TO + " \\((?<x>\\d),(?<y>\\d)\\)");
        Matcher matcher = patternForMoveTO.matcher(command);
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            String result = GameController.movingCard(x, y, Battle.getCurrentBattle());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfAttack(String command, Scanner scanner) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForAttack = Pattern.compile(StringsRq.ATTACK + " " + "(?<cardId>[\\w]+)");
        Matcher matcher = patternForAttack.matcher(command);
        if (command.split(" ")[1].equals("combo")) {
            checkSyntaxOfComboAttack(command, scanner);
            return true;
        }
        if (matcher.matches()) {
            String result = GameController.attack(command.split(" ")[1], Battle.getCurrentBattle());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfComboAttack(String command, Scanner scanner) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForAttackCombo = Pattern.compile(StringsRq.ATTACK_COMBO);
        Matcher matcher = patternForAttackCombo.matcher(command);
        if (matcher.matches()) {
            System.out.println("Enter opponent cardId:");
            String opponentCardId = scanner.nextLine();
            System.out.println("Enter your cardIds:");
            String ids = scanner.nextLine();
            String[] allIds = ids.split(" ");
            ArrayList<String> toPass = new ArrayList<>(Arrays.asList(allIds));
            String result = GameController.attackCombo(opponentCardId, Battle.getCurrentBattle(), toPass.toArray(new String[0]));
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfUseSpecialPower(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForUserSpecialPower = Pattern.compile(StringsRq.USE_SPECIAL_POWER + " \\((?<x>\\d),(?<y>\\d)\\)");
        Matcher matcher = patternForUserSpecialPower.matcher(command);
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            System.out.println(GameController.useSpecialPower(x, y, Battle.getCurrentBattle()));
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfShowHand(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForShowHand = Pattern.compile(StringsRq.SHOW_HAND);
        Matcher matcher = patternForShowHand.matcher(command);
        if (matcher.matches()) {
            BattleView.showHand(Battle.getCurrentBattle());
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfInsert(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForInsert = Pattern.compile(StringsRq.INSERT + " (?<cardName>[\\w+ ]+) in \\((?<x>\\d),(?<y>\\d)\\)");
        Matcher matcher = patternForInsert.matcher(command);
        if (matcher.matches()) {
            String cardName = matcher.group("cardName");
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            String result = GameController.insertCard(cardName, x, y, Battle.getCurrentBattle());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfEndTurn(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForEndTurn = Pattern.compile(StringsRq.END_TURN);
        Matcher matcher = patternForEndTurn.matcher(command);
        if (matcher.matches()) {
            GameController.endTurn(Battle.getCurrentBattle());
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfShowCollectibles(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForShowCollectibles = Pattern.compile(StringsRq.SHOW_COLLECTIBLES);
        Matcher matcher = patternForShowCollectibles.matcher(command);
        if (matcher.matches()) {
            BattleView.showCollectAbles();
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfUse(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForUse = Pattern.compile(StringsRq.USE + " \\(\\d,\\d\\)");
        Matcher matcher = patternForUse.matcher(command);
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            String result = GameController.useItem(x, y, Battle.getCurrentBattle());
            System.out.println(result);
            return true;
        } else {
            Request.changeErrorType();
            return false;
        }
    }

    static boolean checkSyntaxForShowNextCard(String command) {
        if (comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            Request.changeMenuType(MenuType.MAIN_MENU);
            MainMenuView.showMainMenu();
            return true;
        }
        Pattern patternForShowNextCard = Pattern.compile(StringsRq.SHOW_NEXT_CARD);
        Matcher matcher = patternForShowNextCard.matcher(command);
        if (matcher.matches()) {
            BattleView.showNextCard();
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfEndGame(String command) {
        Pattern patternForEndGame = Pattern.compile(StringsRq.END_GAME);
        Matcher matcher = patternForEndGame.matcher(command);
        if (matcher.matches()) {
            Battle.getCurrentBattle().endingGame();
            System.out.println(Battle.getSituationOfGame());
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }


}
