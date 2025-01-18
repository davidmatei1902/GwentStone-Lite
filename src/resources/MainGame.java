package resources;

import fileio.DecksInput;
import fileio.GameInput;

import java.util.ArrayList;

public class MainGame {
    private Decks playerOneDecks;
    private Decks playerTwoDecks;
    private ArrayList<Game> games;


    public MainGame(DecksInput playerOneDecks, DecksInput playerTwoDecks, ArrayList<GameInput> games) {
        this.playerOneDecks = new Decks(playerOneDecks.getNrCardsInDeck(), playerOneDecks.getNrDecks(), playerOneDecks.getDecks());
        this.playerTwoDecks = new Decks(playerTwoDecks.getNrCardsInDeck(), playerTwoDecks.getNrDecks(), playerTwoDecks.getDecks());

        this.games = new ArrayList<>(games.size());
        for(GameInput g : games) {
            Game correctGame = new Game(g);
            this.games.add(correctGame);
        }
    }
    public Decks getPlayerOneDecks() {
        return playerOneDecks;
    }

    public void setPlayerOneDecks(Decks playerOneDecks) {
        this.playerOneDecks = playerOneDecks;
    }

    public Decks getPlayerTwoDecks() {
        return playerTwoDecks;
    }

    public void setPlayerTwoDecks(Decks playerTwoDecks) {
        this.playerTwoDecks = playerTwoDecks;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "Input{"
                + "player_one_decks="
                + playerOneDecks
                + ", player_two_decks="
                + playerTwoDecks
                +  ", games="
                + games
                +  '}';
    }
}
