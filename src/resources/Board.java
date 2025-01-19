package resources;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Card>> cards;

    private static final int MAX_COLUMNS = 5;
    private static final int MAX_ROWS = 4;


    public Board() {

        cards = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            ArrayList<Card> row = new ArrayList<>(MAX_COLUMNS);
            cards.add(row);
        }
    }

    public ArrayList<ArrayList<Card>> getCards() {
        return cards;
    }

    public void setCards(final ArrayList<ArrayList<Card>> cards) {
        this.cards = cards;
    }

    public int getMaxColums() {
        return MAX_COLUMNS;
    }
}
