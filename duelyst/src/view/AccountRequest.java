package view;

public class AccountRequest extends Request {

    private static final String CREATE_ACCOUNT = "create";
    private static final String LOGIN = "login";
    private static final String SHOW_LEADER_BOARD = "show";
    private static final String SAVE = "save";
    private static final String LOGOUT = "logout";
    private static final String HELP = "help";



    public RequestType getType() {
        if (command == null || command.equals("")) {
            return null;
        }
        if (command.substring(0, 6).matches(CREATE_ACCOUNT)) {
            return RequestType.CREATE_ACCOUNT;
        } else if (command.substring(0, 5).matches(LOGIN)) {
            return RequestType.LOGIN;
        } else if (command.substring(0, 4).matches(SHOW_LEADER_BOARD)) {
            return RequestType.SHOW_LEADER_BOARD;
        } else if (command.substring(0, 4).matches(SAVE)) {
            return RequestType.SAVE;
        } else if (command.substring(0, 6).matches(LOGOUT)) {
            return RequestType.LOGOUT;
        } else if (command.substring(0, 4).matches(HELP)) {
            return RequestType.HELP;
        }
        return RequestType.EXIT;
    }

    /**
     * public boolean checkSyntaxOfCreateAccountCommand() {
     *
     *     }
     *
     *     public boolean checkSyntaxOfLoginCommand() {
     *
     *     }
     *
     *     public boolean checkSyntaxOfShowLeaderBoardCommand() {
     *
     *     }
     *
     *     public boolean checkSyntaxOfSaveCommand() {
     *
     *     }
     *
     *     public boolean checkSyntaxOfLogOutCommand() {
     *
     *     }
     *
     *     public boolean checkSyntaxOfHelpCommand() {
     *
     *     }

     */

}
