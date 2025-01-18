package resources;

public class Round {
    private int currentPlayerTurn;

    private int playerOneTurn = 0;
    private int playerOneHasEndedHisTurn = 0;

    private int playerTwoTurn = 0;
    private int playerTwoHasEndedHisTurn = 0;

    private int currentRoundNumber = 0;


    public void increaseRoundNumber() {
        currentRoundNumber++;
    }

    public boolean isStartOfRound() {
        return playerOneHasEndedHisTurn == 0 && playerTwoHasEndedHisTurn == 0;
    }

    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public void setCurrentPlayerTurn(int currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public int getPlayerTwoHasEndedHisTurn() {
        return playerTwoHasEndedHisTurn;
    }

    public void setPlayerTwoHasEndedHisTurn(int playerTwoHasEndedHisTurn) {
        this.playerTwoHasEndedHisTurn = playerTwoHasEndedHisTurn;
    }

    public int getPlayerOneHasEndedHisTurn() {
        return playerOneHasEndedHisTurn;
    }

    public void setPlayerOneHasEndedHisTurn(int playerOneHasEndedHisTurn) {
        this.playerOneHasEndedHisTurn = playerOneHasEndedHisTurn;
    }

    public void setCurrentRoundNumber(int currentRoundNumber) {
        this.currentRoundNumber = currentRoundNumber;
    }

    public void setPlayerTwoTurn(int playerTwoTurn) {
        this.playerTwoTurn = playerTwoTurn;
    }

    public void setPlayerOneTurn(int playerOneTurn) {
        this.playerOneTurn = playerOneTurn;
    }

    public int getPlayerOneTurn() {
        return playerOneTurn;
    }

    public int getPlayerTwoTurn() {
        return playerTwoTurn;
    }

    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }
}
