package resources;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;


    public Hand() {
        cards = new ArrayList<>();
    }
    public ArrayList<Card> getCards() {
        return cards;
    }


    /**
     * Function that search for a card in an ArrayList by given index
     * @param index
     * @return Card that exists at that index
     */
    public Card getCard(final int index) {
        if (index >= 0 && index < cards.size()) {
            Card card = cards.get(index);
            return card;
        } else {
            return null;
        }
    }

    public void removeCardAtIndex(final int index) {
        if (index >= 0 && index < cards.size()) {
            Card card = cards.remove(index);
        }
    }


    /**
     * Function that add given Card into Player Hand
     * @param card
     */
    public void addCard(final Card card) {
        cards.add(card);
    }

    public void setCards(final ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Function that prints the player hand
     */
    public void printHand() {
        if (cards != null) {
            for (Card card : cards) {
                System.out.println(card);
            }
        }
        System.out.println("\n");
    }


}
