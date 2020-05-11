package com.webAvalon.game;

import com.webAvalon.data.PlayerData;
import com.webAvalon.data.RoomData;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private static int roomNumbers = 10;
    private static RoomManager instance = new RoomManager();

    private List<MissionManager> dealers;
    private List<Room> rooms;

    private RoomManager() {
        dealers = new ArrayList<>();
        rooms = new ArrayList<>();
        for (int i = 0; i < roomNumbers; i++) {
            RoomData roomData = new RoomData(String.valueOf(i));
            roomData.setOnGoing(false);
            MissionManager dealer = new MissionManager(roomData);
            dealers.add(dealer);
            Room room = new Room();
            room.setDealer(dealer);
            room.setRoomData(roomData);
            rooms.add(room);
        }
    }

    public static RoomManager getInstance() {
        return instance;
    }

    public RoomData getRoomData(String room) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomData().getRoom().equals(room)) {
                return rooms.get(i).getRoomData();
            }
        }
        return null;
    }

    public RoomData enterRoom(String room, String playerName) {
        for (int i = 0; i < rooms.size(); i++) {
            RoomData roomData = rooms.get(i).getRoomData();
            if (roomData.getRoom().equals(room)) {
                if (roomData.containsPlayerName(playerName)) {
                    return null;
                }
                roomData.addPlayer(playerName);
                Player player = new Player();
                PlayerData playerData = new PlayerData();
                playerData.setName(playerName);
                player.setPlayerData(playerData);
                rooms.get(i).getPlayers().add(player);
                return roomData;
            }
        }
        return null;
    }

    public MissionManager getDealer(String room) {
        for (MissionManager dealer : dealers) {
            if (dealer.getRoomData().equals(room)) {
                return dealer;
            }
        }
        return null;
    }

    public Room getRoom(String roomName) {
        for (Room room :
                rooms) {
            if (room.getRoomData().getRoom().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    public boolean closeRoom(String room) {
        for (int i = 0; i < rooms.size(); i++) {
            RoomData roomData = rooms.get(i).getRoomData();
            if (roomData.getRoom().equals(room)) {
                roomData.clear();
                return true;
            }
        }
        return false;
    }

    public List<RoomData> getAllRoomsInfo() {
        List<RoomData> roomsInfo = new ArrayList<>();
        for (Room room :
                rooms) {
            roomsInfo.add(room.getRoomData());
        }
        return roomsInfo;
    }

    public void clearRooms() {
        for (int i = 0; i < rooms.size(); i++) {
            rooms.get(i).clear();
        }
    }
}
