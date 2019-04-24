package CardCollections;

import effects.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(card.getName())) {
                cards.remove(i);
                return;
            }
        }
    }

}
