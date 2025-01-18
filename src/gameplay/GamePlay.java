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
    private Board board = new Board();
    private Round round = new Round();

    private ObjectMapper mapper = new ObjectMapper();


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
//                p1DeckCopy.add(new Card(card));
                p1DeckCopy.add(Card.createCard(card));
            }

            for (Card card : p2Decks.getDeck(p2DeckIdx)) {
//                p2DeckCopy.add(new Card(card));
                p2DeckCopy.add(Card.createCard(card));

            }


            long seed = startGame.getShuffleSeed();
            Random random1 = new Random(seed);
            Collections.shuffle(p1DeckCopy, random1);
            Random random2 = new Random(seed);
            Collections.shuffle(p2DeckCopy, random2);


            // todo *****THINGS*****
//            Card firstPlayerOneCard = p1DeckCopy.removeFirst();
//            Card firstPlayerTwoCard = p2DeckCopy.removeFirst();
            // todo *****THINGS*****



            // init every player's hands
            Hand playerOneHand = new Hand();
            Hand playerTwoHand = new Hand();
            playerOne.setPlayerHand(playerOneHand);
            playerTwo.setPlayerHand(playerTwoHand);

            // todo *****THINGS*****
//            playerOne.getPlayerHand().addCard(firstPlayerOneCard);
//            playerTwo.getPlayerHand().addCard(firstPlayerTwoCard);
            // todo *****THINGS*****



            // debug
//            playerOne.getPlayerHand().printHand();
//            playerTwo.getPlayerHand().printHand();
//            System.out.println("\n");

            playerOne.setMana(1);
            playerTwo.setMana(1);

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

    /**
     * Function that output all the cards on the table (from top left to bottom right)
     * @param board
     */
    public void getCardsOnTable(final Board board) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", "getCardsOnTable");

        ArrayNode allCardsNode = mapper.createArrayNode();
        for(ArrayList<Card> row : board.getCards()) {


            ArrayNode eachRowNode = mapper.createArrayNode();

            if(row == null  || row.isEmpty())
            {
                allCardsNode.add(eachRowNode);
            }
            else {
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

                        eachRowNode.add(objectNodeCard);
                    }
                }
                allCardsNode.add(eachRowNode);
            }
        }

        objectNode.put("output", allCardsNode);
        output.add(objectNode);
    }

    public void handlePlacingCard(final Player player, final Hand playerHand, final Card cardToBePlaced, final int handIdx, final ObjectNode objectNode) {
        int maxColums = board.getMaxColums();
        cardToBePlaced.addToBoard(board, player);
        
        // if playerOne is placing card
//        if (player.getPlayerNumber() == 1) {
//            /// Last row of the board is full (back row of first player)
//            if (board.getCards().get(3).size() > maxColums) {
//                objectNode.put("command", "placeCard");
//                objectNode.put("handIdx", handIdx);
//                objectNode.put("error", "Cannot place card on table since row is full.");
//                return;
//            } else {
//                board.addCardToBoard(cardToBePlaced, player.getPlayerNumber());
//            }
//        } else {
//            /// First row of the board is full (back row of second player)
//            if (board.getCards().get(0).size() > maxColums) {
//                objectNode.put("command", "placeCard");
//                objectNode.put("handIdx", handIdx);
//                objectNode.put("error", "Cannot place card on table since column is full.");
//                return;
//            } else {
//                board.addCardToBoard(cardToBePlaced, player.getPlayerNumber());
//            }
//        }
    }


    /**
     * Function place card on board
     * @param player
     * @param handIdx card index from hand which want to be placed
     * @param playerHand player hand
     */
    // already known which player I am
    public void placeCard(final Player player, final Hand playerHand, final int handIdx) {
        ObjectNode objectNode = mapper.createObjectNode();

        Card cardToBePlaced = playerHand.getCard(handIdx);


        ///   TODO IF CARD FROM HAND IS NULL, DO SOMETHING
        if (cardToBePlaced == null) {
            return;
        }
        //System.out.println(cardToBePlaced);

        // handle not enough mana
        if (cardToBePlaced.getMana() > player.getMana()) {
            objectNode.put("command", "placeCard");
            objectNode.put("handIdx", handIdx);
            objectNode.put("error", "Not enough mana to place card on table.");
            output.add(objectNode);
            return;
        }

        handlePlacingCard(player,playerHand,cardToBePlaced,handIdx,objectNode);
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
            if(card != null) {
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
        }
        objectNode.set("output", arrayNode);
        output.add(objectNode);
    }

    private void handleEndTurn(final ArrayList<Card> p1Deck, final ArrayList<Card> p2Deck, final Hand playerOneHand, final Hand playerTwoHand) {
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
        round.setPlayerOneHasEndedHisTurn(0);
        round.setPlayerTwoHasEndedHisTurn(0);

        round.setCurrentRoundNumber(round.getCurrentRoundNumber() + 1);

        if (!p1Deck.isEmpty()) playerOneHand.addCard(p1Deck.remove(0));
        if (!p2Deck.isEmpty()) playerTwoHand.addCard(p2Deck.remove(0));

        int manaToAdd = Math.min(round.getCurrentRoundNumber(), 10);

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
    public void startGame(final Card hero1, final Card hero2,
                          final ArrayList<Card> p1Deck,
                          final ArrayList<Card> p2Deck,
                          final StartGame startGameInput,
                          final ArrayList<Actions> actions, final int startingPlayer) {

        Hand playerOneHand = playerOne.getPlayerHand();
        Hand playerTwoHand = playerTwo.getPlayerHand();
        round.setCurrentPlayerTurn(startingPlayer);

        if (round.getCurrentRoundNumber() == 0) {
            if (!p1Deck.isEmpty()) playerOneHand.addCard(p1Deck.remove(0));
            if (!p2Deck.isEmpty()) playerTwoHand.addCard(p2Deck.remove(0));
            round.setCurrentRoundNumber(1);
        }

        for (Actions action : actions) {
            int playerIdx;
            switch (action.getCommand()) {

                // TODO HANDLE WRONG PLACING CARD ACCORDINGLY
                case "placeCard":
                    int handIdx = action.getHandIdx();
                    if (round.getCurrentPlayerTurn() == 1) {
                        placeCard(playerOne, playerOneHand, handIdx);
                    } else {
                        placeCard(playerTwo, playerTwoHand, handIdx);
                    }
                    break;
                case "endPlayerTurn":
                    handleEndTurn(p1Deck,p2Deck,playerOneHand,playerTwoHand);
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