package com.webAvalon.game;

import com.webAvalon.data.PlayerData;
import com.webAvalon.data.RoomData;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private static RoomManager instance = new RoomManager();

    private List<RoomData> rooms;

    private RoomManager() {
        rooms = new ArrayList<RoomData>();
        for(int i = 0; i < 10; i++) {
            RoomData room = new RoomData(String.valueOf(i));
            room.setEmpty(true);
            rooms.add(room);
        }
    }

    public static RoomManager getInstance() {
        return instance;
    }

    public RoomData getRoom(String room) {
        for(int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getRoom() == room) {
                return rooms.get(i);
            }
        }
        return null;
    }

    public RoomData createRoom(int playerNumber, PlayerData player) {
        if (playerNumber >= 5 && playerNumber <= 10) {
            for(int i = 0; i < rooms.size(); i++) {
                RoomData room = rooms.get(i);
                if(room.isEmpty()) {
                    room.setEmpty(false);
                    room.setRoomSize(playerNumber);
                    room.addPlayer(player);
                    return room;
                }
            }
        }
        return null;
    }

    public RoomData enterRoom(String room, PlayerData player) {
        for(int i = 0; i < rooms.size(); i++) {
            RoomData roomData = rooms.get(i);
            if(roomData.getRoom() == room) {
                roomData.addPlayer(player);
                return roomData;
            }
        }
        return null;
    }

    public boolean closeRoom(String room) {
        for (int i = 0; i < rooms.size(); i++) {
            RoomData roomData = rooms.get(i);
            if (roomData.getRoom() == room) {
                roomData.clear();
                return true;
            }
        }
        return false;
    }

    public void clearRooms() {
        for (int i = 0; i < rooms.size(); i++) {
            rooms.get(i).clear();
        }
    }
}
