package resources;


import fileio.CardInput;

import java.util.ArrayList;

public class Decks {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<Card>> decks;

    public Decks(final int nrCardsInDeck, final int nrDecks, final ArrayList<ArrayList<CardInput>> decks) {

        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;

        int numberOfDecks = decks.size();
        this.decks = new ArrayList<ArrayList<Card>>(numberOfDecks);

        for (int i = 0; i < numberOfDecks; i++) {
            ArrayList<Card> listOfCards = new ArrayList<>();
            this.decks.add(listOfCards);
            for (CardInput card: decks.get(i)) {
//                this.decks.get(i).add(new Card(card));
                this.decks.get(i).add(Card.createCard(card));
            }
        }
    }


    public Decks(final Decks dCopy) {
        nrCardsInDeck = dCopy.nrCardsInDeck;
        nrDecks = dCopy.nrDecks;
        ArrayList<ArrayList<Card>> originalDecks = dCopy.getDecks();
        decks = new ArrayList<ArrayList<Card>>();
        // iterate through decks
        for (int i = 0; i < dCopy.getNrDecks(); i++) {
            // get the i-th deck and create new deck object
            ArrayList<Card> originalDeck = originalDecks.get(i);
            ArrayList<Card> deck = new ArrayList<>();
            decks.add(deck);
            // iterate through cards in current deck
            for (int j = 0; j < dCopy.nrCardsInDeck; j++) {
                // add current card
                deck.add(new Card(originalDeck.get(j)));
            }
        }
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    /**
     * Function that returns a deck by its id
     * @param idx
     * @return the deck corresponding the idx
     */
    public ArrayList<Card> getDeck(final int idx) {
        return decks.get(idx);
    }

    public void setDecks(final ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    @Override
    public String toString() {
        return "InfoInput{"
                + "nr_cards_in_deck="
                + nrCardsInDeck
                +  ", nr_decks="
                + nrDecks
                + ", decks="
                + decks
                + '}';
    }
}
