package minions.speacialMinions;

import fileio.CardInput;
import gameplay.Player;
import resources.Board;
import resources.Card;
import gameplay.Player;

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
    public void addToBoard(Board board, Player player) {
        ArrayList<ArrayList<Card>> cards = board.getCards();

        int playerNumber = player.getPlayerNumber();
        if (playerNumber == 1) {
            /// got the  back row for the first player (row 3)
            ArrayList<Card> row = cards.get(3);
            if(row.size() < board.getMaxColums()){
                row.add(this);
                player.decreaseManaBy(this.getMana());
            }
        }
        else
        {
            /// got the back row for the second player (row 0)
            ArrayList<Card> row = cards.get(0);
            if (row.size() < board.getMaxColums()) {
                row.add(this);
                player.decreaseManaBy(this.getMana());
            }
        }
    }
}
