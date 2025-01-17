package resources;

import java.util.ArrayList;

public class Hand {
    ArrayList<Card> cards;


    public Hand() {
        cards = new ArrayList<>();
    }
    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(int index) {
        if(index >= 0 && index < cards.size()) {
            Card card = cards.remove(index);
            System.out.println(card);
            return card;
        }
        else
        {
            return null;
        }
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void printHand() {
        if(cards != null) {
            for (Card card : cards) {
                System.out.println(card);
            }
        }
        System.out.println("\n");
    }


}
