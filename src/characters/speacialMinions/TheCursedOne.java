package characters.speacialMinions;

import fileio.CardInput;
import characters.row.BackRow;
import fileio.Coordinates;
import resources.Board;
import resources.Card;

public class TheCursedOne extends BackRow {
    private final String specialAbilityName = "Shapeshift";

    public TheCursedOne(final Card card) {
        super(card);
    }
    public TheCursedOne(final CardInput card) {
        super(card);
    }

    // swap attack and health of the card
    public void useSpecialAbility(final Card card, final Coordinates attackerPos, final Coordinates attackedPos, final Board board) {
        int tmp = card.getHealth();
        card.setHealth(card.getAttackDamage());
        card.setAttackDamage(tmp);

        if(card.getHealth() <= 0) {
            board.eliminateCardFromPos(attackerPos.getX(), attackerPos.getY());
        }
    }
}
