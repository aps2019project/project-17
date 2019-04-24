package CardCollections;

import effects.Cards;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Cards> cards;

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public void addCard(Cards card) {
        cards.add(card);
    }

    public void removeCard(Cards card) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(card.getName())) {
                cards.remove(i);
                return;
            }
        }
    }

}
