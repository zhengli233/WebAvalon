package com.webAvalon.data;

public class VoteEnableJsonData {
    private int playerNumber;
    private int playerIndex;
    private int gameRound;
    private boolean vote;

    public int getPlayerIndex() {
        return playerIndex;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean getVote() {
        return vote;
    }

    public int getGameRound() {
        return gameRound;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }
}
