package com.webAvalon.data;

import java.util.ArrayList;
import java.util.List;

public class RoomData {
    private String room;
    private boolean onGoing;
    private List<String> playerNames;

    public RoomData() {
        this.room = "";
        this.onGoing = false;
        this.playerNames = new ArrayList<>();
    }

    public RoomData(String room) {
        this.room = room;
        this.onGoing = false;
        this.playerNames = new ArrayList<>();
    }

    public String getRoom() {
        return room;
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public boolean getOnGoing() {
        return onGoing;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setOnGoing(boolean onGoing) {
        this.onGoing = onGoing;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public void addPlayer(String playerName) {
        this.playerNames.add(playerName);
    }

    public void clear() {
        this.onGoing = false;
        this.playerNames.clear();
    }

    public boolean containsPlayerName(String playerName) {
        for (String playerNameExist :
                playerNames) {
            if (playerNameExist.equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    public void removePlayerName(String playerName) {
        for (String containedPlayerName:
             playerNames) {
            if(containedPlayerName.equals(playerName)) {
                playerNames.remove(containedPlayerName);
                return;
            }
        }
    }
}
