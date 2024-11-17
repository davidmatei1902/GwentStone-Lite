package gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;
import resources.*;
import resources.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Objects;

public class GamePlay {
    ArrayList<Game> games;
    Decks p1Decks;
    Decks p2Decks;
    Player p1;
    Player p2;
    ArrayNode output;
    ObjectMapper mapper = new ObjectMapper();

    int currentPlayerTurn;
    int playerOneTurn;
    int playerTwoTurn;

    public GamePlay(ArrayList<Game> games, Decks p1Decks, Decks p2Decks, ArrayNode output) {
        this.games = games;
        this.p1Decks = p1Decks;
        this.p2Decks = p2Decks;
        this.p1 = new Player();
        this.p2 = new Player();
        this.output = output;
    }
    public void  initial_setup() {
        for(Game game : games) {
            //setting startGameInput on current game
            StartGame startGame= game.getStartGame();
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

            for(Card card : p1Decks.getDeck(p1DeckIdx)) {
                p1DeckCopy.add(new Card(card));
            }

            for(Card card : p2Decks.getDeck(p2DeckIdx)) {
                p2DeckCopy.add(new Card(card));
            }


            long seed = startGame.getShuffleSeed();
            Random random1 = new Random(seed);
            Collections.shuffle(p1DeckCopy, random1);
            Random random2 = new Random(seed);
            Collections.shuffle(p2DeckCopy, random2);


            // TODO IMPLEMENT THE REMOVAL OF THE FIRST ELEMENT OF THE LIST
            p1DeckCopy.remove(0);
            p2DeckCopy.remove(0);

            p1.mana = 1;
            p2.mana = 1;

            startGame(hero1, hero2, p1DeckCopy, p2DeckCopy, startGame, actions, startingPlayer);
      }

    }
    public void getPlayerDeck(int playerIdx, ArrayList<Card> deck) {
        ObjectNode objectNode = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        objectNode.put("command", "getPlayerDeck");
        objectNode.put("playerIdx", playerIdx);
        for(Card card : deck) {
            ObjectNode objectNodeCard = mapper.createObjectNode();
            objectNodeCard.put("mana", card.getMana());
            objectNodeCard.put("attackDamage", card.getAttackDamage());
            objectNodeCard.put("health", card.getHealth());
            objectNodeCard.put("description", card.getDescription());
            ArrayNode arrayNodeColors = mapper.createArrayNode();
            for(String color : card.getColors()) {
                arrayNodeColors.add(color);
            }
            objectNodeCard.set("colors", arrayNodeColors);
            objectNodeCard.put("name", card.getName());
            arrayNode.add(objectNodeCard);
        }
        objectNode.set("output", arrayNode);
        output.add(objectNode);
    }

    public void getPlayerHero(int playerIdx,Card hero) {
        ObjectNode objectNode = mapper.createObjectNode(); // {}
        objectNode.put("command", "getPlayerHero");
        objectNode.put("playerIdx", playerIdx);

        ObjectNode objectNodeCard = mapper.createObjectNode();
        objectNodeCard.put("mana", hero.getMana());
        objectNodeCard.put("description", hero.getDescription());

        ArrayNode arrayNodeColors = mapper.createArrayNode();
        for(String color : hero.getColors()) {
            arrayNodeColors.add(color);
        }
        objectNodeCard.set("colors", arrayNodeColors);
        objectNodeCard.put("name", hero.getName());
        objectNodeCard.put("health", hero.getHealth());

        objectNode.set("output", objectNodeCard);
        output.add(objectNode);
    }

    public void getPlayerTurn(int playerIdx) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", "getPlayerTurn");
        objectNode.put("output", playerIdx);
        output.add(objectNode);
    }
    public void startGame(Card hero1, Card hero2,
                          ArrayList<Card> p1Deck,
                          ArrayList<Card> p2Deck,
                          StartGame startGameInput,
                          ArrayList<Actions> actions, int startingPlayer) {


        if(startingPlayer == 1) {
            // is playerOneTurn
            this.currentPlayerTurn = startingPlayer;
            this.playerOneTurn = 1;
            this.playerTwoTurn = 0;
        }
        else {
            // is playerTwoTurn
            this.currentPlayerTurn = startingPlayer;
            this.playerTwoTurn = 1;
            this.playerOneTurn = 0;
        }

        //TODO IMPLEMENT THE "PLAYER HAND" THING


        for(Actions action : actions) {
            int playerIdx;
            switch (action.getCommand()) {
                case "endPlayerTurn":

                    // if is playerOneTurn
                    if(this.currentPlayerTurn == 1)
                    {
                        this.currentPlayerTurn = 2;
                        this.playerOneTurn = 0;
                        this.playerTwoTurn = 1;
                    }
                    else
                    {
                        this.currentPlayerTurn = 1;
                        this.playerOneTurn = 1;
                        this.playerTwoTurn = 0;
                    }
                    break;
                case "getPlayerDeck":
                    playerIdx = action.getPlayerIdx();
                    if(playerIdx == 1) {
                        getPlayerDeck(playerIdx, p1Deck);
                    }
                    else {
                        getPlayerDeck(playerIdx, p2Deck);
                    }
                    break;
                case "getPlayerTurn":
                    getPlayerTurn(this.currentPlayerTurn);
                    break;
                case "getCardsOnTable":
                    break;
                case "getPlayerHero" :
                    playerIdx = action.getPlayerIdx();
                    if(playerIdx == 1)
                    {
                        getPlayerHero(playerIdx,hero1);
                    }
                    else
                    {
                        getPlayerHero(playerIdx,hero2);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}