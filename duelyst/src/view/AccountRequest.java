package view;

import controller.GameController;

import java.io.DataInputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AccountRequest {

    static boolean checkSyntaxOfCreateAccountCommand(String command, Scanner scanner) {
        Pattern patternForCreateAccount = Pattern.compile(StringsRq.CREATE_ACCOUNT + " (?<userName>\\w+)" + " " + "(?<password>\\w+)");
        Matcher matcher = patternForCreateAccount.matcher(command);
        if (matcher.matches()) {
            String userName = matcher.group("userName");
            String passWord = matcher.group("password");
            if (!GameController.checkForValidUserName(userName)) {
//                System.out.println(GameController.createAccount(userName, passWord));
                return true;
            } else {
                System.out.println("UserName Already Exist! Please Try again with another UserName.");
                return true;
            }
        }
        Request.changeErrorType();
        return false;
    }

    static boolean checkSyntaxOfLoginCommand(String command, Scanner scanner) {
        Pattern patternForLogIn = Pattern.compile(StringsRq.LOGIN + " (?<userName>\\w+)");
        Matcher matcher = patternForLogIn.matcher(command);
        if (matcher.matches()) {
            String userName = matcher.group("userName");
            if (GameController.checkForValidUserName(userName)) {
                System.out.println("password: ");
                String passWord = scanner.nextLine();
                String result = GameController.login(userName, passWord);
                System.out.println(result);
                if (!result.equals("your password is wrong!")) {
                    Request.changeMenuType(MenuType.MAIN_MENU);
                    MainMenuView.showMainMenu();
                }
            } else {
                System.out.println("cant find account with this user name");
            }
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfShowLeaderBoardCommand(String command) {
        Pattern patternForShowLeaderBoard = Pattern.compile(StringsRq.SHOW_LEADER_BOARD);
        Matcher matcher = patternForShowLeaderBoard.matcher(command);
        if (matcher.matches()) {
            AccountView.showLeaderBoard();
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

     static boolean checkSyntaxOfLogOutCommand(String command) {
        if (command.toLowerCase().matches(StringsRq.LOGOUT)) {
            String result = GameController.logout();
            System.out.println(result);
            Request.changeMenuType(MenuType.ACCOUNT_MENU);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }
}
