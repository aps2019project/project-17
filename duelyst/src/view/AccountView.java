package view;

import Data.Account;
import controller.GameController;

import java.util.Arrays;
import java.util.Comparator;

public class AccountView extends View {

    public static void showLeaderBoard() {
        GameController.getAccounts().sort(Comparator.reverseOrder());
        for (int i = 0; i < GameController.getAccounts().size(); i++) {
            Account account = GameController.getAccounts().get(i);
            System.out.printf("%d - UserNAme: %s - Wins: %d\n", i+1, account.getUserName(), account.getNumbOfWins());
        }
    }

    public static void accountHelp(){
        System.out.println("create account with this command  ->    \"create account [user name]\"");
        System.out.println("login with this command  ->      \"login [user name]\"");
        System.out.println("show leader bord with this command  ->    \"show leaderboard\"");
        System.out.println("save your account with this command  -> \"accountSave\"");
        System.out.println("logout with this command  ->    \"logout\"");
    }
}
