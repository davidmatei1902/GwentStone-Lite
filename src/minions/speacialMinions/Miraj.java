package minions.speacialMinions;

import fileio.CardInput;
import resources.Board;
import resources.Card;
import gameplay.Player;

import java.util.ArrayList;

public class Miraj extends Card {
    String placingRow = "front";

    public Miraj(Card card) {
        super(card);
    }
    public Miraj(CardInput card) {
        super(card);
    }

    @Override
    public void addToBoard(Board board,Player player) {
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
