package gameplay.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;
import resources.Board;
import resources.Card;
import resources.Round;

public class EventsHandler {

    private AttackEvent attackEvent;

    private Board board = new Board();
    private Round round = new Round();

    private ObjectMapper mapper = new ObjectMapper();

    public Board getBoard() {
        return board;
    }

    public Round getRound() {
        return round;
    }



    public ObjectNode handleAttack(final Coordinates attackerPos, final Coordinates attackedPos) {
        ObjectNode objectNode = mapper.createObjectNode();
        Card attacker = board.getAtPos(attackerPos.getX(), attackerPos.getY());
        Card attacked = board.getAtPos(attackedPos.getX(), attackedPos.getY());

        this.attackEvent = new AttackEvent(attacker, attacked);

        // handle the attackEvent
        objectNode.put("command","cardUsesAttack");
        ObjectNode cardAttackerNode = mapper.createObjectNode();
        cardAttackerNode.put("x",attackerPos.getX());
        cardAttackerNode.put("y",attackerPos.getY());
        objectNode.set("cardAttacker", cardAttackerNode);

        ObjectNode cardAttackedNode = mapper.createObjectNode();
        cardAttackedNode.put("x", attackedPos.getX());
        cardAttackedNode.put("y", attackedPos.getY());
        objectNode.set("cardAttacked", cardAttackedNode);

        // handle if attacker is player one / two and the attacked is also player one / two
        if(attackerPos.isPlayerOneSpace() && attackedPos.isPlayerOneSpace())
        {
            objectNode.put("error", "Attacked card does not belong to the enemy.");
            return objectNode;
        }

        if(attackerPos.isPlayerTwoSpace() && attackedPos.isPlayerTwoSpace()) {
            objectNode.put("error", "Attacked card does not belong to the enemy.");
            return objectNode;
        }

        // handle the usage of special ability in that round
        if(attacker.HasAttacked()) {
            objectNode.put("error", "Attacker card has already attacked this turn.");
            return objectNode;
        }

        if(attacker.isFrozen()) {
            objectNode.put("error", "Attacker card is frozen.");
            return objectNode;
        }

        // TODO IMPLEMENT THE TANK CARD AND TANK HANDLING OF ATTACK
        // HERE


        // set attackEvent to null
        this.attackEvent = null;
        return objectNode;
    }
}
