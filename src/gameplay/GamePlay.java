package gameplay;

import characters.hero.Hero;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fileio.Coordinates;
import gameplay.events.EventsHandler;
import resources.Decks;
import resources.Card;
import resources.Board;
import resources.Game;
import resources.Round;
import resources.Hand;
import resources.StartGame;
import resources.Actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GamePlay {
    private ArrayList<Game> games;
    private Decks p1Decks;
    private Decks p2Decks;
    private Player playerOne;
    private Player playerTwo;
    private ArrayNode output;

    private ObjectMapper mapper = new ObjectMapper();

    private EventsHandler eventHandler = new EventsHandler();


    public GamePlay(final ArrayList<Game> games, final Decks p1Decks,
                    final Decks p2Decks, final ArrayNode output) {
        this.games = games;
        this.p1Decks = p1Decks;
        this.p2Decks = p2Decks;

        /// SETTING UP THE PLAYER NUMBER
        this.playerOne = new Player();
        playerOne.setPlayerNumber(1);
        this.playerTwo = new Player();
        playerTwo.setPlayerNumber(2);

        this.output = output;
    }

    /**
     * This function initialise the game
     */
    public void  initialSetup() {
        for (Game game : games) {
            //setting startGameInput on current game
            StartGame startGame = game.getStartGame();
            //System.out.println(startGameInput);
            ArrayList<Actions> actions = game.getActions();

            Card heroOnecard = startGame.getPlayerOneHero();
            Card heroTwocard = startGame.getPlayerTwoHero();

            // convert Hero from Card Class to Hero Class
            Hero hero1 = Card.createHero(heroOnecard);
            Hero hero2 = Card.createHero(heroTwocard);

            hero1.setHealth(Hero.getMaxHealth());
            hero2.setHealth(Hero.getMaxHealth());
            int p1DeckIdx = startGame.getPlayerOneDeckIdx();
            int p2DeckIdx = startGame.getPlayerTwoDeckIdx();
            ArrayList<Card> p1DeckCopy = new ArrayList<>();
            ArrayList<Card> p2DeckCopy = new ArrayList<>();

            int startingPlayer = startGame.getStartingPlayer();


            for (Card card : p1Decks.getDeck(p1DeckIdx)) {
                p1DeckCopy.add(Card.createCard(card));
            }

            for (Card card : p2Decks.getDeck(p2DeckIdx)) {
                p2DeckCopy.add(Card.createCard(card));

            }

            long seed = startGame.getShuffleSeed();
            Random random1 = new Random(seed);
            Collections.shuffle(p1DeckCopy, random1);
            Random random2 = new Random(seed);
            Collections.shuffle(p2DeckCopy, random2);

            // init every player's hands
            Hand playerOneHand = new Hand();
            Hand playerTwoHand = new Hand();
            playerOne.setPlayerHand(playerOneHand);
            playerTwo.setPlayerHand(playerTwoHand);

            // debug
//            playerOne.getPlayerHand().printHand();
//            playerTwo.getPlayerHand().printHand();
//            System.out.println("\n");

            startGame(hero1, hero2, p1DeckCopy, p2DeckCopy, startGame, actions, startingPlayer);
      }

    }

    /**
     * Function print Player Deck given by Player Id in output
     * @param playerIdx
     * @param deck
     */
    public void getPlayerDeck(final int playerIdx, final ArrayList<Card> deck) {
        ObjectNode objectNode = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        objectNode.put("command", "getPlayerDeck");
        objectNode.put("playerIdx", playerIdx);
        for (Card card : deck) {
            ObjectNode objectNodeCard = card.createOutputWrapper();
            arrayNode.add(objectNodeCard);
        }
        objectNode.set("output", arrayNode);
        output.add(objectNode);
    }

    /**
     * Function print player hero in output
     * @param playerIdx
     * @param hero
     */
    public void getPlayerHero(final int playerIdx, final Hero hero) {
        ObjectNode objectNode = mapper.createObjectNode(); // {}
        objectNode.put("command", "getPlayerHero");
        objectNode.put("playerIdx", playerIdx);

        ObjectNode objectNodeCard = hero.createOutputWrapper();
        objectNode.set("output", objectNodeCard);
        output.add(objectNode);
    }

    /**
     * Function prints player turn in output
     * @param playerIdx
     */
    public void getPlayerTurn(final int playerIdx) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", "getPlayerTurn");
        objectNode.put("output", playerIdx);
        output.add(objectNode);
    }

    public void getPlayerMana(final int playerIdx, final Player player) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", "getPlayerMana");
        objectNode.put("playerIdx", playerIdx);
        objectNode.put("output", player.getMana());
        output.add(objectNode);
    }

    /**
     * Function that output all the cards on the table (from top left to bottom right)
     * @param board
     */
    public void getCardsOnTable(final Board board) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", "getCardsOnTable");

        ArrayNode allCardsNode = mapper.createArrayNode();
        for (ArrayList<Card> row : board.getCards()) {
            ArrayNode eachRowNode = mapper.createArrayNode();
            if (row == null) {
                allCardsNode.add(eachRowNode);
            }

            for (Card card : row) {
                if (card == null) {
                    return;
                }
                ObjectNode objectNodeCardWrapper = card.createOutputWrapper();
                eachRowNode.add(objectNodeCardWrapper);
            }
            allCardsNode.add(eachRowNode);
        }

        objectNode.put("output", allCardsNode);
        output.add(objectNode);
    }

    /**
     * Function place card on board
     * @param player
     * @param handIdx card index from hand which want to be placed
     * @param playerHand player hand
     */
    public void placeCard(final Player player, final Hand playerHand, final int handIdx) {
        ObjectNode objectNode = mapper.createObjectNode();

        Card cardToBePlaced = playerHand.getCard(handIdx);

        // handle not enough mana
        if (cardToBePlaced.getMana() > player.getMana()) {
            objectNode.put("command", "placeCard");
            objectNode.put("handIdx", handIdx);
            objectNode.put("error", "Not enough mana to place card on table.");
            output.add(objectNode);
            return;
        }

        // remove given card
        playerHand.removeCardAtIndex(handIdx);

        ///   TODO IF CARD THAT CAN BE DRAW FROM HAND IS INVALID, DO SOMETHING
        if (cardToBePlaced == null) {
            return;
        }
        //System.out.println(cardToBePlaced);



        cardToBePlaced.addToBoard(eventHandler.getBoard(), player);
    }

    public void cardUsesAttack(final Coordinates attackerPos, final Coordinates attackedPos) {
        eventHandler.handleAttack(attackerPos, attackedPos);
    }


    public void cardUsesAbility(final Coordinates attackerPos, final Coordinates attackedPos) {
        eventHandler.handleSpecialAttack(attackerPos, attackedPos);
    }

    /**
     * Function prints player hand in output
     * @param player
     */
    public void getCardsInHand(final Player player) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", "getCardsInHand");
        objectNode.put("playerIdx", player.getPlayerNumber());

        Hand playerHand = player.getPlayerHand();
        ArrayNode arrayNode = mapper.createArrayNode();
        for (Card card : playerHand.getCards()) {
            if (card == null) {
                return;
            }
            ObjectNode objectNodeCardWrapper = card.createOutputWrapper();
            arrayNode.add(objectNodeCardWrapper);
        }
        objectNode.set("output", arrayNode);
        output.add(objectNode);
    }
    public void getCardAtPosition(final int x,final int y)
    {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", "getCardAtPosition");
        objectNode.put("x", x);
        objectNode.put("y", y);


        Card respectiveCard = eventHandler.getBoard().getAtPos(x,y);
        if(respectiveCard == null)
        {
            objectNode.put("output","No card available at that position.");
            output.add(objectNode);
            return;
        }
        ObjectNode cardOutputNode = respectiveCard.createOutputWrapper();

        objectNode.put("output",cardOutputNode);
        output.add(objectNode);
    }

    private void handleEndTurn(final ArrayList<Card> p1Deck, final ArrayList<Card> p2Deck, final Hand playerOneHand, final Hand playerTwoHand) {
        Round round = eventHandler.getRound();
        if (round.getCurrentPlayerTurn() == 1) {
            // end player one turn
            playerOne.unFreezeCards(playerOne.getPlayerHand());
            round.setPlayerOneHasEndedHisTurn(1);
            round.setPlayerOneTurn(0);
            round.setPlayerTwoTurn(1);
            round.setCurrentPlayerTurn(2);
        } else {
            // end player two turn
            playerTwo.unFreezeCards(playerTwo.getPlayerHand());
            round.setPlayerTwoHasEndedHisTurn(1);
            round.setPlayerOneTurn(1);
            round.setPlayerTwoTurn(0);
            round.setCurrentPlayerTurn(1);
        }

        // if both players have ended their turn, start a new round
        if (round.getPlayerOneHasEndedHisTurn() == 1 && round.getPlayerTwoHasEndedHisTurn() == 1) {
            startNewRound(p1Deck, p2Deck, playerOneHand, playerTwoHand);
        }
    }

    private void startNewRound(final ArrayList<Card> p1Deck, final ArrayList<Card> p2Deck, final Hand playerOneHand, final Hand playerTwoHand) {
        eventHandler.getRound().setPlayerOneHasEndedHisTurn(0);
        eventHandler.getRound().setPlayerTwoHasEndedHisTurn(0);

        eventHandler.getRound().setCurrentRoundNumber(eventHandler.getRound().getCurrentRoundNumber() + 1);

        if (!p1Deck.isEmpty()) {
            playerOneHand.addCard(p1Deck.removeFirst());
        }
        if (!p2Deck.isEmpty()) {
            playerTwoHand.addCard(p2Deck.removeFirst());
        }

        int manaToAdd = Math.min(eventHandler.getRound().getCurrentRoundNumber(), eventHandler.getRound().getMaxRoundsNumber());

        playerOne.incrementManaBy(manaToAdd);
        playerTwo.incrementManaBy(manaToAdd);
    }

    /**
     * Function that starts the game
     * @param hero1
     * @param hero2
     * @param p1Deck
     * @param p2Deck
     * @param startGameInput
     * @param actions
     * @param startingPlayer
     */
    public void startGame(final Hero hero1, final Hero hero2,
                          final ArrayList<Card> p1Deck,
                          final ArrayList<Card> p2Deck,
                          final StartGame startGameInput,
                          final ArrayList<Actions> actions, final int startingPlayer) {

        Hand playerOneHand = playerOne.getPlayerHand();
        Hand playerTwoHand = playerTwo.getPlayerHand();
        eventHandler.getRound().setCurrentPlayerTurn(startingPlayer);


        // draw first card from deck and set round
        eventHandler.getRound().setCurrentRoundNumber(1);
        playerOneHand.addCard(p1Deck.removeFirst());
        playerTwoHand.addCard(p2Deck.removeFirst());

        for (Actions action : actions) {
            int playerIdx;
            switch (action.getCommand()) {

                // TODO HANDLE WRONG PLACING CARD ACCORDINGLY
                case "placeCard":
                    int handIdx = action.getHandIdx();
                    if (eventHandler.getRound().getCurrentPlayerTurn() == 1) {
                        placeCard(playerOne, playerOneHand, handIdx);
                    } else {
                        placeCard(playerTwo, playerTwoHand, handIdx);
                    }
                    break;
                case "cardUsesAttack":
                    Coordinates attackerPos = action.getCardAttacker();
                    Coordinates attackedPos = action.getCardAttacked();
                    cardUsesAttack(attackerPos, attackedPos);
                    break;
                case "cardUsesAbility":
                    Coordinates attackerPosAbility = action.getCardAttacker();
                    Coordinates attackedPosAbility = action.getCardAttacked();
                    cardUsesAbility(attackerPosAbility, attackedPosAbility);
                    break;
                case "endPlayerTurn":
                    handleEndTurn(p1Deck, p2Deck, playerOneHand, playerTwoHand);
                    break;
                case "getPlayerDeck":
                    playerIdx = action.getPlayerIdx();
                    if (playerIdx == 1) {
                        getPlayerDeck(playerIdx, p1Deck);
                    } else {
                        getPlayerDeck(playerIdx, p2Deck);
                    }
                    break;
                case "getPlayerTurn":
                    getPlayerTurn(eventHandler.getRound().getCurrentPlayerTurn());
                    break;
                case "getCardAtPosition":
                    int x = action.getX();
                    int y = action.getY();
                    getCardAtPosition(x,y);
                    break;
                case "getCardsOnTable":
                    getCardsOnTable(eventHandler.getBoard());
                    break;
                case "getCardsInHand":
                    playerIdx = action.getPlayerIdx();
                    if (playerIdx == 1) {
                        getCardsInHand(playerOne);
                    } else {
                        getCardsInHand(playerTwo);
                    }
                    break;
                case "getPlayerHero" :
                    playerIdx = action.getPlayerIdx();
                    if (playerIdx == 1) {
                        getPlayerHero(playerIdx, hero1);
                    } else {
                        getPlayerHero(playerIdx, hero2);
                    }
                    break;
                case "getPlayerMana" :
                    playerIdx = action.getPlayerIdx();
                    if (playerIdx == 1) {
                        getPlayerMana(playerIdx, playerOne);
                    } else {
                        getPlayerMana(playerIdx, playerTwo);
                    }
                default:
                    break;
            }
        }
    }
}