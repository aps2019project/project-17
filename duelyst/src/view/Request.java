package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.GameController;

public class Request {
    protected Scanner scanner = new Scanner(System.in);
    protected String command;
    protected ErrorType error = null;
    protected MenuType menuType=null;

    private static final String CREATE_ACCOUNT = "create account";
    private static final String LOGIN = "login";
    private static final String SHOW_LEADER_BOARD = "show leaderboard";
    private static final String SAVE = "save";
    private static final String LOGOUT = "logout";
    private static final String HELP = "help";
    private static final String SHOW_COLLECTION = "show";//todo maybe will cause a problem
    private static final String SEARCH_COLLECTION = "search";
    private static final String CREATE_DECK = "create deck";
    private static final String DELETE_DECK = "delete deck";
    private static final String ADD_TO_DECK = "add";
    private static final String REMOVE_FROM_DECK = "remove";
    private static final String VALIDATE_DECK = "validate deck";
    private static final String SELECT_DECK = "select deck";
    private static final String SHOW_ALL_DECKS = "show all decks";
    private static final String SHOW_DECK = "show deck";

    public void getNewCommand() {
        command = scanner.nextLine();
    }

    public ErrorType getError() {
        return error;
    }

    public void setError(ErrorType error) {
        this.error = error;
    }

    public boolean isValid() {
        RequestType type = getType();
        if (type == null)
            return false;
        switch (type) {
            case CREATE_ACCOUNT:
                return checkSyntaxOfCreateAccountCommand();
            case LOGIN:
                return checkSyntaxOfLoginCommand();
            case SHOW_LEADER_BOARD:
                return checkSyntaxOfShowLeaderBoardCommand();
            case SAVE_ACCOUNT:
                return checkSyntaxOfSaveCommand();
            case LOGOUT:
                return checkSyntaxOfLogOutCommand();
            case ACCOUNT_HELP:
                return checkSyntaxOfHelpCommand();
        }
        return true;
    }


