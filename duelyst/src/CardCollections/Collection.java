package CardCollections;
import effects.*;
import java.util.ArrayList;

public class Collection {
    private ArrayList<Cards> cards;
    private ArrayList<Item> items;
    private static ArrayList<Deck> decks = new ArrayList<>();

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String search(String name){
        if(findCardByName(name)!=null){
            return findCardByName(name).getId();
        }else if(findItemByName(name)!=null){
            return findItemByName(name).getId();
        }else{
            return null;
        }
    }

    private Cards findCardByName(String cardName){
        for(int i=0;i<cards.size();i++){
            if(cards.get(i).getName().equals(cardName)){
                return cards.get(i);
            }
        }
        return null;
    }

    private Item findItemByName(String itemName){
        for(int i=0;i<items.size();i++){
            if(items.get(i).getName().equals(itemName)){
                return items.get(i);
            }
        }
        return null;
    }

    private Cards findCardByID(String cardID){
        for(int i=0;i<cards.size();i++){
            if(cards.get(i).getId().equals(cardID)){
                return cards.get(i);
            }
        }
        return null;
    }

    private Item findItemByID(String itemID){
        for(int i=0;i<items.size();i++){
            if(items.get(i).getId().equals(itemID)){
                return items.get(i);
            }
        }
        return null;
    }

    public String createDeck(String deckName){
        Deck deck=new Deck(deckName);
        for(Deck deck1:decks){
            if(deck1.getName().equals(deckName)){
                return "Deck Already Exist! Please Try again with another DeckName.";
            }
        }
        decks.add(deck);
        return "Deck Successfully created";
    }

    public Deck findDeck(String deckName){
        for (Deck deck:decks) {
            if(deck.getName().equals(deckName)){
                return deck;
            }
        }
        return null;
    }

    public void deleteDeck(String deckName){
        Deck garbageDeck=findDeck(deckName);
        if(garbageDeck!=null){
            decks.remove(garbageDeck);
            return;
        }
        return;
    }

    public boolean isCardInDeck(String cardID,String deckName){
        if(findDeck(deckName)==null || findCardByID(cardID)==null){
            return false;
        }
        if(findDeck(deckName).getCards().contains(findCardByID(cardID))){
            return true;
        }
        return false;
    }

    public boolean isItemInDeck(String itemID,String deckName){
        if(findDeck(deckName)==null || findItemByID(itemID)==null){
            return false;
        }
        if(findDeck(deckName).getItems().contains(findItemByID(itemID))){
            return true;
        }
        return false;
    }

    public String addToDeck(String ID,String deckName){
        if(findCardByID(ID)==null || findItemByID(ID)==null){
            return "No such Card/Item ID found in collection";
        }
        if(!isCardInDeck(ID, deckName) || !isItemInDeck(ID, deckName)){
            return "This Card/Item ID doesn't exist in this deck!";
        }
        if(findDeck(deckName).getNumberOfDeckCards()+1>20){
            return "The Deck card storage is full.";
        }
        if(findDeck(deckName).getHero()!=null ){
            return "This deck has hero";
        }else{
            findDeck(deckName).setHero((Hero) findCardByID(ID));
            return "Hero added succesfully";
        }
//        if(findCardByID(ID)!=null){
//            findDeck(deckName).getCards().add(findCardByID(ID));
//        }
    }
}
