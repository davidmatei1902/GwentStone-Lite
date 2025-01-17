package gameplay;

import resources.*;

import java.util.ArrayList;

public class Player {

    private int playerNumber;
    private int mana;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int maxMana = 10;

    private Hand playerHand;

    public Player() {
        mana = 0;
        gamesPlayed = 0;
        gamesWon = 0;
        gamesLost = 0;
        playerHand = new Hand();
    }

    public void incrementManaBy(int amount) {
        // increase amount of mana if only the following amount is not bigger that maxMana
        if(mana + amount < maxMana) {
            mana += amount;
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public void unFreezeCards(final Hand playerHand) {
        ArrayList<Card> cards = playerHand.getCards();
        for (Card card : cards) {
            if (card.getFrozenStatus()) {
                card.setFrozenStatus(false);
            }
        }
    }
}