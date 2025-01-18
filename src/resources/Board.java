package resources;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Card>> cards;
    private int maxColums = 5;


    public Board() {

        cards = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            ArrayList<Card> row = new ArrayList<>(5);
            cards.add(row);
        }
    }

    public void addCardToBoard(Card card, int playerNumber){
        if (playerNumber == 1) {
            /// got the row for the first player (back row - row 3)
            ArrayList<Card> row = cards.get(3);

            if(row.isEmpty()){
                row.add(card);
            }
        }
        else
        {
            /// got the row for the second player (back row - row 0)
            ArrayList<Card> row = cards.get(0);
            if (row.isEmpty()) {
                row.add(card);
            }

//            else
//            {
//                // TODO IF THE ROW IS FULL
//            }
        }
    }

    public ArrayList<ArrayList<Card>> getCards() {
        return cards;
    }

    public void setCards(ArrayList<ArrayList<Card>> cards) {
        this.cards = cards;
    }

    public int getMaxColums() {
        return maxColums;
    }

    public void setMaxColums(final int maxColums) {
        this.maxColums = maxColums;
    }
}
