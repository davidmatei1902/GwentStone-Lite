package resources;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Card>> cards;

    private static final int MAX_COLUMNS = 5;
    private static final int MAX_ROWS = 4;

    private static final int PLAYER_ONE_NUMBER = 1;
    private static final int PLAYER_TWO_NUMBER = 2;

    private static final int PLAYER_ONE_FRONT_ROW = 2;
    private static final int PLAYER_TWO_FRONT_ROW = 1;



    public Board() {
        cards = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            ArrayList<Card> row = new ArrayList<>(MAX_COLUMNS);
            cards.add(row);
        }
    }

    //TODO RETURNS THE FIRST TANK THAT IS ON THAT ROW, SHOULD BE LIKE THIS ???
    // this function should be called with row 1 for player 2 and row 2 for player 1
    public Card findTankOnRow(final int playerRow) {
        ArrayList<Card> row = cards.get(playerRow);
        for (Card card : row) {
            if (card.isTank()) {
                return card;
            }
        }

        return null;
    }

    // are there more than 1 tank on a row?
    public final boolean checkForTank(final int playerNum) {
        boolean isATank = false;
        if(playerNum == PLAYER_ONE_NUMBER) {
            ArrayList<Card> row = cards.get(PLAYER_ONE_FRONT_ROW);

            for(Card card : row) {
                if(card.isTank()) {
                    isATank = true;
                    break;
                }
            }
            return isATank;
        }
        else if(playerNum == PLAYER_TWO_NUMBER) {
            ArrayList<Card> row = cards.get(PLAYER_TWO_FRONT_ROW);

            for(Card card : row) {
                if(card.isTank()) {
                    isATank = true;
                    break;
                }
            }
            return isATank;
        }
        return isATank;
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
