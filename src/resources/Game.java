package resources;


import fileio.ActionsInput;
import fileio.GameInput;

import java.util.ArrayList;

public class Game {
    private StartGame startGame;
    private ArrayList<Actions> actions;

    public Game(final GameInput game) {
        this.startGame = new StartGame(game.getStartGame());

        this.actions = new ArrayList<>();
        for (ActionsInput action : game.getActions()) {
            Actions goodAction = new Actions(action);
            this.actions.add(goodAction);
        }

    }

    public StartGame getStartGame() {
        return startGame;
    }

    public void setStartGame(final StartGame startGame) {
        this.startGame = startGame;
    }

    public ArrayList<Actions> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<Actions> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "GameInput{"
                +  "startGame="
                + startGame
                + ", actions="
                + actions
                + '}';
    }
}
