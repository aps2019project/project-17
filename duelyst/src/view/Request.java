package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Data.Account;
import controller.GameController;

public class Request {
    protected Scanner scanner = new Scanner(System.in);
    protected String command;
    protected ErrorType error = null;
    protected MenuType menuType = null;

    private static final String CREATE_ACCOUNT = "create account";
    private static final String LOGIN = "login";
    private static final String SHOW_LEADER_BOARD = "show leaderboard";
    private static final String SAVE = "save";
    private static final String LOGOUT = "logout";
    private static final String HELP = "help";
    private static final String SHOW = "show";
    private static final String SEARCH = "search";
    private static final String CREATE_DECK = "create deck";
    private static final String DELETE_DECK = "delete deck";
    private static final String ADD_TO_DECK = "add";
    private static final String REMOVE_FROM_DECK = "remove";
    private static final String VALIDATE_DECK = "validate deck";
    private static final String SELECT_DECK = "select deck";
    private static final String SHOW_ALL_DECKS = "show all decks";
    private static final String SHOW_DECK = "show deck";
    private static final String SHOW_FOR_SHOP_MENU = "show collection";
    private static final String SEARCH_COLLECTION = "search collection";
    private static final String BUY = "buy";
    private static final String SELL = "sell";


    public void getNewCommand() {
        command = scanner.nextLine().toLowerCase();
    }

    public ErrorType getError() {
        return error;
    }

    public void setError(ErrorType error) {
        this.error = error;
    }

    public boolean isValid() {//todo remember to complete this part
        if (getType() == null)
            return false;
        switch (getType()) {
            case CREATE_ACCOUNT:
                return checkSyntaxOfCreateAccountCommand();
            case LOGIN:
                return checkSyntaxOfLoginCommand();
            case SHOW_LEADER_BOARD:
                return checkSyntaxOfShowLeaderBoardCommand();
            case LOGOUT:
                return checkSyntaxOfLogOutCommand();
            case HELP:
                return checkSyntaxOfHelpCommand();
            case SAVE:
                return checkSyntaxOfSaveCommand();
            case SHOW:
                return checkSyntaxOfShowCommand();
            case SEARCH:
                return checkSyntaxOfSearchCommand();
            case CREATE_DECK:
                return checkSyntaxOfCreateDeck();
            case DELETE_DECK:
                return checkSyntaxOfDeleteDeck();
            case ADD_TO_DECK:
                return checkSyntaxOfAddToDeck();
            case REMOVE_FROM_DECK:
                return checkSyntaxOfRemoveFromDeck();
            case VALIDATE_DECK:
                return checkSyntaxOfValidateDeck();
            case SELECT_DECK:
                return checkSyntaxOfSelectDeck();
            case SHOW_ALL_DECKS:
                return checkSyntaxOfShowAllDecks();
            case SHOW_DECK:
                return checkSyntaxOfShowDeck();
            case SHOW_fOR_SHOP_MENU:
                return checkSyntaxForShowForShopMenu();
        }
        return true;
    }


