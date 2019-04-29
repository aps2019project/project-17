package view;

import java.util.Scanner;

import controller.GameController;

public class Request {
    protected Scanner scanner = new Scanner(System.in);
    protected String command;
    protected ErrorType error = null;

    private static final String CREATE_ACCOUNT = "create account";
    private static final String LOGIN = "login";
    private static final String SHOW_LEADER_BOARD = "show leaderboard";
    private static final String SAVE_ACCOUNT = "save";
    private static final String LOGOUT = "logout";
    private static final String ACCOUNT_HELP = "help";
    private static final String SHOW_COLLECTION = "show";
    private static final String SEARCH_COLLECTION = "search";
    private static final String SAVE_COLLECTION = "save";
    private static final String CREATE_DECK = "create deck";
    private static final String DELETE_DECK = "delete deck";
    private static final String ADD_TO_DECK = "add";
    private static final String REMOVE_FROM_DECK = "remove";
    private static final String VALIDATE_DECK = "validate deck";
    private static final String SELECT_DECK = "select deck";
    private static final String SHOW_ALL_DECKS = "show all decks";
    private static final String SHOW_DECK = "show deck";
    private static final String COLLECTION_HELP = "help";

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
        if (command.substring(0, 14).matches(CREATE_ACCOUNT)) {
            return RequestType.CREATE_ACCOUNT;
        } else if (command.substring(0, 5).matches(LOGIN)) {
            return RequestType.LOGIN;
        } else if (command.substring(0, 16).matches(SHOW_LEADER_BOARD)) {
            return RequestType.SHOW_LEADER_BOARD;
        } else if (command.substring(0, 4).matches(SAVE_ACCOUNT)) {
            return RequestType.SAVE_ACCOUNT;
        } else if (command.substring(0, 6).matches(LOGOUT)) {
            return RequestType.LOGOUT;
        } else if (command.substring(0, 4).matches(ACCOUNT_HELP)) {
            return RequestType.ACCOUNT_HELP;
        }
        error = ErrorType.INVALID_INPUT;
        return RequestType.EXIT;//todo نمیدونم اینو:(
    }


    public boolean checkSyntaxOfCreateAccountCommand() {
        if (command.toLowerCase().matches("create account \\w+")) {
            String userName = command.split(" ")[2];
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
        if (command.toLowerCase().matches("login \\w+")) {
            String userName = command.split(" ")[1];
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
        if (command.toLowerCase().matches("show leaderboard")) {
            AccountView.showLeaderBoard();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfSaveCommand() {//todo must to be specified for different menus
        if (command.toLowerCase().matches("save")) {
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
        if (command.toLowerCase().matches("help")) {
            AccountView.accountHelp();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

}
