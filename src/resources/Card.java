package resources;

import com.sun.source.tree.AnnotatedTypeTree;
import fileio.CardInput;
import minions.normalMinions.Berserker;
import minions.normalMinions.Goliath;
import minions.normalMinions.Sentinel;
import minions.normalMinions.Warden;
import minions.speacialMinions.Disciple;
import minions.speacialMinions.Miraj;
import minions.speacialMinions.TheCursedOne;
import minions.speacialMinions.TheRipper;

import gameplay.Player;

import java.util.ArrayList;

public class Card {
        private int mana;
        private int attackDamage;
        private int health;
        private String description;
        private ArrayList<String> colors;
        private String name;

        private boolean isFrozen = false;

    public boolean getFrozenStatus() {
        return isFrozen;
    }

    public void setFrozenStatus(boolean frozen) {
        isFrozen = frozen;
    }

    public Card()
    {
        mana = 0;
        attackDamage = 0;
        health = 0;
        description = "";
        colors = new ArrayList<>();
        name = "";
    }

    public Card(Card card) {
        this.mana = card.mana;
        this.attackDamage = card.attackDamage;
        this.health = card.health;
        this.description = card.description;
        this.colors = card.colors;
        this.name = card.name;
    }

    public static Card createCard(CardInput cardInput) {
        switch (cardInput.getName()) {
            case "Warden":
                return new Warden(cardInput);
            case "Goliath":
                return new Goliath(cardInput);
            case "Sentinel":
                return new Sentinel(cardInput);
            case "Berserker":
                return new Berserker(cardInput);

            case "Disciple":
                return new Disciple(cardInput);
            case "Miraj":
                return new Miraj(cardInput);
            case "The Cursed One":
                return new TheCursedOne(cardInput);
            case "The Ripper":
                return new TheRipper(cardInput);
            default:
                System.out.println("Unknown card type: " + cardInput.getName());
                return null;  // Return null or a default card type
        }
    }

    public static Card createCard(Card card) {
        switch (card.getName()) {
            case "Warden":
                return new Warden(card);
            case "Goliath":
                return new Goliath(card);
            case "Sentinel":
                return new Sentinel(card);
            case "Berserker":
                return new Berserker(card);

            case "Disciple":
                return new Disciple(card);
            case "Miraj":
                return new Miraj(card);
            case "The Cursed One":
                return new TheCursedOne(card);
            case "The Ripper":
                return new TheRipper(card);

            default:
                System.out.println("Unknown card type: " + card.getName());
                return null;  // Return null or a default card type
        }
    }

    public Card(CardInput cardInput) {
        this.mana = cardInput.getMana();
        this.attackDamage = cardInput.getAttackDamage();
        this.health = cardInput.getHealth();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
    }

    public void addToBoard(Board board, Player player) {
    }
    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMana() {
        return mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attackDamage
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                +'}';
    }
}
