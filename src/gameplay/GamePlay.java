package gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Objects;

public class GamePlay {
    ArrayList<GameInput> games;
    DecksInput p1Decks;
    DecksInput p2Decks;
    Player p1;
    Player p2;
    ArrayNode output;
    ObjectMapper mapper = new ObjectMapper();
    public GamePlay(ArrayList<GameInput> games, DecksInput p1Decks, DecksInput p2Decks, ArrayNode output) {
        this.games = games;
        this.p1Decks = p1Decks;
        this.p2Decks = p2Decks;
        this.p1 = new Player();
        this.p2 = new Player();
        this.output = output;
    }
    public void  initial_setup() {
        for(GameInput game : games) {
            StartGameInput startGameInput= game.getStartGame();
            ArrayList<ActionsInput> actions = game.getActions();
            CardInput hero1 = startGameInput.getPlayerOneHero();
            CardInput hero2 = startGameInput.getPlayerTwoHero();
            int startPlayerIdx = startGameInput.getStartingPlayer();
            int p1DeckIdx = startGameInput.getPlayerOneDeckIdx();
            int p2DeckIdx = startGameInput.getPlayerTwoDeckIdx();
            ArrayList<CardInput> p1DeckCopy = new ArrayList<>();
            ArrayList<CardInput> p2DeckCopy = new ArrayList<>();
            for(CardInput card : p1Decks.getDeck(p1DeckIdx)) {
                p1DeckCopy.add(new CardInput(card));
            }
            for(CardInput card : p2Decks.getDeck(p2DeckIdx)) {
                p2DeckCopy.add(new CardInput(card));
            }
            int seed = startGameInput.getShuffleSeed();
            Random random1 = new Random(seed);
            Collections.shuffle(p1DeckCopy, random1);
            Random random2 = new Random(seed);
            Collections.shuffle(p2DeckCopy, random2);
            p1.mana = 1;
            p2.mana = 1;
            startGame(hero1, hero2, p1DeckCopy, p2DeckCopy, startGameInput, actions);

      }

    }
    public void getCardsInHand(int playerIdx, ArrayList<CardInput> deck) {
        ObjectNode objectNode = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        objectNode.put("command", "getCardsInHand");
        objectNode.put("playerIdx", playerIdx);
        for(CardInput card : deck) {
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
    public void startGame(CardInput hero1, CardInput hero2,
                          ArrayList<CardInput> p1Deck,
                          ArrayList<CardInput> p2Deck,
                          StartGameInput startGameInput,
                          ArrayList<ActionsInput> actions) {
        for(ActionsInput action : actions) {

            switch (action.getCommand()) {
                case "getCardsInHand":
                    int playerIdx = action.getPlayerIdx();
                    if(playerIdx == 1) {
                        getCardsInHand(playerIdx, p1Deck);
                    }
                    else {
                        getCardsInHand(playerIdx, p2Deck);
                    }
                    break;
                case "getPlayerDeck":
                    break;
                case "getCardsOnTable":
                    break;
                default:
                    break;
            }
        }

    }
}