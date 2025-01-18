package minions.speacialMinions;

import fileio.CardInput;
import gameplay.Player;
import resources.Board;
import resources.Card;

import java.util.ArrayList;

public class TheRipper extends Card {
    String placingRow = "front";

    public TheRipper(Card card) {
        super(card);
    }
    public TheRipper(CardInput card) {
        super(card);
    }

    @Override
    public void addToBoard(Board board, Player player) {
        ArrayList<ArrayList<Card>> cards = board.getCards();

        int playerNumber = player.getPlayerNumber();
        if (playerNumber == 1) {
            /// got the front row for the first player (row 2)
            ArrayList<Card> row = cards.get(2);
            if(row.size() < board.getMaxColums()){
                row.add(this);
                player.decreaseManaBy(this.getMana());
            }
        }
        else
        {
            /// got the front row for the second player (row 1)
            ArrayList<Card> row = cards.get(1);
            if (row.size() < board.getMaxColums()) {
                row.add(this);
                player.decreaseManaBy(this.getMana());
            }
        }
    }
}
