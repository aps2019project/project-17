package view;

import Appearance.ExceptionEndGame;
import CardCollections.Deck;
import Data.Account;
import GameGround.*;
import controller.GameController;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Data.MODE.*;

public class Request {
    private static Scanner scanner = new Scanner(System.in);
    private static String command;
    private static ErrorType error;
    private static MenuType menuType;
    private static String secondPlayerUserName = null;

    static {
        //we set the default menu in constructor
        menuType = MenuType.ACCOUNT_MENU;
        error = null;
    }

    public static void getNewCommand() {
        command = scanner.nextLine().toLowerCase().trim();
    }

    public static ErrorType getError() {
        return error;
    }

    static void changeMenuType(MenuType target) {
        menuType = target;
    }

    static void changeErrorType() {
        error = ErrorType.INVALID_INPUT;
    }

    public static boolean isValid() throws ExceptionEndGame {
        RequestType requestType = getType();

        if (requestType == null)
            return false;

        switch (requestType) {
            case ENTER:
                return checkSyntaxForEnter();
            case CREATE_ACCOUNT:
                return AccountRequest.checkSyntaxOfCreateAccountCommand(command, scanner);
            case LOGIN:
                return AccountRequest.checkSyntaxOfLoginCommand(command, scanner);
            case SHOW_LEADER_BOARD:
                return AccountRequest.checkSyntaxOfShowLeaderBoardCommand(command);
            case LOGOUT:
                return AccountRequest.checkSyntaxOfLogOutCommand(command);
            case HELP:
                return CommonRequests.checkSyntaxOfHelpCommand(command, menuType);
            case SAVE:
                return CommonRequests.checkSyntaxOfSaveCommand(command, menuType);
            case SHOW:
                return CommonRequests.checkSyntaxOfShowCommand(command, menuType);
            case SEARCH:
                return CommonRequests.checkSyntaxOfSearchCommand(command, menuType);
            case CREATE_DECK:
                return CollectionRequest.checkSyntaxOfCreateDeck(command);
            case DELETE_DECK:
                return CollectionRequest.checkSyntaxOfDeleteDeck(command);
            case ADD_TO_DECK:
                return CollectionRequest.checkSyntaxOfAddToDeck(command);
            case REMOVE_FROM_DECK:
                return CollectionRequest.checkSyntaxOfRemoveFromDeck(command);
            case VALIDATE_DECK:
                return CollectionRequest.checkSyntaxOfValidateDeck(command);
            case SELECT_DECK:
                return CollectionRequest.checkSyntaxOfSelectDeck(command);
            case SHOW_ALL_DECKS:
                return CollectionRequest.checkSyntaxOfShowAllDecks(command);
            case SHOW_DECK:
                return CollectionRequest.checkSyntaxOfShowDeck(command);
            case SHOW_fOR_SHOP_MENU:
                return ShopRequest.checkSyntaxForShowForShopMenu(command);
            case SEARCH_COLLECTION:
                return ShopRequest.checkSyntaxOfSearchCollection(command);
            case BUY:
                return ShopRequest.checkSyntaxOfBuyCommand(command);
            case SELL:
                return ShopRequest.checkSyntaxOfSellCommand(command);
            case GAME_INFO:
                return BattleRequest.checkSyntaxForGameInfo(command);
            case SHOW_MY_MINIONS:
                return BattleRequest.checkSyntaxForShowMyMinions(command);
            case SHOW_OPPONENT_MINIONS:
                return BattleRequest.checkSyntaxOfShowOpponentMinions(command);
            case SHOW_CARD_INFO:
                return BattleRequest.checkSyntaxOfShowCardInfo(command);
            case SELECT:
                return BattleRequest.checkSyntaxForSelect(command);
            case MOVE_TO:
                return BattleRequest.checkSyntaxOfMoveTO(command);
            case ATTACK:
                return BattleRequest.checkSyntaxOfAttack(command, scanner);
            case ATTACK_COMBO:
                return BattleRequest.checkSyntaxOfComboAttack(command, scanner);
            case USE_SPECIAL_POWER:
                return BattleRequest.checkSyntaxOfUseSpecialPower(command);
            case SHOW_HAND:
                return BattleRequest.checkSyntaxOfShowHand(command);
            case INSERT:
                return BattleRequest.checkSyntaxOfInsert(command);
            case END_TURN:
                return BattleRequest.checkSyntaxOfEndTurn(command);
            case SHOW_COLLECTIBLES:
                return BattleRequest.checkSyntaxOfShowCollectibles(command);
            case SHOW_INFO:
                return CommonRequests.checkSyntaxOfShowInfo(command, menuType);
            case USE:
                return BattleRequest.checkSyntaxOfUse(command);
            case SHOW_NEXT_CARD:
                return BattleRequest.checkSyntaxForShowNextCard(command);
            case SHOW_CARDS:
                return GraveYardRequest.checkSyntaxOfShowCards(command);
            case END_GAME:
                return BattleRequest.checkSyntaxOfEndGame(command);
            case ENTER_GRAVE_YARD:
                return GraveYardRequest.checkSyntaxOfEnterGraveYard();
            case START_GAME:
                return checkSyntaxOfStartGame();
            case START_MULTI_PLAYER_GAME:
                return checkSyntaxOfStartMultiPlayer();
            case EXIT:
                return CommonRequests.checkSyntaxOfExitCommand(command,menuType);
            case EXIT_GAME:
                menuType = null;
        }
        return true;
    }


