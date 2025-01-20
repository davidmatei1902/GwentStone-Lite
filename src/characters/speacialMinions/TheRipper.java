package characters.speacialMinions;

import fileio.CardInput;
import characters.row.FrontRow;
import fileio.Coordinates;
import resources.Board;
import resources.Card;

public class TheRipper extends FrontRow {
    private final String specialAbilityName = "Weak Knees";

    public TheRipper(final Card card) {
        super(card);
    }
    public TheRipper(final CardInput card) {
        super(card);
    }


    // DECREASE ATTACK DAMAGE OF ENEMY BY TWO. IF THE ATTACK IS BELOW TWO, SET IT TO ZERO
    public void useSpecialAbility(final Card card, final Coordinates attackerPos, final Coordinates attackedPos, final Board board) {
        card.setAttackDamage(card.getAttackDamage() - 2);
        if (card.getAttackDamage() < 2) {
            card.setAttackDamage(0);
        }
    }
}
