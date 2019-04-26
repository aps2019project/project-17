package view;

import Data.Account;

import java.util.Arrays;
import java.util.Comparator;

public class AccountView extends View {

    /**
     * sorts accounts in decreasing order
     */
    public static void showLeaderBoard() {
        Account.getAccounts().sort(new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                return o2.compareTo(o1);
            }
        });
        int counter = 1;
        for (Account account : Account.getAccounts()) {
            System.out.println(counter + " - UserName : " + account.getUserName() + " - Wins : " + account.getNumbOfWins());
            counter++;
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
}