    public static RequestType getType() {
        if (command == null || command.equals("")) {
            return null;
        }

        Pattern patternForEnter = Pattern.compile(StringsRq.ENTER + " \\w+");
        Matcher matcherForEnter = patternForEnter.matcher(command);
        Pattern patternForCreateAccount = Pattern.compile(StringsRq.CREATE_ACCOUNT + " " + "[\\w+ ]+");
        Matcher matcherForCreateAccount = patternForCreateAccount.matcher(command);
        Pattern patternForLogIn = Pattern.compile(StringsRq.LOGIN + " " + "[\\w+ ]+");
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
        Pattern patternForSearch = Pattern.compile(StringsRq.SEARCH + " [\\w+ ]+");
        Matcher matcherForSearch = patternForSearch.matcher(command);
        Pattern patternForCreateDeck = Pattern.compile(StringsRq.CREATE_DECK + " [\\w+ ]+");
        Matcher matcherForCreateDeck = patternForCreateDeck.matcher(command);
        Pattern patternForDeleteDeck = Pattern.compile(StringsRq.DELETE_DECK + " [\\w+ ]+");
        Matcher matcherForDeleteDeck = patternForDeleteDeck.matcher(command);
        Pattern patternForAddToDeck = Pattern.compile(StringsRq.ADD_TO_DECK + " " + "[\\w+ ]+ to deck [\\w+ ]+");
        Matcher matcherForAddToDeck = patternForAddToDeck.matcher(command);
        Pattern patternForRemoveFromDeck = Pattern.compile(StringsRq.REMOVE_FROM_DECK + " [\\w+ ]+ from deck [\\w+ ]+");
        Matcher matcherForRemoveFromDeck = patternForRemoveFromDeck.matcher(command);
        Pattern patternForValidateDeck = Pattern.compile(StringsRq.VALIDATE_DECK + " " + "[\\w+ ]+");
        Matcher matcherForValidateDeck = patternForValidateDeck.matcher(command);
        Pattern patternForSelectDeck = Pattern.compile(StringsRq.SELECT_DECK + " " + "[\\w+ ]+");
        Matcher matcherForSelectDeck = patternForSelectDeck.matcher(command);
        Pattern patternForShowAllDecks = Pattern.compile(StringsRq.SHOW_ALL_DECKS);
        Matcher matcherForShowAllDecks = patternForShowAllDecks.matcher(command);
        Pattern patternForShowDeck = Pattern.compile(StringsRq.SHOW_DECK + " [\\w+ ]+");
        Matcher matcherForShowDeck = patternForShowDeck.matcher(command);
        Pattern patternForShowForShopMenu = Pattern.compile(StringsRq.SHOW_FOR_SHOP_MENU);
        Matcher matcherForShowForShopMenu = patternForShowForShopMenu.matcher(command);
        Pattern patternForSearchCollection = Pattern.compile(StringsRq.SEARCH_COLLECTION + " " + "[\\w+ ]+");
        Matcher matcherForSearchCollection = patternForSearchCollection.matcher(command);
        Pattern patternForBuy = Pattern.compile(StringsRq.BUY + " [\\w+ ]+");
        Matcher matcherForSBuy = patternForBuy.matcher(command);
        Pattern patternForSell = Pattern.compile(StringsRq.SELL + " " + "[\\w+ ]+");
        Matcher matcherForSell = patternForSell.matcher(command);
        Pattern patternForGameInfo = Pattern.compile(StringsRq.GAME_INFO);
        Matcher matcherForGameInfo = patternForGameInfo.matcher(command);
        Pattern patternForShowMyMinions = Pattern.compile(StringsRq.SHOW_MY_MINIONS);
        Matcher matcherForShowMyMinions = patternForShowMyMinions.matcher(command);
        Pattern patternForShowOpponentMinions = Pattern.compile(StringsRq.SHOW_OPPONENT_MINIONS);
        Matcher matcherForShowOpponentMinions = patternForShowOpponentMinions.matcher(command);
        Pattern patternForShowCardInfo = Pattern.compile(StringsRq.SHOW_CARD_INFO + " [\\w+ ]+");
        Matcher matcherForShowCardInfo = patternForShowCardInfo.matcher(command);
        Pattern patternForSelect = Pattern.compile(StringsRq.SELECT + " [\\w+ ]+");
        Matcher matcherForSelect = patternForSelect.matcher(command);
        Pattern patternForMoveTO = Pattern.compile(StringsRq.MOVE_TO + " \\(\\d,\\d\\)");
        Matcher matcherForMoveTO = patternForMoveTO.matcher(command);
        Pattern patternForAttack = Pattern.compile(StringsRq.ATTACK + " [\\w+ ]+");
        Matcher matcherForAttack = patternForAttack.matcher(command);
        Pattern patternForAttackCombo = Pattern.compile(StringsRq.ATTACK_COMBO);
        Matcher matcherForAttackCombo = patternForAttackCombo.matcher(command);
        Pattern patternForUserSpecialPower = Pattern.compile(StringsRq.USE_SPECIAL_POWER + " \\(\\d,\\d\\)");
        Matcher matcherForUserSpecialPower = patternForUserSpecialPower.matcher(command);
        Pattern patternForShowHand = Pattern.compile(StringsRq.SHOW_HAND);
        Matcher matcherForShowHand = patternForShowHand.matcher(command);
        Pattern patternForInsert = Pattern.compile(StringsRq.INSERT + " [\\w+ ]+ in \\(\\d,\\d\\)");
        Matcher matcherForInsert = patternForInsert.matcher(command);
        Pattern patternForEndTurn = Pattern.compile(StringsRq.END_TURN);
        Matcher matcherForEndTurn = patternForEndTurn.matcher(command);
        Pattern patternForShowCollectibles = Pattern.compile(StringsRq.SHOW_COLLECTIBLES);
        Matcher matcherForShowCollectibles = patternForShowCollectibles.matcher(command);
        Pattern patternForShowInfo = Pattern.compile(StringsRq.SHOW_INFO);
        Matcher matcherForShowInfo = patternForShowInfo.matcher(command);
        Pattern patternForShowInfoForGraveYard = Pattern.compile(StringsRq.SHOW_INFO + " \\w+");
        Matcher matcherForShowInfoForGraveYard = patternForShowInfoForGraveYard.matcher(command);
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
        Pattern patternForExit = Pattern.compile(StringsRq.EXIT);
        Matcher matcherForExit = patternForExit.matcher(command);
        Pattern patternForStartGame = Pattern.compile(StringsRq.START_GAME + " \\w+ \\w+");
        Matcher matcherForStartGame = patternForStartGame.matcher(command);
        Pattern patternForStartMultiPlayerGame = Pattern.compile(StringsRq.START_MULTI_PLAYER_GAME + " \\w+");
        Matcher matcherForStartMultiPlayerGame = patternForStartMultiPlayerGame.matcher(command);
        Pattern patternForSelectUser = Pattern.compile(StringsRq.SELECT_USER + " \\w+");
        Matcher matcherForSelectUser = patternForSelectUser.matcher(command);

        if (matcherForLogIn.matches()) {
            if (menuType.equals(MenuType.ACCOUNT_MENU)) {
                return RequestType.LOGIN;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForEnter.matches()) {
            if (menuType.equals(MenuType.MAIN_MENU)) {
                return RequestType.ENTER;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForCreateAccount.matches()) {
            if (menuType.equals(MenuType.ACCOUNT_MENU)) {
                return RequestType.CREATE_ACCOUNT;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowLeaderBoard.matches()) {
            if (menuType.equals(MenuType.ACCOUNT_MENU)) {
                return RequestType.SHOW_LEADER_BOARD;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForLogOut.matches()) {
            if (menuType.equals(MenuType.MAIN_MENU)) {
                return RequestType.LOGOUT;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShow.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU) || menuType.equals(MenuType.SHOP_MENU)) {
                return RequestType.SHOW;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForSearch.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU) || menuType.equals(MenuType.SHOP_MENU)) {
                return RequestType.SEARCH;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForCreateDeck.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU)) {
                return RequestType.CREATE_DECK;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForDeleteDeck.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU)) {
                return RequestType.DELETE_DECK;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForAddToDeck.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU)) {
                return RequestType.ADD_TO_DECK;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForRemoveFromDeck.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU)) {
                return RequestType.REMOVE_FROM_DECK;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForValidateDeck.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU)) {
                return RequestType.VALIDATE_DECK;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForSelectDeck.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU)) {
                return RequestType.SELECT_DECK;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowAllDecks.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU)) {
                return RequestType.SHOW_ALL_DECKS;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowDeck.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU)) {
                return RequestType.SHOW_DECK;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowForShopMenu.matches()) {
            if (menuType.equals(MenuType.SHOP_MENU)) {
                return RequestType.SHOW_fOR_SHOP_MENU;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForSearchCollection.matches()) {
            if (menuType.equals(MenuType.SHOP_MENU)) {
                return RequestType.SEARCH_COLLECTION;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForSBuy.matches()) {
            if (menuType.equals(MenuType.SHOP_MENU)) {
                return RequestType.BUY;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForSell.matches()) {
            if (menuType.equals(MenuType.SHOP_MENU)) {
                return RequestType.SELL;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForGameInfo.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.GAME_INFO;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowMyMinions.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.SHOW_MY_MINIONS;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowOpponentMinions.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.SHOW_OPPONENT_MINIONS;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowCardInfo.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.SHOW_CARD_INFO;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForSelect.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.SELECT;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForMoveTO.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.MOVE_TO;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForAttack.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.ATTACK;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForAttackCombo.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.ATTACK_COMBO;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForUserSpecialPower.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.USE_SPECIAL_POWER;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowHand.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.SHOW_HAND;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForInsert.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.INSERT;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForEndTurn.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.END_TURN;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowCollectibles.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.SHOW_COLLECTIBLES;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowInfo.matches() || matcherForShowInfoForGraveYard.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU) || menuType.equals(MenuType.GRAVE_YARD)) {
                return RequestType.SHOW_INFO;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForUse.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.USE;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowNextCard.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.SHOW_NEXT_CARD;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForEndGame.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.END_GAME;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForEnterGraveYard.matches()) {
            if (menuType.equals(MenuType.BATTLE_MENU)) {
                return RequestType.ENTER_GRAVE_YARD;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForShowCards.matches()) {
            if (menuType.equals(MenuType.GRAVE_YARD)) {
                return RequestType.SHOW_CARDS;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForSave.matches()) {
            if (menuType.equals(MenuType.COLLECTION_MENU) || menuType.equals(MenuType.ACCOUNT_MENU)) {
                return RequestType.SAVE;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForStartGame.matches()) {
            if (menuType.equals(MenuType.SINGLE_PLAYER)) {
                return RequestType.START_GAME;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForStartMultiPlayerGame.matches()) {
            if (menuType.equals(MenuType.MULTI_PLAYER)) {
                return RequestType.START_MULTI_PLAYER_GAME;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForSelectUser.matches()) {
            if (menuType.equals(MenuType.MULTI_PLAYER)) {
                return RequestType.SELECT_USER;
            }
            error = ErrorType.INVALID_INPUT;
            return null;
        } else if (matcherForHelp.matches()) {
            return RequestType.HELP;
        } else if (matcherForExit.matches()) {
            if (menuType.equals(MenuType.MAIN_MENU)) {
                return RequestType.EXIT_GAME;
            }
            return RequestType.EXIT;
        }
        error = ErrorType.INVALID_INPUT;
        return null;
    }

    private static boolean checkSyntaxForEnter() {
        Pattern patternForEnter = Pattern.compile(StringsRq.ENTER + " (?<menuName>\\w+)");
        Matcher matcher = patternForEnter.matcher(command);
        if (matcher.matches()) {
            String menuName = matcher.group("menuName");
            switch (menuName) {
                case "collection":
                    System.out.println("entered collection");
                    menuType = MenuType.COLLECTION_MENU;
                    break;
                case "shop":
                    System.out.println("entered shop");
                    menuType = MenuType.SHOP_MENU;
                    break;
                case "battle":
//                    if (!Account.getLoginUser().getPlayer().isPlayerReadyForBattle()) {
//                        System.out.println("selected deck is invalid");
//                        return true;
//                    }
                    menuType = MenuType.BATTLE_MENU;
                    BattleView.showBattleMenu();
                    checkSyntaxOfEnteringBattle();
                    if (menuType.equals(MenuType.SINGLE_PLAYER)) {
                        checkSyntaxOfGameType();
                    }
                    return true;
                case "customcard":
                    System.out.println("entered customCard");
                    menuType=MenuType.CustomCard;
                    CustomCardView.showCustomCardMenu();
                    CustomCardRequest.checkSyntaxOfCustomCard(scanner);
                    return true;
                default:
                    error = ErrorType.INVALID_INPUT;
                    break;

            }
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    private static void checkSyntaxOfEnteringBattle() {
        boolean flag = true;
        while (flag) {
            String singleOrMulti = scanner.next();
            if (singleOrMulti.equals("1")) {
                menuType = MenuType.SINGLE_PLAYER;
                BattleView.showGameStateMenu();
                flag = false;
            } else if (singleOrMulti.equals("2")) {
                menuType = MenuType.MULTI_PLAYER;
                AccountView.showLeaderBoard();
                System.out.println("Enter your Opponent user name:");
                secondPlayerUserName = scanner.next();
                Account accountOfPlayerTwo = GameController.getAccount(secondPlayerUserName);
                if (accountOfPlayerTwo == null) {
                    System.out.println("Invalid opponent username");
                    menuType = MenuType.MAIN_MENU;
                    MainMenuView.showMainMenu();
                }
                flag = false;
            } else {
                System.out.println("Please choose one or two!");
            }
        }

    }

    private static void checkSyntaxOfGameType() {//story or custom
        while (true) {
            String type = scanner.nextLine();
            while (type.equals("")) {
                type = scanner.nextLine();
            }
            if (type.equals("1") || type.equals("2")) {
                if (type.equals("1")) {
                    BattleView.showStoryMode();
                    GameController.initializeAIStory();
                    while (true) {
                        String mode = scanner.nextLine();
                        while (mode.equals("")) {
                            mode = scanner.nextLine();
                        }
                        switch (mode) {
                            case "1":
                                GameController.setAiPlayer(KH);
                                GameController.createBattleKillHeroSingle(Account.getLoginUser().getPlayer(), SinglePlayerModes.STORY);
                                menuType = MenuType.BATTLE_MENU;
                                System.out.println("You have entered the Battle,Fight!");
                                return;
                            case "2":
                                GameController.setAiPlayer(HF);
                                GameController.createBattleHoldingFlagSingle(Account.getLoginUser().getPlayer(), SinglePlayerModes.STORY);
                                menuType = MenuType.BATTLE_MENU;
                                System.out.println("You have entered the Battle,Fight!");
                                return;
                            case "3":
                                GameController.setAiPlayer(CF);
                                GameController.createBattleCaptureFlagSingle(Account.getLoginUser().getPlayer(), 7, SinglePlayerModes.STORY);
                                menuType = MenuType.BATTLE_MENU;
                                System.out.println("You have entered the Battle,Fight!");
                                return;
                            default:
                                System.out.println("Please choose only one of above modes!(Enter the number)");
                        }
                    }
                } else {
                    CollectionView.showAllDecks(Account.getLoginUser());
                    BattleView.showCustomMode();
                    return;
                }
            } else {
                System.out.println("Please choose one or two!");
            }
        }

    }

    private static boolean checkSyntaxOfStartGame() {
        Pattern patternForStartGame = Pattern.compile(StringsRq.START_GAME + " (?<deckName>\\w+) (?<mode>\\w+)");
        Matcher matcher = patternForStartGame.matcher(command);
        if (matcher.matches()) {
            if (menuType.equals(MenuType.SINGLE_PLAYER)) {
                String deckName = matcher.group("deckName");
                String mode = matcher.group("mode");
                Deck deck = Account.getLoginUser().getCollection().findDeck(deckName);
                if (deck == null) {
                    System.out.println("Such deck not found");
                    return true;
                }
                String enterBattle = "You have entered the Battle,Fight!";
                switch (mode) {
                    case "kh":
                        GameController.createNewAIInstance("AI MODE KH", deck);
                        GameController.createBattleHoldingFlagSingle(Account.getLoginUser().getPlayer(), SinglePlayerModes.CUSTOM);
                        menuType = MenuType.BATTLE_MENU;
                        System.out.println(enterBattle);
                        break;
                    case "hf":
                        GameController.createNewAIInstance("AI MODE HF", deck);
                        GameController.createBattleHoldingFlagSingle(Account.getLoginUser().getPlayer(), SinglePlayerModes.CUSTOM);
                        menuType = MenuType.BATTLE_MENU;
                        System.out.println(enterBattle);
                        break;
                    case "cf":
                        System.out.println("Enter number of flags:");
                        int numberOfFlags = scanner.nextInt();
                        GameController.createNewAIInstance("AI MODE CF", deck);
                        GameController.createBattleCaptureFlagSingle(Account.getLoginUser().getPlayer(), numberOfFlags, SinglePlayerModes.CUSTOM);
                        menuType = MenuType.BATTLE_MENU;
                        System.out.println(enterBattle);
                        break;
                    default:
                        error = ErrorType.INVALID_INPUT;
                        return false;
                }
            }
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

    private static boolean checkSyntaxOfStartMultiPlayer() {
        Pattern patternForStartMultiPlayerGame = Pattern.compile(StringsRq.START_MULTI_PLAYER_GAME + " (?<mode>[\\w+ ]+)");
        Matcher matcher = patternForStartMultiPlayerGame.matcher(command);
        if (matcher.matches()) {
            if (menuType.equals(MenuType.MULTI_PLAYER)) {
                if (secondPlayerUserName == null || secondPlayerUserName.equals("")) {
                    System.out.println("You have not selected any opponents yet!");
                    return true;
                }
                Account accountOfPlayerTwo = GameController.getAccount(secondPlayerUserName);
                if (accountOfPlayerTwo == null) {
                    System.out.println("invalid username");
                    return true;
                }

//                if (!accountOfPlayerTwo.getPlayer().isPlayerReadyForBattle()) {
//                    System.out.println("selected deck for second player is invalid,Try again");
//                    menuType = MenuType.MAIN_MENU;
//                    MainMenuView.showMainMenu();
//                    return true;
//                }
                String mode = matcher.group("mode");
                switch (mode) {
                    case "kh":
                        GameController.createBattleKillHeroMulti(Account.getLoginUser().getPlayer(), accountOfPlayerTwo.getPlayer());
                        menuType = MenuType.BATTLE_MENU;
                        System.out.println("You have entered the Battle,Fight!");
                        break;
                    case "hf":
                        GameController.createBattleHoldingFlagMulti(Account.getLoginUser().getPlayer(), accountOfPlayerTwo.getPlayer());
                        menuType = MenuType.BATTLE_MENU;
                        System.out.println("You have entered the Battle,Fight!");
                        break;
                    case "cf":
                        System.out.println("Enter number of flags:");
                        int numberOfFlags = scanner.nextInt();
                        GameController.createBattleCaptureFlagMulti(Account.getLoginUser().getPlayer(), accountOfPlayerTwo.getPlayer(), numberOfFlags);
                        menuType = MenuType.BATTLE_MENU;
                        System.out.println("You have entered the Battle,Fight!");
                        break;
                    default:
                        error = ErrorType.INVALID_INPUT;
                        return false;
                }
            }
        } else {
            error = ErrorType.INVALID_INPUT;
            return false;
        }
        return true;
    }

}
