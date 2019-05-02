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

        Pattern patternForCreateAccount = Pattern.compile(StringsRq.CREATE_ACCOUNT + " " + "\\w+");
        Matcher matcherForCreateAccount = patternForCreateAccount.matcher(command);
        Pattern patternForLogIn = Pattern.compile(StringsRq.LOGIN + " " + "\\w+");
        Matcher matcherForLogIn = patternForLogIn.matcher(command);
        Pattern patternForShowLeaderBoard = Pattern.compile(StringsRq.SHOW_LEADER_BOARD);
        Matcher matcherForShowLeaderBoard = patternForShowLeaderBoard.matcher(command);
        Pattern patternForSave = Pattern.compile(StringsRq.SAVE);
        Matcher matcherForSave = patternForSave.matcher(command);
        Pattern patternForLogOut = Pattern.compile(StringsRq.LOGOUT);
        Matcher matcherForLogOut = patternForLogOut.matcher(command);
        Pattern patternForHelp = Pattern.compile(StringsRq.HELP);
        Matcher matcherForHelp = patternForHelp.matcher(command);
        Pattern patternForShow = Pattern.compile(StringsRq.SHOW);
        Matcher matcherForShow = patternForShow.matcher(command);
        Pattern patternForSearch = Pattern.compile(StringsRq.SEARCH + " \\w+");
        Matcher matcherForSearch = patternForSearch.matcher(command);
        Pattern patternForCreateDeck = Pattern.compile(StringsRq.CREATE_DECK + " \\w+");
        Matcher matcherForCreateDeck = patternForCreateDeck.matcher(command);
        Pattern patternForDeleteDeck = Pattern.compile(StringsRq.DELETE_DECK + " \\w+");
        Matcher matcherForDeleteDeck = patternForDeleteDeck.matcher(command);
        Pattern patternForAddToDeck = Pattern.compile(StringsRq.ADD_TO_DECK + " " + "\\w+ to deck \\w+");
        Matcher matcherForAddToDeck = patternForAddToDeck.matcher(command);
        Pattern patternForRemoveFromDeck = Pattern.compile(StringsRq.REMOVE_FROM_DECK + " \\w+ from deck \\w+");
        Matcher matcherForRemoveFromDeck = patternForRemoveFromDeck.matcher(command);
        Pattern patternForValidateDeck = Pattern.compile(StringsRq.VALIDATE_DECK + " " + "\\w+");
        Matcher matcherForValidateDeck = patternForValidateDeck.matcher(command);
        Pattern patternForSelectDeck = Pattern.compile(StringsRq.SELECT_DECK + " " + "\\w+");
        Matcher matcherForSelectDeck = patternForSelectDeck.matcher(command);
        Pattern patternForShowAllDecks = Pattern.compile(StringsRq.SHOW_ALL_DECKS);
        Matcher matcherForShowAllDecks = patternForShowAllDecks.matcher(command);
        Pattern patternForShowDeck = Pattern.compile(StringsRq.SHOW_DECK + " \\w+");
        Matcher matcherForShowDeck = patternForShowDeck.matcher(command);
        Pattern patternForShowForShopMenu = Pattern.compile(StringsRq.SHOW_FOR_SHOP_MENU);
        Matcher matcherForShowForShopMenu = patternForShowForShopMenu.matcher(command);
        Pattern patternForSearchCollection = Pattern.compile(StringsRq.SEARCH_COLLECTION + " " + "\\w+");
        Matcher matcherForSearchCollection = patternForSearchCollection.matcher(command);
        Pattern patternForBuy = Pattern.compile(StringsRq.BUY + " \\w+");
        Matcher matcherForSBuy = patternForBuy.matcher(command);
        Pattern patternForSell = Pattern.compile(StringsRq.SELL + " " + "\\w+");
        Matcher matcherForSell = patternForSell.matcher(command);
        Pattern patternForGameInfo = Pattern.compile(StringsRq.GAME_INFO);
        Matcher matcherForGameInfo = patternForGameInfo.matcher(command);
        Pattern patternForShowMyMinions = Pattern.compile(StringsRq.SHOW_MY_MINIONS);
        Matcher matcherForShowMyMinions = patternForShowMyMinions.matcher(command);
        Pattern patternForShowOpponentMinions = Pattern.compile(StringsRq.SHOW_OPPONENT_MINIONS);
        Matcher matcherForShowOpponentMinions = patternForShowOpponentMinions.matcher(command);
        Pattern patternForShowCardInfo = Pattern.compile(StringsRq.SHOW_CARD_INFO + " \\w+");
        Matcher matcherForShowCardInfo = patternForShowCardInfo.matcher(command);
        Pattern patternForSelect = Pattern.compile(StringsRq.SELECT + " \\w+");
        Matcher matcherForSelect = patternForSelect.matcher(command);
        Pattern patternForMoveTO = Pattern.compile(StringsRq.MOVE_TO + " \\(\\d,\\d\\)");
        Matcher matcherForMoveTO = patternForMoveTO.matcher(command);
        Pattern patternForAttack = Pattern.compile(StringsRq.ATTACK + " \\w+");
        Matcher matcherForAttack = patternForAttack.matcher(command);
        Pattern patternForAttackCombo = Pattern.compile(StringsRq.ATTACK_COMBO);
        Matcher matcherForAttackCombo = patternForAttackCombo.matcher(command);
        Pattern patternForUserSpecialPower = Pattern.compile(StringsRq.USE_SPECIAL_POWER + " \\(\\d,\\d\\)");
        Matcher matcherForUserSpecialPower = patternForUserSpecialPower.matcher(command);
        Pattern patternForShowHand = Pattern.compile(StringsRq.SHOW_HAND);
        Matcher matcherForShowHand = patternForShowHand.matcher(command);
        Pattern patternForInsert = Pattern.compile(StringsRq.INSERT + " \\w+ in \\(\\d,\\d\\)");
        Matcher matcherForInsert = patternForInsert.matcher(command);
        Pattern patternForEndTurn = Pattern.compile(StringsRq.END_TURN);
        Matcher matcherForEndTurn = patternForEndTurn.matcher(command);
        Pattern patternForShowCollectibles = Pattern.compile(StringsRq.SHOW_COLLECTIBLES);
        Matcher matcherForShowCollectibles = patternForShowCollectibles.matcher(command);
        Pattern patternForShowInfo = Pattern.compile(StringsRq.SHOW_INFO);
        Matcher matcherForShowInfo = patternForShowInfo.matcher(command);
        Pattern patternForUse = Pattern.compile(StringsRq.USE + " \\(\\d,\\d\\)");
        Matcher matcherForUse = patternForUse.matcher(command);
        Pattern patternForShowNextCard = Pattern.compile(StringsRq.SHOW_NEXT_CARD);
        Matcher matcherForShowNextCard = patternForShowNextCard.matcher(command);
        Pattern patternForEnterGraveYard = Pattern.compile(StringsRq.ENTER_GRAVE_YARD);
        Matcher matcherForEnterGraveYard = patternForEnterGraveYard.matcher(command);
        Pattern patternForShowCards = Pattern.compile(StringsRq.SHOW_CARDS);
        Matcher matcherForShowCards = patternForShowCards.matcher(command);
        Pattern patternForEndGame = Pattern.compile(StringsRq.END_GAME);
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
        Pattern patternForCreateAccount = Pattern.compile(StringsRq.CREATE_ACCOUNT + " (?<userName>\\w+)");
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
        Pattern patternForLogIn = Pattern.compile(StringsRq.LOGIN + " (?<userName>\\w+)");
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
        Pattern patternForShowLeaderBoard = Pattern.compile(StringsRq.SHOW_LEADER_BOARD);
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
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfLogOutCommand() {
        if (command.toLowerCase().matches(StringsRq.LOGOUT)) {
            String result = GameController.logout();
            System.out.println(result);
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxOfHelpCommand() {
        Pattern patternForSave = Pattern.compile(StringsRq.HELP);
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
        Pattern patternForShow = Pattern.compile(StringsRq.SHOW);
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
        Pattern patternForSearchCollection = Pattern.compile(StringsRq.SEARCH + " (?<name>\\w+)");
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
        Pattern patternForSCreateDeck = Pattern.compile(StringsRq.CREATE_DECK + " (?<name>\\w+)");
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
        Pattern patternForDeleteDeck = Pattern.compile(StringsRq.DELETE_DECK + " (?<name>\\w+)");
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
        Pattern patternForAddToDeck = Pattern.compile(StringsRq.ADD_TO_DECK + " (?<cardId>\\w+) to deck (?<deckName>\\w+)");
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
        Pattern patternForRemoveFromDeck = Pattern.compile(StringsRq.REMOVE_FROM_DECK + " (?<cardId>\\w+) from deck (?<deckName>\\w+)");
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
        Pattern patternForValidateDeck = Pattern.compile(StringsRq.VALIDATE_DECK + " (?<name>\\w+)");
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
        Pattern patternForSelectDeck = Pattern.compile(StringsRq.SELECT_DECK + " (?<name>\\w+)");
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
        Pattern patternForShowAllDecks = Pattern.compile(StringsRq.SHOW_ALL_DECKS);
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
        Pattern patternForShowDeck = Pattern.compile(StringsRq.SELECT_DECK + " (?<name>\\w+)");
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
        Pattern patternForShowForShopMenu = Pattern.compile(StringsRq.SHOW_FOR_SHOP_MENU + "");
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
        Pattern patternForSearchCollection = Pattern.compile(StringsRq.SEARCH_COLLECTION + " " + "(?<name>\\w+)");
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
        Pattern patternForBuy = Pattern.compile(StringsRq.BUY + " (?<name>\\w+)");
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
        Pattern patternForSell = Pattern.compile(StringsRq.SELL + " (?<name>\\w+)");
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

    public boolean checkSyntaxForGameInfo() {
        Pattern patternForGameInfo = Pattern.compile(StringsRq.GAME_INFO);
        Matcher matcher = patternForGameInfo.matcher(command);
        if (matcher.matches()) {
            BattleView.showGameInfo();
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    public boolean checkSyntaxForShowMyMinions() {
        Pattern patternForShowMyMinions = Pattern.compile(StringsRq.SHOW_MY_MINIONS);
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
        Pattern patternForShowOpponentMinions = Pattern.compile(StringsRq.SHOW_OPPONENT_MINIONS);
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
        Pattern patternForShowCardInfo = Pattern.compile(StringsRq.SHOW_CARD_INFO + " (?<cardId>\\w+)");
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

    public boolean checkSyntaxForSelect() {
        Pattern patternForSelect = Pattern.compile(StringsRq.SELECT + " (?<cardId>\\w+)");
        Matcher matcher = patternForSelect.matcher(command);
        String cardId = matcher.group("cardId");
        //todo have to set a method in battle fo selecting item then will complete this method according to id(card or item)
        return true;
    }

    public boolean checkSyntaxOfMoveTO() {
        Pattern patternForMoveTO = Pattern.compile(StringsRq.MOVE_TO + " \\((?<x>\\d),(?<y>\\d)\\)");
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
        Pattern patternForAttack = Pattern.compile(StringsRq.ATTACK + " (?<cardId>\\w+)");
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
        Pattern patternForAttackCombo = Pattern.compile(StringsRq.ATTACK_COMBO);
        Matcher matcher = patternForAttackCombo.matcher(command);
        // TODO: the combo attack method must   added and the syntax can get more than one card id so this must be handled
        return true;
    }

    public boolean checkSyntaxOfUseSpecialPower() {
        Pattern patternForUserSpecialPower = Pattern.compile(StringsRq.USE_SPECIAL_POWER + " \\((?<x>\\d),(?<y>\\d)\\)");
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
        Pattern patternForShowHand = Pattern.compile(StringsRq.SHOW_HAND);
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
        Pattern patternForInsert = Pattern.compile(StringsRq.INSERT + " (?<cardName>\\w)+ in  \\((?<x>\\d),(?<y>\\d)\\)");
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
        Pattern patternForEndTurn = Pattern.compile(StringsRq.END_TURN);
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
        Pattern patternForShowCollectibles = Pattern.compile(StringsRq.SHOW_COLLECTIBLES);
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
            Pattern patternForShowInfoInBattleMenu = Pattern.compile(StringsRq.SHOW_INFO);
            Matcher matcher = patternForShowInfoInBattleMenu.matcher(command);
            if (matcher.matches()) {
                BattleView.showInfo();// TODO: must to be completed
            } else {
                error = ErrorType.INVALID_INPUT;
                return false;
            }
        } else if (menuType.equals(MenuType.GRAVE_YARD)) {
            Pattern patternForShowInfoInBGraveYardMenu = Pattern.compile(StringsRq.SHOW_INFO + " (?<cardId>\\w+)");
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
        Pattern patternForUse = Pattern.compile(StringsRq.USE + " \\(\\d,\\d\\)");
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

    public boolean checkSyntaxForShowNextCard() {
        Pattern patternForShowNextCard = Pattern.compile(StringsRq.SHOW_NEXT_CARD);
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
        Pattern patternForShowCards = Pattern.compile(StringsRq.SHOW_CARDS);
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
        Pattern patternForEndGame = Pattern.compile(StringsRq.END_GAME);
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
