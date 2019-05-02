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
    private static final String GAME_INFO = "game info";
    private static final String SHOW_MY_MINIONS = "show my minions";
    private static final String SHOW_OPPONENT_MINIONS = "show opponent minions";
    private static final String SHOW_CARD_INFO = "show card info";
    private static final String SELECT = "select";//foe selecting card or item in game ground
    private static final String MOVE_TO = "move to";
    private static final String ATTACK = "attack";
    private static final String ATTACK_COMBO = "attac combo";
    private static final String USE_SPECIAL_POWER = "use special power";
    private static final String SHOW_HAND = "show hand";
    private static final String INSERT = "insert";
    private static final String END_TURN = "end turn";
    private static final String SHOW_COLLECTIBLES = "show collectibles";
    private static final String SHOW_INFO = "show info";//for battle or grave yard
    private static final String USE = "use";
    private static final String SHOW_NEXT_CARD = "show next card";
    private static final String ENTER_GRAVE_YARD = "enter grave yard";
    private static final String SHOW_CARDS = "show cards";
    private static final String END_GAME = "end game";

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
            case SEARCH_COLLECTION:
                return checkSyntaxOfSearchCollection();
            case BUY:
                return checkSyntaxOfBuyCommand();
            case SELL:
                return checkSyntaxOfSellCommand();
            case GAME_INFO:
                return checkSyntaxOfGameInfo();
            case SHOW_MY_MINIONS:
                return checkSyntaxOfShowMyMinions();
            case SHOW_OPPONENT_MINIONS:
                return checkSyntaxOfShowOpponentMinions();
            case SHOW_CARD_INFO:
                return checkSyntaxOfShowCardInfo();
            case SELECT:
                return checkSyntaxOfSelect();
            case MOVE_TO:
                return checkSyntaxOfMoveTO();
            case ATTACK:
                return checkSyntaxOfAtack();
            case ATTACK_COMBO:
                return checkSyntaxOfComboAttack();
            case USE_SPECIAL_POWER:
                return checkSyntaxOfUseSpecialPower();
            case SHOW_HAND:
                return checkSyntaxOfShowHand();
            case INSERT:
                return checkSyntaxOfInsert();
            case END_TURN:
                return checkSyntaxOfEndTurn();
            case SHOW_COLLECTIBLES:
                return checkSyntaxOfShowCollectibles();
            case SHOW_INFO:
                return checkSyntaxOfShowInfo();
            case USE:
                return checkSyntaxOfUse();
            case SHOW_NEXT_CARD:
                return checkSyntaxOfShowNextCard();
            case SHOW_CARDS:
                return checkSyntaxOfShowCards();
            case END_GAME:
                return checkSyntaxOfEndGame();
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
        Matcher matcherForSell = patternForSell.matcher(command);
        Pattern patternForGameInfo = Pattern.compile(GAME_INFO);
        Matcher matcherForGameInfo = patternForGameInfo.matcher(command);
        Pattern patternForShowMyMinions = Pattern.compile(SHOW_MY_MINIONS);
        Matcher matcherForShowMyMinions = patternForShowMyMinions.matcher(command);
        Pattern patternForShowOpponentMinions = Pattern.compile(SHOW_OPPONENT_MINIONS);
        Matcher matcherForShowOpponentMinions = patternForShowOpponentMinions.matcher(command);
        Pattern patternForShowCardInfo = Pattern.compile(SHOW_CARD_INFO + " \\w+");
        Matcher matcherForShowCardInfo = patternForShowCardInfo.matcher(command);
        Pattern patternForSelect = Pattern.compile(SELECT + " \\w+");
        Matcher matcherForSelect = patternForSelect.matcher(command);
        Pattern patternForMoveTO = Pattern.compile(MOVE_TO + " \\(\\d,\\d\\)");
        Matcher matcherForMoveTO = patternForMoveTO.matcher(command);
        Pattern patternForAttack = Pattern.compile(ATTACK + " \\w+");
        Matcher matcherForAttack = patternForAttack.matcher(command);
        Pattern patternForAttackCombo = Pattern.compile(ATTACK_COMBO);
        Matcher matcherForAttackCombo = patternForAttackCombo.matcher(command);
        Pattern patternForUserSpecialPower = Pattern.compile(USE_SPECIAL_POWER + " \\(\\d,\\d\\)");
        Matcher matcherForUserSpecialPower = patternForUserSpecialPower.matcher(command);
        Pattern patternForShowHand = Pattern.compile(SHOW_HAND);
        Matcher matcherForShowHand = patternForShowHand.matcher(command);
        Pattern patternForInsert = Pattern.compile(INSERT + " \\w+ in \\(\\d,\\d\\)");
        Matcher matcherForInsert = patternForInsert.matcher(command);
        Pattern patternForEndTurn = Pattern.compile(END_TURN);
        Matcher matcherForEndTurn = patternForEndTurn.matcher(command);
        Pattern patternForShowCollectibles = Pattern.compile(SHOW_COLLECTIBLES);
        Matcher matcherForShowCollectibles = patternForShowCollectibles.matcher(command);
        Pattern patternForShowInfo = Pattern.compile(SHOW_INFO);
        Matcher matcherForShowInfo = patternForShowInfo.matcher(command);
        Pattern patternForUse = Pattern.compile(USE + " \\(\\d,\\d\\)");
        Matcher matcherForUse = patternForUse.matcher(command);
        Pattern patternForShowNextCard = Pattern.compile(SHOW_NEXT_CARD);
        Matcher matcherForShowNextCard = patternForShowNextCard.matcher(command);
        Pattern patternForEnterGraveYard = Pattern.compile(ENTER_GRAVE_YARD);
        Matcher matcherForEnterGraveYard = patternForEnterGraveYard.matcher(command);
        Pattern patternForShowCards = Pattern.compile(SHOW_CARDS);
        Matcher matcherForShowCards = patternForShowCards.matcher(command);
        Pattern patternForEndGame = Pattern.compile(END_GAME);
        Matcher matcherForEndGame = patternForEndGame.matcher(command);


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
        } else if (matcherForSell.matches()) {
            menuType = MenuType.SHOP_MENU;
            return RequestType.SELL;
        } else if (matcherForGameInfo.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.GAME_INFO;
        } else if (matcherForShowMyMinions.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.SHOW_MY_MINIONS;
        } else if (matcherForShowOpponentMinions.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.SHOW_OPPONENT_MINIONS;
        } else if (matcherForShowCardInfo.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.SHOW_CARD_INFO;
        } else if (matcherForSelect.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.SELECT;
        } else if (matcherForMoveTO.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.MOVE_TO;
        } else if (matcherForAttack.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.ATTACK;
        } else if (matcherForAttackCombo.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.ATTACK_COMBO;
        } else if (matcherForUserSpecialPower.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.USE_SPECIAL_POWER;
        } else if (matcherForShowHand.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.SHOW_HAND;
        } else if (matcherForInsert.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.INSERT;
        } else if (matcherForEndTurn.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.END_TURN;
        } else if (matcherForShowCollectibles.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.SHOW_COLLECTIBLES;
        } else if (matcherForShowInfo.matches()) {//for battle or grave yard menu
            return RequestType.SHOW_INFO;
        } else if (matcherForUse.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.USE;
        } else if (matcherForShowNextCard.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.SHOW_NEXT_CARD;
        } else if (matcherForEnterGraveYard.matches()) {
            menuType = MenuType.GRAVE_YARD;
            return RequestType.ENTER_GRAVE_YARD;
        } else if (matcherForShowCards.matches()) {
            menuType = MenuType.GRAVE_YARD;
            return RequestType.SHOW_CARDS;
        } else if (matcherForEndGame.matches()) {
            menuType = MenuType.BATTLE_MENU;
            return RequestType.END_GAME;
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
            System.out.println("password: ");
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

    public boolean checkSyntaxOfSaveCommand() {
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

    public boolean checkSyntaxOfHelpCommand() {
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
        } else if (menuType.equals(MenuType.SHOP_MENU)) {
            if (matcher.matches()) {
                ShopView.showAllProducts(Account.getLoginUser());
            } else {
                error = ErrorType.INVALID_INPUT;
                return false;
            }
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
        } else if (menuType.equals(MenuType.SHOP_MENU)) {
            if (matcher.matches()) {
                String name = matcher.group("name");
                String ID = GameController.searchInShop(name, Account.getLoginUser().getShop());
                System.out.println(ID);
            } else {
                error = ErrorType.INVALID_INPUT;
                return false;
            }
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
            String result = GameController.setMainDeck(name, Account.getLoginUser().getCollection());
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

    public boolean checkSyntaxForShowForShopMenu() {
        Pattern patternForShowForShopMenu = Pattern.compile(SHOW_FOR_SHOP_MENU + "");
        Matcher matcher = patternForShowForShopMenu.matcher(command);
        if (matcher.matches()) {
            CollectionView.showUserCollection(Account.getLoginUser());
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfSearchCollection() {
        Pattern patternForSearchCollection = Pattern.compile(SEARCH_COLLECTION + " " + "(?<name>\\w+)");
        Matcher matcher = patternForSearchCollection.matcher(command);
        if (matcher.matches()) {//todo what to do if there was more than one card with that name?
            String name = matcher.group("name");
            String ID = GameController.search(name, Account.getLoginUser().getCollection());
            System.out.println(ID);
            return true;
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
    }

    public boolean checkSyntaxOfBuyCommand() {
        Pattern patternForBuy = Pattern.compile(BUY + " (?<name>\\w+)");
        Matcher matcher = patternForBuy.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.buy(name, Account.getLoginUser().getShop());
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfSellCommand() {
        Pattern patternForSell = Pattern.compile(SELL + " (?<name>\\w+)");
        Matcher matcher = patternForSell.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.sell(name, Account.getLoginUser().getShop());
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfGameInfo() {
        Pattern patternForGameInfo = Pattern.compile(GAME_INFO);
        Matcher matcher = patternForGameInfo.matcher(command);
        if (matcher.matches()) {
            BattleView.showGameInfo();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowMyMinions() {
        Pattern patternForShowMyMinions = Pattern.compile(SHOW_MY_MINIONS);
        Matcher matcher = patternForShowMyMinions.matcher(command);
        if (matcher.matches()) {
            BattleView.showMyMinions();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowOpponentMinions() {
        Pattern patternForShowOpponentMinions = Pattern.compile(SHOW_OPPONENT_MINIONS);
        Matcher matcher = patternForShowOpponentMinions.matcher(command);
        if (matcher.matches()) {
            BattleView.showOpponentMinions();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowCardInfo() {
        Pattern patternForShowCardInfo = Pattern.compile(SHOW_CARD_INFO + " (?<cardId>\\w+)");
        Matcher matcher = patternForShowCardInfo.matcher(command);
        if (matcher.matches()) {
            String cardId = matcher.group("cardId");
            BattleView.showCardInfo(, cardId);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfSelect() {
        Pattern patternForSelect = Pattern.compile(SELECT + " (?<cardId>\\w+)");
        Matcher matcher = patternForSelect.matcher(command);
        String cardId = matcher.group("cardId");
        //todo have to set a method in battle fo selecting item then will complete this method according to id(card or item)
        return true;
    }

    public boolean checkSyntaxOfMoveTO() {
        Pattern patternForMoveTO = Pattern.compile(MOVE_TO + " \\((?<x>\\d),(?<y>\\d)\\)");
        Matcher matcher = patternForMoveTO.matcher(command);
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            String result = GameController.movingCard(x, y, );
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfAtack() {
        Pattern patternForAttack = Pattern.compile(ATTACK + " (?<cardId>\\w+)");
        Matcher matcher = patternForAttack.matcher(command);
        String cardId = matcher.group("cardId");
        if (matcher.matches()) {
            String result = GameController.attack(cardId, );
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfComboAttack() {
        Pattern patternForAttackCombo = Pattern.compile(ATTACK_COMBO);
        Matcher matcher = patternForAttackCombo.matcher(command);
        // TODO: the combo attack method must   added and the syntax can get more than one card id so this must be handled
        return true;
    }

    public boolean checkSyntaxOfUseSpecialPower() {
        Pattern patternForUserSpecialPower = Pattern.compile(USE_SPECIAL_POWER + " \\((?<x>\\d),(?<y>\\d)\\)");
        Matcher matcher = patternForUserSpecialPower.matcher(command);
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            // TODO: must to be completed with methods
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowHand() {
        Pattern patternForShowHand = Pattern.compile(SHOW_HAND);
        Matcher matcher = patternForShowHand.matcher(command);
        if (matcher.matches()) {
            BattleView.showHand();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfInsert() {
        Pattern patternForInsert = Pattern.compile(INSERT + " (?<cardName>\\w)+ in  \\((?<x>\\d),(?<y>\\d)\\)");
        Matcher matcher = patternForInsert.matcher(command);
        if (matcher.matches()) {
            String cardName = matcher.group("cardName");
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            String result = GameController.insertCard(cardName, x, y, );
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfEndTurn() {
        Pattern patternForEndTurn = Pattern.compile(END_TURN);
        Matcher matcher = patternForEndTurn.matcher(command);
        if (matcher.matches()) {
            GameController.endTurn();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowCollectibles() {
        Pattern patternForShowCollectibles = Pattern.compile(SHOW_COLLECTIBLES);
        Matcher matcher = patternForShowCollectibles.matcher(command);
        if (matcher.matches()) {
            BattleView.showCollectables();// TODO: must to be completed
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowInfo() {
        if (menuType.equals(MenuType.BATTLE_MENU)) {
            Pattern patternForShowInfoInBattleMenu = Pattern.compile(SHOW_INFO);
            Matcher matcher = patternForShowInfoInBattleMenu.matcher(command);
            if (matcher.matches()) {
                BattleView.showInfo();// TODO: must to be completed
            } else {
                error = ErrorType.INVALID_INPUT;
                return false;
            }
        } else if (menuType.equals(MenuType.GRAVE_YARD)) {
            Pattern patternForShowInfoInBGraveYardMenu = Pattern.compile(SHOW_INFO + " (?<cardId>\\w+)");
            Matcher matcher = patternForShowInfoInBGraveYardMenu.matcher(command);
            if (matcher.matches()) {
                String cardId = matcher.group("cardId");
                BattleView.showCardInfo(, cardId);
                return true;
            } else {
                error = ErrorType.INVALID_INPUT;
                return false;
            }
        }
        return true;
    }

    public boolean checkSyntaxOfUse() {
        Pattern patternForUse = Pattern.compile(USE + " \\(\\d,\\d\\)");
        Matcher matcher = patternForUse.matcher(command);
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            // TODO: must to be completed by adding use method for items
            return true;
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
    }

    public boolean checkSyntaxOfShowNextCard() {
        Pattern patternForShowNextCard = Pattern.compile(SHOW_NEXT_CARD);
        Matcher matcher = patternForShowNextCard.matcher(command);
        if (matcher.matches()) {
            BattleView.showNextCard();// TODO: must to be completed
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfShowCards() {
        Pattern patternForShowCards = Pattern.compile(SHOW_CARDS);
        Matcher matcher = patternForShowCards.matcher(command);
        if (matcher.matches()) {
            // TODO: a method for showing grave yard cards (easy)
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfEndGame() {
        Pattern patternForEndGame = Pattern.compile(END_GAME);
        Matcher matcher = patternForEndGame.matcher(command);
        if (matcher.matches()) {
            // TODO: needs a method for ending game
            return true;
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
    }
}
