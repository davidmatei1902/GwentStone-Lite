package gameplay.events;

import resources.Card;

public class EventsHandler {
    private AttackEvent attackEvent;

    public void handleAttack(final Card attacker, final Card attacked) {
        this.attackEvent = new AttackEvent(attacker, attacked);

        // handle the attackEvent
        // HOW DO I CHECK IF THE ATTACKED CARD IS FROM PLAYER 1 OR PLAYER 2 ????


        this.attackEvent = null;
    }
}
