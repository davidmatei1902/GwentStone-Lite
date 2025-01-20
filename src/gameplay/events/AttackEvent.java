package gameplay.events;

import resources.Card;

public class AttackEvent {
    private final Card attacker;
    private final Card attacked;

    public AttackEvent(Card attacker, Card attacked) {
        this.attacker = attacker;
        this.attacked = attacked;
    }
}
