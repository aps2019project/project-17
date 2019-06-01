package view;

import Data.Account;
import GameGround.Battle;
import controller.GameController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommonRequests {

    static boolean checkSyntaxOfHelpCommand(String command,MenuType menuType) {
        Pattern patternForSave = Pattern.compile(StringsRq.HELP);
        Matcher matcher = patternForSave.matcher(command);
        if (matcher.matches()) {
            switch (menuType) {
                case MAIN_MENU:
                    MainMenuView.MainMenuHelp();
                    break;
                case ACCOUNT_MENU:
                    AccountView.accountHelp();
                    break;
                case COLLECTION_MENU:
                    CollectionView.collectionHelp();
                    break;
                case SHOP_MENU:
                    ShopView.shopHelp();
                    break;
                case BATTLE_MENU:
                    BattleView.battleHelp();
                    break;
            }
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfSaveCommand(String command,MenuType menuType) {
        Pattern patternForSave = Pattern.compile(StringsRq.SAVE);
        Matcher matcher = patternForSave.matcher(command);
        if (matcher.matches()) {
            switch (menuType) {
                case ACCOUNT_MENU:
                    String result = GameController.accountSave(Account.getLoginUser());
                    System.out.println(result);
                    break;
                case COLLECTION_MENU:
                    result = GameController.collectionSave(Account.getLoginUser().getCollection());
                    System.out.println(result);
                    break;
            }

        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfShowCommand(String command,MenuType menuType) {
        Pattern patternForShow = Pattern.compile(StringsRq.SHOW);
        Matcher matcher = patternForShow.matcher(command);
        if (menuType.equals(MenuType.COLLECTION_MENU)) {
            if (matcher.matches()) {
                CollectionView.showUserCollection(Account.getLoginUser());
            } else {
                Request.changeErrorType();
                return false;
            }
        } else if (menuType.equals(MenuType.SHOP_MENU)) {
            if (matcher.matches()) {
                ShopView.showAllProducts(Account.getLoginUser());
            } else {
                Request.changeErrorType();
                return false;
            }
        }
        return true;
    }

    static boolean checkSyntaxOfSearchCommand(String command,MenuType menuType) {
        Pattern patternForSearchCollection = Pattern.compile(StringsRq.SEARCH + " (?<name>[\\w+ ]+)");
        Matcher matcher = patternForSearchCollection.matcher(command);
        if (menuType.equals(MenuType.COLLECTION_MENU)) {
            if (matcher.matches()) {
                String name = matcher.group("name");
                String ID = GameController.search(name, Account.getLoginUser().getCollection());
                System.out.println(ID);
            } else {
                Request.changeErrorType();
                return false;
            }
        } else if (menuType.equals(MenuType.SHOP_MENU)) {
            if (matcher.matches()) {
                String name = matcher.group("name");
                String ID = GameController.searchInShop(name, Account.getLoginUser().getShop());
                System.out.println(ID);
            } else {
                Request.changeErrorType();
                return false;
            }
        }
        return true;
    }


    static boolean checkSyntaxOfShowInfo(String command,MenuType menuType) {
        if (BattleRequest.comeOutOfTheGame()) {
            System.out.println(Battle.getSituationOfGame());
            menuType = MenuType.MAIN_MENU;
            MainMenuView.showMainMenu();
            return true;
        }
        if (menuType.equals(MenuType.BATTLE_MENU)) {
            Pattern patternForShowInfoInBattleMenu = Pattern.compile(StringsRq.SHOW_INFO);
            Matcher matcher = patternForShowInfoInBattleMenu.matcher(command);
            if (matcher.matches()) {
                String result = BattleView.showInfo(Battle.getCurrentBattle());
                System.out.println(result);
            } else {
                Request.changeErrorType();
                return false;
            }
        } else if (menuType.equals(MenuType.GRAVE_YARD)) {
            Pattern patternForShowInfoInBGraveYardMenu = Pattern.compile(StringsRq.SHOW_INFO + " (?<cardId>[\\w+ ]+)");
            Matcher matcher = patternForShowInfoInBGraveYardMenu.matcher(command);
            if (matcher.matches()) {
                String cardId = matcher.group("cardId");
                String result = GameController.showCardInfoFromGraveYard(cardId, Battle.getCurrentBattle());
                System.out.println(result);
                return true;
            } else {
                Request.changeErrorType();
                return false;
            }
        }
        return true;
    }

    static boolean checkSyntaxOfExitCommand(String command,MenuType menuType) {
        Pattern patternForExit = Pattern.compile(StringsRq.EXIT);
        Matcher matcher = patternForExit.matcher(command);
        if (matcher.matches()) {
            if (menuType == MenuType.GRAVE_YARD) {
                Request.changeMenuType(MenuType.BATTLE_MENU);
            } else {
                Request.changeMenuType(MenuType.MAIN_MENU);
                MainMenuView.showMainMenu();
            }
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

}
