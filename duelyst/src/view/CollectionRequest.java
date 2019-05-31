package view;

import Data.Account;
import controller.GameController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CollectionRequest {

    static boolean checkSyntaxOfCreateDeck(String command) {
        Pattern patternForSCreateDeck = Pattern.compile(StringsRq.CREATE_DECK + " (?<name>[\\w+ ]+)");
        Matcher matcher = patternForSCreateDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.createDeck(name, Account.getLoginUser().getCollection());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfDeleteDeck(String command) {
        Pattern patternForDeleteDeck = Pattern.compile(StringsRq.DELETE_DECK + " (?<name>[\\w+ ]+)");
        Matcher matcher = patternForDeleteDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.deleteDeck(name, Account.getLoginUser().getCollection());

            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfAddToDeck(String command) {

        Pattern patternForAddToDeck = Pattern.compile(StringsRq.ADD_TO_DECK + " (?<cardId>[\\w+ ]+) to deck (?<deckName>[\\w+ ]+)");
        Matcher matcher = patternForAddToDeck.matcher(command);
        if (matcher.matches()) {
            String cardId = matcher.group("cardId");
            String deckName = matcher.group("deckName");
            String result = GameController.addToDeck(cardId, deckName, Account.getLoginUser().getCollection());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfRemoveFromDeck(String command) {
        Pattern patternForRemoveFromDeck = Pattern.compile(StringsRq.REMOVE_FROM_DECK + " (?<cardId>[\\w+ ]+) from deck (?<deckName>[\\w+ ]+)");
        Matcher matcher = patternForRemoveFromDeck.matcher(command);
        if (matcher.matches()) {
            String cardId = matcher.group("cardId");
            String deckName = matcher.group("deckName");
            String result = GameController.removeFromDeck(cardId, deckName, Account.getLoginUser().getCollection());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfValidateDeck(String command) {
        Pattern patternForValidateDeck = Pattern.compile(StringsRq.VALIDATE_DECK + " (?<name>[\\w+ ]+)");
        Matcher matcher = patternForValidateDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.isDeckValidate(name, Account.getLoginUser().getCollection());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfSelectDeck(String command) {
        Pattern patternForSelectDeck = Pattern.compile(StringsRq.SELECT_DECK + " (?<name>[\\w+ ]+)");
        Matcher matcher = patternForSelectDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            String result = GameController.setMainDeck(name, Account.getLoginUser().getCollection());
            System.out.println(result);
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfShowAllDecks(String command) {
        Pattern patternForShowAllDecks = Pattern.compile(StringsRq.SHOW_ALL_DECKS);
        Matcher matcher = patternForShowAllDecks.matcher(command);
        if (matcher.matches()) {
            CollectionView.showAllDecks(Account.getLoginUser());
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

    static boolean checkSyntaxOfShowDeck(String command) {
        Pattern patternForShowDeck = Pattern.compile(StringsRq.SHOW_DECK + " (?<name>[\\w+ ]+)");
        Matcher matcher = patternForShowDeck.matcher(command);
        if (matcher.matches()) {
            String name = matcher.group("name");
            CollectionView.showDeck(name, Account.getLoginUser().getCollection());
        } else {
            Request.changeErrorType();
            return false;
        }
        return true;
    }

}
