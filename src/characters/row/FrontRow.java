package characters.row;

import fileio.CardInput;
import gameplay.Player;
import resources.Board;
import resources.Card;

import java.util.ArrayList;

public class FrontRow extends Card {
    private static final int FRONT_ROW_PLAYER_ONE = 2;
    private static final int FRONT_ROW_PLAYER_TWO = 1;

    public FrontRow(final Card card) {
        super(card);
    }

    public FrontRow(final CardInput cardInput) {
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
            /// got the front row for the first player (row 2)
            ArrayList<Card> row = cards.get(FRONT_ROW_PLAYER_ONE);
            if (row.size() < board.getMaxColums()) {
                row.add(this);
                player.decreaseManaBy(this.getMana());
            }
        } else {
            /// got the front  row for the second player (row 1)
            ArrayList<Card> row = cards.get(FRONT_ROW_PLAYER_TWO);
            if (row.size() < board.getMaxColums()) {
                row.add(this);
                player.decreaseManaBy(this.getMana());
            }
        }
    }
}
