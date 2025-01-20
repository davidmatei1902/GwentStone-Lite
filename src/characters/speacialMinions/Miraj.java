package characters.speacialMinions;

import fileio.CardInput;
import characters.row.FrontRow;
import fileio.Coordinates;
import resources.Board;
import resources.Card;

public class Miraj extends FrontRow {
    private final String specialAbilityName = "Skyjack";

    public Miraj(final Card card) {
        super(card);
    }
    public Miraj(final CardInput card) {
        super(card);
    }

    public void useSpecialAbility(final Card card, final Coordinates attackerPos, final Coordinates attackedPos, final Board board) {

        // swap the healths
        int tmp = card.getHealth();
        card.setHealth(this.getHealth());
        this.setHealth(tmp);
    }
}
