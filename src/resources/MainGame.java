package resources;

import fileio.CardInput;
import fileio.DecksInput;
import fileio.GameInput;

import java.util.ArrayList;

public class MainGame {
    private Decks playerOneDecks;
    private Decks playerTwoDecks;
    private ArrayList<Game> games;


    public MainGame(final DecksInput playerOneDecks, final DecksInput playerTwoDecks, final ArrayList<GameInput> games) {
        int nrCardsInDeck1 = playerOneDecks.getNrCardsInDeck();
        int nrCardsInDeck2 = playerTwoDecks.getNrCardsInDeck();
        int nrDecks1 = playerOneDecks.getNrDecks();
        int nrDecks2 = playerTwoDecks.getNrDecks();
        ArrayList<ArrayList<CardInput>> p1Deck = playerOneDecks.getDecks();
        ArrayList<ArrayList<CardInput>> p2Deck = playerTwoDecks.getDecks();
        this.playerOneDecks = new Decks(nrCardsInDeck1, nrDecks1, p1Deck);
        this.playerTwoDecks = new Decks(nrCardsInDeck2, nrDecks2, p2Deck);

        this.games = new ArrayList<>(games.size());
        for (GameInput g : games) {
            Game correctGame = new Game(g);
            this.games.add(correctGame);
        }
    }
    public Decks getPlayerOneDecks() {
        return playerOneDecks;
    }

    public void setPlayerOneDecks(final Decks playerOneDecks) {
        this.playerOneDecks = playerOneDecks;
    }

    public Decks getPlayerTwoDecks() {
        return playerTwoDecks;
    }

    public void setPlayerTwoDecks(final Decks playerTwoDecks) {
        this.playerTwoDecks = playerTwoDecks;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(final ArrayList<Game> games) {
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