    public RequestType getType() {
        if (command == null || command.equals("")) {
            return null;
        }
        Pattern patternForCreateAccount = Pattern.compile(CREATE_ACCOUNT + " " + "\\w+");
        Matcher matcherForCreateAccount = patternForCreateAccount.matcher(command);
        Pattern patternForLogIn = Pattern.compile(LOGIN + " " + "\\w+");
        Matcher matcherForLogIn = patternForLogIn.matcher(command);
        Pattern patternForShowLeaderBoard = Pattern.compile(SHOW_LEADER_BOARD);
        Matcher matcherForShowLeaderBoard = patternForShowLeaderBoard.matcher(command);
        Pattern patternForSave = Pattern.compile(SAVE);
        Matcher matcherForSave = patternForSave.matcher(command);
        Pattern patternForLogOut = Pattern.compile(LOGOUT);
        Matcher matcherForLogOut = patternForLogOut.matcher(command);
        Pattern patternForHelp = Pattern.compile(HELP);
        Matcher matcherForHelp = patternForHelp.matcher(command);
        Pattern patternForShow = Pattern.compile(SHOW);
        Matcher matcherForShow = patternForShow.matcher(command);
        Pattern patternForSearch = Pattern.compile(SEARCH + " \\w+");
        Matcher matcherForSearch = patternForSearch.matcher(command);
        Pattern patternForCreateDeck = Pattern.compile(CREATE_DECK + " \\w+");
        Matcher matcherForCreateDeck = patternForCreateDeck.matcher(command);
        Pattern patternForDeleteDeck = Pattern.compile(DELETE_DECK + " \\w+");
        Matcher matcherForDeleteDeck = patternForDeleteDeck.matcher(command);
        Pattern patternForAddToDeck = Pattern.compile(ADD_TO_DECK + " " + "\\w+ to deck \\w+");
        Matcher matcherForAddToDeck = patternForAddToDeck.matcher(command);
        Pattern patternForRemoveFromDeck = Pattern.compile(REMOVE_FROM_DECK + " \\w+ from deck \\w+");
        Matcher matcherForRemoveFromDeck = patternForRemoveFromDeck.matcher(command);
        Pattern patternForValidateDeck = Pattern.compile(VALIDATE_DECK + " " + "\\w+");
        Matcher matcherForValidateDeck = patternForValidateDeck.matcher(command);
        Pattern patternForSelectDeck = Pattern.compile(SELECT_DECK + " " + "\\w+");
        Matcher matcherForSelectDeck = patternForSelectDeck.matcher(command);
        Pattern patternForShowAllDecks = Pattern.compile(SHOW_ALL_DECKS);
        Matcher matcherForShowAllDecks = patternForShowAllDecks.matcher(command);
        Pattern patternForShowDeck = Pattern.compile(SHOW_DECK + " \\w+");
        Matcher matcherForShowDeck = patternForShowDeck.matcher(command);
        Pattern patternForShowForShopMenu = Pattern.compile(SHOW_FOR_SHOP_MENU);
        Matcher matcherForShowForShopMenu = patternForShowForShopMenu.matcher(command);
        Pattern patternForSearchCollection = Pattern.compile(SEARCH_COLLECTION + " " + "\\w+");
        Matcher matcherForSearchCollection = patternForSearchCollection.matcher(command);
        Pattern patternForBuy = Pattern.compile(BUY + " \\w+");
        Matcher matcherForSBuy = patternForBuy.matcher(command);
        Pattern patternForSell = Pattern.compile(SELL + " " + "\\w+");
        Matcher matcherForSSell = patternForSell.matcher(command);
        if (matcherForCreateAccount.matches()) {
            menuType = MenuType.ACCOUNT_MENU;
            return RequestType.CREATE_ACCOUNT;
        } else if (matcherForLogIn.matches()) {
            menuType = MenuType.ACCOUNT_MENU;
            return RequestType.LOGIN;
        } else if (matcherForShowLeaderBoard.matches()) {
            menuType = MenuType.ACCOUNT_MENU;
            return RequestType.SHOW_LEADER_BOARD;
        } else if (matcherForLogOut.matches()) {
            menuType = MenuType.ACCOUNT_MENU;
            return RequestType.LOGOUT;
        } else if (matcherForShow.matches()) {
            return RequestType.SHOW;
        } else if (matcherForSearch.matches()) {
            return RequestType.SEARCH;
        } else if (matcherForCreateDeck.matches()) {
            menuType = MenuType.COLLECTION_MENU;
            return RequestType.CREATE_DECK;
        } else if (matcherForDeleteDeck.matches()) {
            menuType = MenuType.COLLECTION_MENU;
            return RequestType.DELETE_DECK;
        } else if (matcherForAddToDeck.matches()) {
            menuType = MenuType.COLLECTION_MENU;
            return RequestType.ADD_TO_DECK;
        } else if (matcherForRemoveFromDeck.matches()) {
            menuType = MenuType.COLLECTION_MENU;
            return RequestType.REMOVE_FROM_DECK;
        } else if (matcherForValidateDeck.matches()) {
            menuType = MenuType.COLLECTION_MENU;
            return RequestType.VALIDATE_DECK;
        } else if (matcherForSelectDeck.matches()) {
            menuType = MenuType.COLLECTION_MENU;
            return RequestType.SELECT_DECK;
        } else if (matcherForShowAllDecks.matches()) {
            menuType = MenuType.COLLECTION_MENU;
            return RequestType.SHOW_ALL_DECKS;
        } else if (matcherForShowDeck.matches()) {
            menuType = MenuType.COLLECTION_MENU;
            return RequestType.SHOW_DECK;
        } else if (matcherForShowForShopMenu.matches()) {
            menuType = MenuType.SHOP_MENU;
            return RequestType.SHOW_fOR_SHOP_MENU;
        } else if (matcherForSearchCollection.matches()) {
            menuType = MenuType.SHOP_MENU;
            return RequestType.SEARCH_COLLECTION;
        } else if (matcherForSBuy.matches()) {
            menuType = MenuType.SHOP_MENU;
            return RequestType.BUY;
        } else if (matcherForSSell.matches()) {
            menuType = MenuType.SHOP_MENU;
            return RequestType.SELL;
        } else if (matcherForSave.matches()) {
            return RequestType.SAVE;
        } else if (matcherForHelp.matches()) {
            return RequestType.HELP;
        }
        error = ErrorType.INVALID_INPUT;
        return RequestType.EXIT;//todo نمیدونم اینو:(
    }


