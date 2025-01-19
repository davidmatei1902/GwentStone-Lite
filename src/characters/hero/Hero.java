package characters.hero;

import resources.Card;

public class Hero extends Card {
    private static final int MAX_HEALTH = 30;
    public Hero(Card card) {
        super(card);
    }

    public static int getMaxHealth() {
        return MAX_HEALTH;
    }

}
