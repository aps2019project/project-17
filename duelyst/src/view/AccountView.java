package view;

import Data.Account;

import java.util.Arrays;
import java.util.Comparator;

public class AccountView extends View {

    public static void showLeaderBoard() {
        Account.getAccounts().sort(new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                return o2.compareTo(o1);
            }
        });
        for (int i = 0; i < Account.getAccounts().size(); i++) {
            Account account = Account.getAccounts().get(i);
            System.out.printf("%d - UserNAme: %s - Wins: %d", i,account.getUserName(), account.getNumbOfWins());
        }
    }

    public static void AccountView() {
        /**
         * list of account possible orders
         */
        System.out.println("create account [user name]");
        System.out.println("login [user name]");
        System.out.println("show leaderboard");
        System.out.println("save");
        System.out.println("logout");
        System.out.println("help");
    }

    public static void help(){
        System.out.println("create account with this command ->    \"create account [user name]\"");
        System.out.println("login with this command ->      \"login [user name]\"");
        System.out.println("show leader bord with this command ->    \"show leaderboard\"");
        System.out.println("save your account with this command -> \"save\"");
        System.out.println("logout with this command -> \"logout\"");
    }
}
