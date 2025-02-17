package characters.row;

import fileio.CardInput;
import gameplay.Player;
import resources.Board;
import resources.Card;

import java.util.ArrayList;

public class BackRow extends Card {
    private static final int BACK_ROW_PLAYER_ONE = 3;
    private static final int BACK_ROW_PLAYER_TWO = 0;

    public BackRow(final Card card) {
        super(card);
    }

    public BackRow(final CardInput cardInput) {
        super(cardInput);
    }


    /**
     * Function that adds a card in the front row
     * @param board
     * @param player
     */
    @Override
    public void addToBoard(final Board board, final Player player) {
        ArrayList<ArrayList<Card>> cards = board.getCards();

        int playerNumber = player.getPlayerNumber();
        if (playerNumber == 1) {
            /// got the back row for the first player (row 3)
            ArrayList<Card> row = cards.get(BACK_ROW_PLAYER_ONE);
            if (row.size() < board.getMaxColums()) {
                row.add(this);
                player.decreaseManaBy(this.getMana());
            }
        } else {
            /// got the back row for the second player (row 0)
            ArrayList<Card> row = cards.get(BACK_ROW_PLAYER_TWO);
            if (row.size() < board.getMaxColums()) {
                row.add(this);
                player.decreaseManaBy(this.getMana());
            }
        }
    }
}
