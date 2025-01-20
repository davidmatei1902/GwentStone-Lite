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

    public Card getAtPos(final int x, final int y) {
        if (x < 0 || x >= cards.size()) {
            return null;
        }
        ArrayList<Card> row = cards.get(x);
        if (y < 0 || y >= row.size()) {
            return null;
        }

        return cards.get(x).get(y);
    }

    public void eliminateCardFromPos(final int x, final int y) {
        if (x < 0 || x >= cards.size()) {
            return;
        }
        ArrayList<Card> row = cards.get(x);
        if (y < 0 || y >= row.size()) {
            return;
        }
        row.remove(y);
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
