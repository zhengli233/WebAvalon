package com.webAvalon.data;

import java.util.*;

public class MissionSuccessVoteData {
    private List<String> playerNameList = new ArrayList<>();
    private boolean vote;
    private int gameRound;
    private String room;

    public List<String> getPlayerNameList() {
        return playerNameList;
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

    public void setPlayerNameList(List<String> playerNameList) {
        this.playerNameList = playerNameList;
    }

    public void addPlayerName(String playerName) {
        this.playerNameList.add(playerName);
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
}
