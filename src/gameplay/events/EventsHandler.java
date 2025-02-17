package gameplay.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;
import resources.Board;
import resources.Card;
import resources.Round;

public class EventsHandler {

    private AttackEvent attackEvent;

    //TODO - THE GOOD BOARD
    private Board board = new Board();


    private Round round = new Round();

    private ObjectMapper mapper = new ObjectMapper();

    private static final int PLAYER_ONE_FRONT_ROW = 2;
    private static final int PLAYER_TWO_FRONT_ROW = 1;

    public Board getBoard() {
        return board;
    }

    public Round getRound() {
        return round;
    }

    public ObjectNode handleSpecialAttack(final Coordinates attackerPos, final Coordinates attackedPos) {
        ObjectNode objectNode = mapper.createObjectNode();

        objectNode.put("command","cardUsesAttack");
        ObjectNode cardAttackerNode = mapper.createObjectNode();
        cardAttackerNode.put("x", attackerPos.getX());
        cardAttackerNode.put("y", attackerPos.getY());
        objectNode.set("cardAttacker", cardAttackerNode);

        ObjectNode cardAttackedNode = mapper.createObjectNode();
        cardAttackedNode.put("x", attackedPos.getX());
        cardAttackedNode.put("y", attackedPos.getY());
        objectNode.set("cardAttacked", cardAttackedNode);

        // todo
    }


    public ObjectNode handleAttack(final Coordinates attackerPos, final Coordinates attackedPos) {
        ObjectNode objectNode = mapper.createObjectNode();

        // handle the attackEvent
        objectNode.put("command","cardUsesAttack");
        ObjectNode cardAttackerNode = mapper.createObjectNode();
        cardAttackerNode.put("x", attackerPos.getX());
        cardAttackerNode.put("y", attackerPos.getY());
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

        // Get attacker and attacked cards
        Card attacker = board.getAtPos(attackerPos.getX(), attackerPos.getY());
        if (attacker == null) {
            return null;
        }
        Card attacked = board.getAtPos(attackedPos.getX(), attackedPos.getY());
        if (attacked == null) {
            return null;
        }
        this.attackEvent = new AttackEvent(attacker, attacked);


        // handle the usage of special ability in that round
        if (attacker.HasAttacked()) {
            objectNode.put("error", "Attacker card has already attacked this turn.");
            return objectNode;
        }

        if (attacker.isFrozen()) {
            objectNode.put("error", "Attacker card is frozen.");
            return objectNode;
        }

        // TODO IMPLEMENT THE TANK CARD AND TANK HANDLING OF ATTACK
        // HERE

        //if the one attacked is player two, handle the tank check, else, handle for player one
        if (attackedPos.isPlayerOneSpace()) {
            boolean itExistsATank = board.checkForTank(1);
            if (itExistsATank)
            {
                Card tankCard = board.findTankOnRow(PLAYER_ONE_FRONT_ROW);

                // if the tank card is not equal to the one atttacked, handle the error
                if(!attacked.equals(tankCard)) {
                    objectNode.put("error", "Attacked card is not of type 'Tank’.");
                    return objectNode;
                }

            }
        } else if (attackedPos.isPlayerTwoSpace()) {
            boolean itExistsATank = board.checkForTank(2);
            if (itExistsATank)
            {
                Card tankCard = board.findTankOnRow(PLAYER_TWO_FRONT_ROW);

                // if the tank card is not equal to the one atttacked, handle the error
                if(!attacked.equals(tankCard)) {
                    objectNode.put("error", "Attacked card is not of type 'Tank’.");
                    return objectNode;
                }
            }
        }



        //IMPLEMENT THE ATTACK
        attackEvent.handleAttack(board, attacker, attacked, attackerPos, attackedPos);


        // set attackEvent to null
        this.attackEvent = null;
        return objectNode;
    }
}
