package gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import resources.*;
import resources.Card;

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
    private Board board = new Board();
    private Round round = new Round();

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
        /////////////////////////////////

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
            Card hero1 = startGame.getPlayerOneHero();
            Card hero2 = startGame.getPlayerTwoHero();
            hero1.setHealth(30);
            hero2.setHealth(30);
            int startPlayerIdx = startGame.getStartingPlayer();
            int p1DeckIdx = startGame.getPlayerOneDeckIdx();
            int p2DeckIdx = startGame.getPlayerTwoDeckIdx();
            ArrayList<Card> p1DeckCopy = new ArrayList<>();
            ArrayList<Card> p2DeckCopy = new ArrayList<>();

            int startingPlayer = startGame.getStartingPlayer();

//            CardInput firstPlayerOneCard = p1Decks.getCardFromDeck(p1DeckIdx);
//            CardInput firstPlayerTwoCard = p2Decks.getCardFromDeck(p2DeckIdx);
//
//            p1DeckCopy.add(new CardInput(firstPlayerOneCard));
//            p2DeckCopy.add(new CardInput(firstPlayerTwoCard));

            for (Card card : p1Decks.getDeck(p1DeckIdx)) {
                p1DeckCopy.add(new Card(card));
            }

            for (Card card : p2Decks.getDeck(p2DeckIdx)) {
                p2DeckCopy.add(new Card(card));
            }


            long seed = startGame.getShuffleSeed();
            Random random1 = new Random(seed);
            Collections.shuffle(p1DeckCopy, random1);
            Random random2 = new Random(seed);
            Collections.shuffle(p2DeckCopy, random2);



            // TODO removing the first card from deck pass the first test... why?
            Card firstPlayerOneCard = p1DeckCopy.removeFirst();
            Card firstPlayerTwoCard = p2DeckCopy.removeFirst();


            // init every player's hands
            Hand playerOneHand = new Hand();
            Hand playerTwoHand = new Hand();
            playerOne.setPlayerHand(playerOneHand);
            playerTwo.setPlayerHand(playerTwoHand);
            playerOne.getPlayerHand().addCard(firstPlayerOneCard);
            playerTwo.getPlayerHand().addCard(firstPlayerTwoCard);


            // debug
            playerOne.getPlayerHand().printHand();
            playerTwo.getPlayerHand().printHand();
            System.out.println("\n");

            playerOne.setMana(1);
            playerTwo.setMana(1);

            // TODO IMPLEMENT THE ROUND SYSTEM, EACH WITH 2 SUB-ROUNDS






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
            ObjectNode objectNodeCard = mapper.createObjectNode();
            objectNodeCard.put("mana", card.getMana());
            objectNodeCard.put("attackDamage", card.getAttackDamage());
            objectNodeCard.put("health", card.getHealth());
            objectNodeCard.put("description", card.getDescription());
            ArrayNode arrayNodeColors = mapper.createArrayNode();
            for (String color : card.getColors()) {
                arrayNodeColors.add(color);
            }
            objectNodeCard.set("colors", arrayNodeColors);
            objectNodeCard.put("name", card.getName());
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
    public void getPlayerHero(final int playerIdx, final Card hero) {
        ObjectNode objectNode = mapper.createObjectNode(); // {}
        objectNode.put("command", "getPlayerHero");
        objectNode.put("playerIdx", playerIdx);

        ObjectNode objectNodeCard = mapper.createObjectNode();
        objectNodeCard.put("mana", hero.getMana());
        objectNodeCard.put("description", hero.getDescription());

        ArrayNode arrayNodeColors = mapper.createArrayNode();
        for (String color : hero.getColors()) {
            arrayNodeColors.add(color);
        }
        objectNodeCard.set("colors", arrayNodeColors);
        objectNodeCard.put("name", hero.getName());
        objectNodeCard.put("health", hero.getHealth());

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

    public void getPlayerMana(final int playerIdx,final Player player) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", "getPlayerMana");
        objectNode.put("playerIdx", playerIdx);
        objectNode.put("output", player.getMana());
        output.add(objectNode);
    }

    public void getCardsOnTable(final Board board) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", "getCardsOnTable");

        ArrayNode allCardsNode = mapper.createArrayNode();
        for(ArrayList<Card> row : board.getCards()) {

            /*
                If row is empty, append to list as empty list
             */
            if(row == null  || row.isEmpty())
            {
                ArrayNode eachRowNode = mapper.createArrayNode();
                allCardsNode.add(eachRowNode);
            }

            for (Card card : row) {
                if (card != null) {
                    ObjectNode objectNodeCard = mapper.createObjectNode();
                    objectNodeCard.put("mana", card.getMana());
                    objectNodeCard.put("attackDamage", card.getAttackDamage());
                    objectNodeCard.put("health", card.getHealth());
                    objectNodeCard.put("description", card.getDescription());
                    ArrayNode arrayNodeColors = mapper.createArrayNode();
                    for (String color : card.getColors()) {
                        arrayNodeColors.add(color);
                    }
                    objectNodeCard.set("colors", arrayNodeColors);
                    objectNodeCard.put("name", card.getName());

                    allCardsNode.add(objectNodeCard);
                }
            }
        }

        objectNode.put("output", allCardsNode);
        output.add(objectNode);
    }


    /**
     * Function place card on board
     * @param player
     * @param handIdx
     */
    // already known which player I am
    public void placeCard(final Player player, final int handIdx) {
        ObjectNode objectNode = mapper.createObjectNode();

        Hand playerHand = player.getPlayerHand();
        Card cardToBePlaced = playerHand.getCard(handIdx);


        ///   TODO IF CARD FROM HAND IS NULL, DO SOMETHING
        if (cardToBePlaced == null) {
            return;
        }
        //System.out.println(cardToBePlaced);

        if (cardToBePlaced.getMana() > player.getMana()) {
            objectNode.put("command", "placeCard");
            objectNode.put("handIdx", handIdx);
            objectNode.put("error", "Not enough mana to place card on table.");
            output.add(objectNode);
            return;
        }
        int maxColums = board.getMaxColums();


        // if playerOne is placing card
        if (player.getPlayerNumber() == 1) {
            /// Last row of the board is full (back row of first player)
            if (board.getCards().get(3).size() > maxColums) {
                objectNode.put("command", "placeCard");
                objectNode.put("handIdx", handIdx);
                objectNode.put("error", "Cannot place card on table since row is full.");
                return;
            } else {
                board.addCardToBoard(cardToBePlaced, player.getPlayerNumber());
            }
        } else {
            /// First row of the board is full (back row of second player)
            if (board.getCards().get(0).size() > maxColums) {
                objectNode.put("command", "placeCard");
                objectNode.put("handIdx", handIdx);
                objectNode.put("error", "Cannot place card on table since column is full.");
                return;
            } else {
                board.addCardToBoard(cardToBePlaced, player.getPlayerNumber());
            }
        }
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
            ObjectNode objectNodeCard = mapper.createObjectNode();
            objectNode.put("mana", card.getMana());
            objectNode.put("attackDamage", card.getAttackDamage());
            objectNode.put("health", card.getHealth());
            objectNode.put("description", card.getDescription());

            ArrayNode arrayNodeColors = mapper.createArrayNode();
            for (String color : card.getColors()) {
                arrayNode.add(color);
            }
            objectNodeCard.set("colors", arrayNodeColors);
            arrayNode.add(objectNodeCard);
        }
        objectNode.set("output", arrayNode);
        output.add(objectNode);
    }

    /// TODO BA VEZI AICI
    /// TODO IN REF PUNE CARTILE MAI INTAI IN MANA, LE VERIFICA SI CRED CA APOI LE PUNE PE MASA ????////////
    /// TODO IMPLEMENTEAZA RUNDELE CA SISTEM
    //////////////////////////////////////////////
    //////////////////////////////////////////////

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
    public void startGame(final Card hero1, final Card hero2,
                          final ArrayList<Card> p1Deck,
                          final ArrayList<Card> p2Deck,
                          final StartGame startGameInput,
                          final ArrayList<Actions> actions, final int startingPlayer) {


        if (startingPlayer == 1) {
            // is playerOneTurn
//            this.currentPlayerTurn = startingPlayer;
//            this.playerOneTurn = 1;
//            this.playerTwoTurn = 0;

            round.setPlayerOneTurn(1);
            round.setPlayerTwoTurn(0);
            round.setCurrentPlayerTurn(1);
        } else {
            // is playerTwoTurn
//            this.currentPlayerTurn = startingPlayer;
//            this.playerOneTurn = 0;
//            this.playerTwoTurn = 1;

            round.setPlayerOneTurn(0);
            round.setPlayerTwoTurn(1);
            round.setCurrentPlayerTurn(2);
        }

        //TODO IMPLEMENT THE "PLAYER HAND" THING


        for (Actions action : actions) {

            int playerIdx;
            switch (action.getCommand()) {
                case "placeCard":
                    int handIdx = action.getHandIdx();
                    if (round.getCurrentPlayerTurn() == 1) {
                        placeCard(playerOne, handIdx);
                    } else {
                        placeCard(playerTwo, handIdx);
                    }
                    break;
                case "endPlayerTurn":

                    // if current player turn is playerOne
                    if (round.getCurrentPlayerTurn() == 1) {
                        playerOne.unFreezeCards(playerOne.getPlayerHand());

                        // set player one turn done
                        round.setPlayerOneHasEndedHisTurn(1);

                        round.setPlayerOneTurn(0);
                        round.setPlayerTwoTurn(1);
                        round.setCurrentPlayerTurn(2);
                    } else {
                        playerTwo.unFreezeCards(playerTwo.getPlayerHand());

                        round.setPlayerTwoHasEndedHisTurn(1);

                        round.setPlayerOneTurn(1);
                        round.setPlayerTwoTurn(0);
                        round.setCurrentPlayerTurn(1);
                    }

                    if (round.getPlayerOneHasEndedHisTurn() == 1 && round.getPlayerTwoHasEndedHisTurn() == 1) {
                        // reset turn flags for the next round
                        round.setPlayerOneHasEndedHisTurn(0);
                        round.setPlayerTwoHasEndedHisTurn(0);

                        // increment the round number
                        round.setCurrentRoundNumber(round.getCurrentRoundNumber() + 1);

                        // increase current mana for each player
                        playerOne.incrementManaBy(round.getCurrentRoundNumber());
                        playerTwo.incrementManaBy(round.getCurrentRoundNumber());
                    }


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
                    getPlayerTurn(round.getCurrentPlayerTurn());
                    break;
                case "getCardsOnTable":
                    getCardsOnTable(this.board);
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
                        getPlayerMana(playerIdx,playerOne);
                    } else {
                        getPlayerMana(playerIdx, playerTwo);
                    }
                default:
                    break;
            }
        }
    }
}