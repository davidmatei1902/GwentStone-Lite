package gameplay.events;

import fileio.Coordinates;
import resources.Card;
import resources.Board;

public class AttackEvent {
    private final Card attacker;
    private final Card attacked;

    public AttackEvent(final Card attacker, final Card attacked) {
        this.attacker = attacker;
        this.attacked = attacked;
    }


    public void handleAttack(final Board board, final Card attacker, final Card attacked, final Coordinates attackerPos, final Coordinates attackedPos) {
        int attackerDamage = attacker.getAttackDamage();

        attacked.decreaseHealthBy(attackerDamage);
        if(attacked.getHealth() < 0) {

            // should be here or not
            attacked.setHealth(0);

            board.eliminateCardFromPos(attackedPos.getX(), attackedPos.getY());
        }
    }
}
