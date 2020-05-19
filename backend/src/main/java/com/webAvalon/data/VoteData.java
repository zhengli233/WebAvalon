package com.webAvalon.data;

import java.util.List;

public class VoteData {
    List<String> candidateList;
    private int gameRound;
    private boolean vote;
    private String room;
    private String playerName;

    public List<String> getCandidateList() {
        return candidateList;
    }

    public boolean getVote() {
        return vote;
    }

    public int getGameRound() {
        return gameRound;
    }

    public String getRoom() {
        return room;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setCandidateList(List<String> candidateList) {
        this.candidateList = candidateList;
    }

    public void addCandidate(String playerName) {
        this.candidateList.add(playerName);
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
