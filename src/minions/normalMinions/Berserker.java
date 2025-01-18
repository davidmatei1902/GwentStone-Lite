package minions.normalMinions;

import resources.Board;
import resources.Card;
import fileio.CardInput;

import java.util.ArrayList;

public class Berserker extends Card {
    String placingRow = "back";

    public Berserker(CardInput card) {
        super(card);
    }

    public Berserker(Card card) {
        super(card);
    }

    @Override
    public void addToBoard(Board board, int playerNumber) {
        ArrayList<ArrayList<Card>> cards = board.getCards();

        if (playerNumber == 1) {
            /// got the back row for the first player (row 3)
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
