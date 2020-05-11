package com.webAvalon.game;

import com.webAvalon.data.RoomData;

import java.util.List;

public class Room {
    private RoomData roomData;
    private List<Player> players;
    private MissionManager dealer;

    public RoomData getRoomData() {
        return roomData;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public MissionManager getDealer() {
        return dealer;
    }

    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setDealer(MissionManager dealer) {
        this.dealer = dealer;
    }

    public void clear() {
        roomData.clear();
        players.clear();
    }

    public boolean containsPlayer(String playerName) {
        for (Player player :
                players) {
            if (player.getPlayerData().getName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayer(String playerName) {
        for (Player player :
                players) {
            if (player.getPlayerData().getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }
}
