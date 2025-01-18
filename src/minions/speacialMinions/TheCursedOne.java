package minions.speacialMinions;

import fileio.CardInput;
import resources.Board;
import resources.Card;

import java.util.ArrayList;

public class TheCursedOne extends Card {
    String placingRow = "back";

    public TheCursedOne(Card card) {
        super(card);
    }
    public TheCursedOne(CardInput card) {
        super(card);
    }
    @Override
    public void addToBoard(Board board, int playerNumber) {
        ArrayList<ArrayList<Card>> cards = board.getCards();

        if (playerNumber == 1) {
            /// got the  back row for the first player (row 3)
            ArrayList<Card> row = cards.get(3);
            if(row.isEmpty()){
                row.add(this);
            }
        }
        else
        {
            /// got the back row for the second player (row 0)
            ArrayList<Card> row = cards.get(0);
            if (row.isEmpty()) {
                row.add(this);
            }
        }
    }
}