    public boolean checkSyntaxOfCreateAccountCommand() {
        Pattern patternForCreateAccount = Pattern.compile(CREATE_ACCOUNT + " (?<userName>\\w+)");
        Matcher matcher = patternForCreateAccount.matcher(command);
        if (matcher.matches()) {
            String userName = matcher.group("userName");
            System.out.println("Please enter your password:");
            String passWord = scanner.nextLine();
            System.out.println(GameController.createAccount(userName, passWord));
            return true;
        }
        error = ErrorType.INVALID_INPUT;
        return false;
    }

    public boolean checkSyntaxOfLoginCommand() {
        Pattern patternForLogIn = Pattern.compile(LOGIN + " (?<userName>\\w+)");
        Matcher matcher = patternForLogIn.matcher(command);
        if (matcher.matches()) {
            String userName = matcher.group("userName");
            String passWord = scanner.nextLine();
            String result = GameController.login(userName, passWord);
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowLeaderBoardCommand() {
        Pattern patternForShowLeaderBoard = Pattern.compile(SHOW_LEADER_BOARD);
        Matcher matcher = patternForShowLeaderBoard.matcher(command);
        if (matcher.matches()) {
            AccountView.showLeaderBoard();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfSaveCommand() {//todo must to be specified for different menus
        Pattern patternForSave = Pattern.compile(SAVE);
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
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfLogOutCommand() {
        if (command.toLowerCase().matches("logout")) {
            String result = GameController.logout();
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfHelpCommand() {//todo must to be specified for different menus
        Pattern patternForSave = Pattern.compile(HELP);
        Matcher matcher = patternForSave.matcher(command);
        if (matcher.matches()) {
            switch (menuType) {
                case MAIN_MENU:
                    //todo main menu help
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
                    //todo needs battle help
                    break;
            }
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    /**
     * notice that this show collection is for collection menu
     *
     * @return
     */
    public boolean checkSyntaxOfShowCommand() {
        Pattern patternForShow = Pattern.compile(SHOW);
        Matcher matcher = patternForShow.matcher(command);
        if (menuType.equals(MenuType.COLLECTION_MENU)) {
            if (matcher.matches()) {
                CollectionView.showUserCollection(Account.getLoginUser());
            } else {
                error = ErrorType.INVALID_INPUT;
                return false;
            }
        } else if (menuType.equals(MenuType.SHOP_MENU)) {//todo complete this part

        }

        return true;
    }

    public boolean checkSyntaxOfSearchCommand() {
        Pattern patternForSearchCollection = Pattern.compile(SEARCH + " (?<name>\\w+)");
        Matcher matcher = patternForSearchCollection.matcher(command);
        if (menuType.equals(MenuType.COLLECTION_MENU)) {
            if (matcher.matches()) {
                String name = matcher.group("name");
                String ID = GameController.search(name, Account.getLoginUser().getCollection());
                System.out.println(ID);
            } else {
                error = ErrorType.INVALID_INPUT;
                return false;
            }
        } else if (menuType.equals(MenuType.SHOP_MENU)) {//todo complete this part

        }
        return true;
    }

    public boolean checkSyntaxOfCreateDeck() {
        Pattern patternForSCreateDeck = Pattern.compile(CREATE_DECK + " (?<name>\\w+)");
        Matcher matcher = patternForSCreateDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.createDeck(name, Account.getLoginUser().getCollection());
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfDeleteDeck() {
        Pattern patternForDeleteDeck = Pattern.compile(DELETE_DECK + " (?<name>\\w+)");
        Matcher matcher = patternForDeleteDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.deleteDeck(name, Account.getLoginUser().getCollection());

            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfAddToDeck() {
        Pattern patternForAddToDeck = Pattern.compile(ADD_TO_DECK + " (?<cardId>\\w+) to deck (?<deckName>\\w+)");
        Matcher matcher = patternForAddToDeck.matcher(command);
        if (matcher.matches()) {
            String cardId = matcher.group("cardId");
            String deckName = matcher.group("deckName");
            String result = GameController.addToDeck(cardId, deckName, Account.getLoginUser().getCollection());
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfRemoveFromDeck() {
        Pattern patternForRemoveFromDeck = Pattern.compile(REMOVE_FROM_DECK + " (?<cardId>\\w+) from deck (?<deckName>\\w+)");
        Matcher matcher = patternForRemoveFromDeck.matcher(command);
        if (matcher.matches()) {
            String cardId = matcher.group("cardId");
            String deckName = matcher.group("deckName");
            String result = GameController.removeFromDeck(cardId, deckName, Account.getLoginUser().getCollection());
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfValidateDeck() {
        Pattern patternForValidateDeck = Pattern.compile(VALIDATE_DECK + " (?<name>\\w+)");
        Matcher matcher = patternForValidateDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.isDeckValidate(name, Account.getLoginUser().getCollection());
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfSelectDeck() {
        Pattern patternForSelectDeck = Pattern.compile(SELECT_DECK + " (?<name>\\w+)");
        Matcher matcher = patternForSelectDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.setMainDeck(name, Account.getLoginUser());
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowAllDecks() {
        Pattern patternForShowAllDecks = Pattern.compile(SHOW_ALL_DECKS);
        Matcher matcher = patternForShowAllDecks.matcher(command);
        if (matcher.matches()) {
            CollectionView.showAllDecks(Account.getLoginUser());
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowDeck() {
        Pattern patternForShowDeck = Pattern.compile(SELECT_DECK + " (?<name>\\w+)");
        Matcher matcher = patternForShowDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            CollectionView.showDeck(name, Account.getLoginUser().getCollection());
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxForShowForShopMenu(){
        Pattern patternForShowForShopMenu = Pattern.compile(SHOW_FOR_SHOP_MENU+"");
        Matcher matcher = patternForShowForShopMenu.matcher(command);
        if(matcher.matches()){
            CollectionView.showUserCollection(Account.getLoginUser());
        }else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

}
