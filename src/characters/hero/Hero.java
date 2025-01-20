package characters.hero;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import resources.Card;

public class Hero extends Card {
    private static final int MAX_HEALTH = 30;

    public Hero(Card card) {
        super(card);
    }

    public ObjectNode createOutputWrapper() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNodeCard = mapper.createObjectNode();
        objectNodeCard.put("mana", this.getMana());
        objectNodeCard.put("description", this.getDescription());

        ArrayNode arrayNodeColors = mapper.createArrayNode();
        for (String color : this.getColors()) {
            arrayNodeColors.add(color);
        }
        objectNodeCard.set("colors", arrayNodeColors);
        objectNodeCard.put("name", this.getName());
        objectNodeCard.put("health", this.getHealth());
        return objectNodeCard;
    }

    public static int getMaxHealth() {
        return MAX_HEALTH;
    }

}
