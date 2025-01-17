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
//        // Check the structure of the matrix
//        System.out.println("Matrix size (rows): " + cards.size());
//        for (int i = 0; i < cards.size(); i++) {
//            System.out.println("Row " + (i + 1) + " size (columns): " + cards.get(i).size());
//        }
    }

    public void addCardToBoard(Card card, int playerNumber){
        if (playerNumber == 1){
            /// got the row for the first player (back row)
            ArrayList<Card> row = cards.get(3);

            if(row.isEmpty()){
                row.add(card);
            }

//            else {
//                // TODO IF THE ROW IS
//            }
        }
        else
        {
            /// got the row for the second player (front row)
            ArrayList<Card> row = cards.get(0);
            if(row.isEmpty()){
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

    public void setMaxColums(int maxColums) {
        this.maxColums = maxColums;
    }
}
