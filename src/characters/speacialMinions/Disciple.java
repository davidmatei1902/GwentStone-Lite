package characters.speacialMinions;

import fileio.CardInput;
import characters.row.BackRow;
import fileio.Coordinates;
import resources.Board;
import resources.Card;

public class Disciple extends BackRow {
    private final int SPECIAL_ABILITY_HEALTH_INCREASE = 2;
    private final String specialAbilityName = "God's Plan";

    public Disciple(final Card card) {
        super(card);
    }
    public Disciple(final CardInput card) {
        super(card);
    }

    // should pass as arg an allied minion
    public void useSpecialAbility(final Card card, final Coordinates attackerPos, final Coordinates attackedPos, final Board board) {
        card.increaseHealthBy(SPECIAL_ABILITY_HEALTH_INCREASE);
    }
}
