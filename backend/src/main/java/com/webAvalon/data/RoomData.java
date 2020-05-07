package com.webAvalon.data;

import java.util.ArrayList;
import java.util.List;

public class RoomData {
    private int roomSize;
    private final String room;
    private boolean empty;
    private List<PlayerData> players;

    public RoomData(String room) {
        this.roomSize = 0;
        this.room = room;
        this.empty = true;
        this.players = new ArrayList<PlayerData>();
    }

    public int getRoomSize() {
        return roomSize;
    }

    public String getRoom() {
        return room;
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean getEmpty() {
        return empty;
    }

    public List<PlayerData> getPlayers() {
        return players;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setPlayers(List<PlayerData> players) {
        this.players = players;
    }

    public void addPlayer(PlayerData player) {
        this.players.add(player);
    }

    public boolean allReady() {
        if (players.size() < roomSize) {
            return false;
        }
        for (int i = 0; i < players.size(); i++) {
            PlayerData player = players.get(i);
            if(!player.isReady()) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        this.roomSize = 0;
        this.empty = true;
        this.players.clear();
    }
}
