package resources;

import characters.hero.Hero;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import characters.normalMinions.Berserker;
import characters.normalMinions.Goliath;
import characters.normalMinions.Sentinel;
import characters.normalMinions.Warden;
import characters.speacialMinions.Disciple;
import characters.speacialMinions.Miraj;
import characters.speacialMinions.TheCursedOne;
import characters.speacialMinions.TheRipper;

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
        private boolean hasAttacked = false;

    public boolean getFrozenStatus() {
        return isFrozen;
    }

    public void setFrozenStatus(final boolean frozen) {
        isFrozen = frozen;
    }

    public Card() {
        mana = 0;
        attackDamage = 0;
        health = 0;
        description = "";
        colors = new ArrayList<>();
        name = "";
    }

    public Card(final Card card) {
        this.mana = card.mana;
        this.attackDamage = card.attackDamage;
        this.health = card.health;
        this.description = card.description;
        this.colors = card.colors;
        this.name = card.name;
    }

    public static Hero createHero(final Card card) {
        return new Hero(card);
    }

    public ObjectNode createOutputWrapper() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNodeCard = mapper.createObjectNode();
        objectNodeCard.put("mana", this.getMana());
        objectNodeCard.put("attackDamage", this.getAttackDamage());
        objectNodeCard.put("health", this.getHealth());
        objectNodeCard.put("description", this.getDescription());

        ArrayNode arrayNodeColors = mapper.createArrayNode();
        for (String color : this.getColors()) {
            arrayNodeColors.add(color);
        }
        objectNodeCard.set("colors", arrayNodeColors);
        objectNodeCard.put("name", this.getName());
        return objectNodeCard;
    }


    /**
     * Function that creates a Card given by the name
     * @param cardInput
     * @return A card based of its name
     */
    public static Card createCard(final CardInput cardInput) {
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

    /**
     * Function that creates a Card given by the name
     * @param card
     * @return A card based of its name
     */
    public static Card createCard(final Card card) {
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
                return null;
        }
    }

    public Card(final CardInput cardInput) {
        this.mana = cardInput.getMana();
        this.attackDamage = cardInput.getAttackDamage();
        this.health = cardInput.getHealth();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
    }

    /**
     * Implementation of a function that add a Card to the board
     * @param board
     * @param player
     */
    public void addToBoard(final Board board, final Player player) {
    }
    public void setMana(final int mana) {
        this.mana = mana;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public void setName(final String name) {
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
                + '}';
    }
}
