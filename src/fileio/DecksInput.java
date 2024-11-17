package fileio;

import javax.smartcardio.Card;
import java.util.ArrayList;

public final class DecksInput {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<CardInput>> decks;

    public DecksInput(DecksInput dCopy) {
        nrCardsInDeck = dCopy.nrCardsInDeck;
        nrDecks = dCopy.nrDecks;
        ArrayList<ArrayList<CardInput>> originalDecks = dCopy.getDecks();
        decks = new ArrayList<ArrayList<CardInput>>();
        // iterate through decks
        for(int i = 0; i < dCopy.getNrDecks(); i++) {
            // get the i-th deck and create new deck object
            ArrayList<CardInput> originalDeck = originalDecks.get(i);
            ArrayList<CardInput> deck = new ArrayList<>();
            decks.add(deck);
            // iterate through cards in current deck
            for(int j = 0; j < dCopy.nrCardsInDeck; j++)
                // add current card
                deck.add(new CardInput(originalDeck.get(j)));
        }
    }
    public DecksInput() {
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public CardInput getCardFromDeck(int deckIndex) {
        return decks.get(deckIndex).get(0);
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
    public ArrayList<CardInput> getDeck(int idx) {
        return decks.get(idx);
    }
    public ArrayList<ArrayList<CardInput>> getDecks() {
        return decks;
    }

    public void setDecks(final ArrayList<ArrayList<CardInput>> decks) {
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
              +'}';
    }
}