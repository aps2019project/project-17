package CardCollections;

import effects.Cards;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Cards> cards;
    private ArrayList<Item> items;

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    private Item findItem(String cardName) {
        for (Item item : items) {
            if (item.getName().equals(cardName))
                return item;
        }
        return null;
    }

    private Cards findCard(String cardName) {
        for (Cards card : cards) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    public String search(String cardName) {
        if (findCard(cardName) != null)
            return findCard(cardName).getId();

        if (findItem(cardName) != null)
            return Integer.toString(findItem(cardName).getItemID());

        return "card didn't find";
    }

    public void save(){}

}
