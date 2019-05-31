package view;

import Data.Account;
import controller.GameController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ShopRequest {


    static boolean checkSyntaxForShowForShopMenu(String command) {
        Pattern patternForShowForShopMenu = Pattern.compile(StringsRq.SHOW_FOR_SHOP_MENU + "");
        Matcher matcher = patternForShowForShopMenu.matcher(command);
        if (matcher.matches()) {
            CollectionView.showUserCollection(Account.getLoginUser());
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfSearchCollection(String command) {
        Pattern patternForSearchCollection = Pattern.compile(StringsRq.SEARCH_COLLECTION + " " + "(?<name>[\\w+ ]+)");
        Matcher matcher = patternForSearchCollection.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String ID = GameController.search(name, Account.getLoginUser().getCollection());
            System.out.println(ID);
            return true;
        } else {
            Request.changeErrorType();
            return false;
        }
    }

    static boolean checkSyntaxOfBuyCommand(String command) {
        Pattern patternForBuy = Pattern.compile(StringsRq.BUY + " (?<name>[\\w+ ]+)");
        Matcher matcher = patternForBuy.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.buy(name, Account.getLoginUser().getShop());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfSellCommand(String command) {
        Pattern patternForSell = Pattern.compile(StringsRq.SELL + " (?<name>[\\w+ ]+)");
        Matcher matcher = patternForSell.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.sell(name, Account.getLoginUser().getShop());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }
}
