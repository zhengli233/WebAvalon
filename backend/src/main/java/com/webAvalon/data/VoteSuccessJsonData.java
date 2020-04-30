package com.webAvalon.data;

import java.util.*;

public class VoteSuccessJsonData {
    private int playerIndex;
    private List<Integer> votePlayerIndexes = new ArrayList<Integer>();
    private boolean vote;
    private int gameRound;

    public int getPlayerIndex() {
        return playerIndex;
    }

    public List<Integer> getVotePlayerIndexes() {
        return votePlayerIndexes;
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

    public void setVotePlayerIndexes(List<Integer> indexes) {
        this.votePlayerIndexes = indexes;
    }

    public void addVotePlayerIndex(Integer index){
        votePlayerIndexes.add(index);
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }
}