    public RequestType getType() {
        if (command == null || command.equals("")) {
            return null;
        }
        Pattern patternForCreateAccount = Pattern.compile(CREATE_ACCOUNT + " \\w+");
        Matcher matcherForCreateAccount = patternForCreateAccount.matcher(command);
        Pattern patternForLogIn = Pattern.compile(LOGIN + " \\w+");
        Matcher matcherForLogIn = patternForLogIn.matcher(command);
        Pattern patternForShowLeaderBoard = Pattern.compile(SHOW_LEADER_BOARD);
        Matcher matcherForShowLeaderBoard = patternForShowLeaderBoard.matcher(command);
        Pattern patternForSaveAccount = Pattern.compile(SAVE);
        Matcher matcherForSaveAccount = patternForSaveAccount.matcher(command);
        Pattern patternForLogOut = Pattern.compile(LOGOUT );
        Matcher matcherForLogOut = patternForLogOut.matcher(command);
        Pattern patternForAccountHelp = Pattern.compile(HELP);
        Matcher matcherForAccuntHelp = patternForAccountHelp.matcher(command);
        Pattern patternForSearchCollection = Pattern.compile(SEARCH_COLLECTION+ " \\w+");
        Matcher matcherForSearchCollection = patternForSearchCollection.matcher(command);
        Pattern patternForCreateDeck = Pattern.compile(CREATE_DECK + " \\w+");
        Matcher matcherForCreateDeck = patternForCreateDeck.matcher(command);
        Pattern patternForDeleteDeck = Pattern.compile(DELETE_DECK + " \\w+");
        Matcher matcherForDeleteDeck = patternForDeleteDeck.matcher(command);
        Pattern patternForAddToDeck = Pattern.compile(ADD_TO_DECK + " \\w+ to deck \\w+");
        Matcher matcherForAddToDeck = patternForAddToDeck.matcher(command);
        Pattern patternForRemoveFromDeck = Pattern.compile(REMOVE_FROM_DECK + " \\w+ from deck \\w+");
        Matcher matcherForRemoveFromDeck = patternForRemoveFromDeck.matcher(command);
        Pattern patternForValidateDeck = Pattern.compile(VALIDATE_DECK + " \\w+");
        Matcher matcherForValidateDeck = patternForValidateDeck.matcher(command);
        Pattern patternForSelectDeck = Pattern.compile(SELECT_DECK + " \\w+");
        Matcher matcherForSelectDeck = patternForSelectDeck.matcher(command);
        Pattern patternForShowAllDecks = Pattern.compile(SHOW_ALL_DECKS);
        Matcher matcherForShowAllDecks = patternForShowAllDecks.matcher(command);
        Pattern patternForShowDeck = Pattern.compile(SHOW_DECK+" \\w+");
        Matcher matcherForShowDeck = patternForShowDeck.matcher(command);
        if (matcherForSearchCollection.matches()) {
            menuType=MenuType.ACCOUNT_MENU;
            return RequestType.CREATE_ACCOUNT;
        } else if (matcherForLogIn.matches()) {
            menuType=MenuType.ACCOUNT_MENU;
            return RequestType.LOGIN;
        } else if (matcherForShowLeaderBoard.matches()) {
            menuType=MenuType.ACCOUNT_MENU;
            return RequestType.SHOW_LEADER_BOARD;
        } else if (matcherForSaveAccount.matches()) {
            menuType=MenuType.ACCOUNT_MENU;
            return RequestType.SAVE_ACCOUNT;
        } else if (matcherForLogOut.matches()) {
            menuType=MenuType.ACCOUNT_MENU;
            return RequestType.LOGOUT;
        } else if (matcherForAccuntHelp.matches()) {
            menuType=MenuType.ACCOUNT_MENU;
            return RequestType.ACCOUNT_HELP;
        }else if(matcherForSearchCollection.matches()){
            menuType=MenuType.COLLECTION_MENU;
            return RequestType.SEARCH_COLLECTION;
        }else if(matcherForCreateDeck.matches()){
            menuType=MenuType.COLLECTION_MENU;
            return RequestType.CREATE_DECK;
        }else if(matcherForDeleteDeck.matches()){
            menuType=MenuType.COLLECTION_MENU;
            return RequestType.DELETE_DECK;
        }else if(matcherForAddToDeck.matches()){
            menuType=MenuType.COLLECTION_MENU;
            return RequestType.ADD_TO_DECK;
        }else if(matcherForRemoveFromDeck.matches()){
            menuType=MenuType.COLLECTION_MENU;
            return RequestType.REMOVE_FROM_DECK;
        }else if(matcherForValidateDeck.matches()){
            menuType=MenuType.COLLECTION_MENU;
            return RequestType.VALIDATE_DECK;
        }else if(matcherForSelectDeck.matches()){
            menuType=MenuType.COLLECTION_MENU;
            return RequestType.SELECT_DECK;
        }else if(matcherForShowAllDecks.matches()){
            menuType=MenuType.COLLECTION_MENU;
            return RequestType.SHOW_ALL_DECKS;
        }else if(matcherForShowDeck.matches()){
            menuType=MenuType.COLLECTION_MENU;
            return RequestType.SHOW_DECK;
        }
        error = ErrorType.INVALID_INPUT;
        return RequestType.EXIT;//todo نمیدونم اینو:(
    }


    public boolean checkSyntaxOfCreateAccountCommand() {
        Pattern patternForCreateAccount = Pattern.compile(CREATE_ACCOUNT + " (?<userName>\\w+)");
        Matcher matcher = patternForCreateAccount.matcher(command);
        if (matcher.matches()){
            String userName = matcher.group("userName");
            String passWord = scanner.nextLine();
            String result = GameController.createAccount(userName, passWord);
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
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
            String result = GameController.save();
            System.out.println(result);
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
            AccountView.accountHelp();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

}
