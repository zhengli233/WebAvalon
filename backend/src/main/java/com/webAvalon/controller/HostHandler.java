package com.webAvalon.controller;

import com.alibaba.fastjson.JSON;
import com.webAvalon.data.PlayerData;
import com.webAvalon.data.RoomData;
import com.webAvalon.game.RoomManager;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@MessageMapping("/host")
public class HostHandler {
    private RoomManager manager = RoomManager.getInstance();
    @Resource
    private SimpMessagingTemplate sender;


    @MessageMapping("/enterRoom")
    @SendToUser("/topic/enterRoomStatus")
    public String enterRoom(String msg) {
        System.out.println(msg);
        RoomData roomData = JSON.parseObject(msg, RoomData.class);
        String room = roomData.getRoom();
        String playerName = roomData.getPlayerNames().get(0);
        if (manager.enterRoom(room, playerName) == null) {
            broadcastAllRoomsInfo();
            broadcastRoomInfo(room);
            return JSON.toJSONString(false);
        } else {
            broadcastAllRoomsInfo();
            broadcastRoomInfo(room);
            return JSON.toJSONString(true);
        }
    }

    @MessageMapping("/getAllRooms")
    public void getAllRoomsInfo() {
        broadcastAllRoomsInfo();
    }

    @MessageMapping("/getRoomInfo/{room}")
    public void getRoomsInfo(@DestinationVariable String room) {
        broadcastRoomInfo(room);
    }

    @MessageMapping("/leaveRoom/{room}")
    public void leaveRoom(@DestinationVariable String room, String msg) {
        PlayerData playerData = JSON.parseObject(msg, PlayerData.class);
        String playerName = playerData.getName();
        manager.leaveRoom(room, playerName);
        broadcastRoomInfo(room);
        broadcastAllRoomsInfo();
    }

    @MessageMapping("/clearAllRooms")
    public void clearAllRooms() {
        manager.clearRooms();
        broadcastAllRoomsInfo();
    }

    private void broadcastAllRoomsInfo() {
        sender.convertAndSend("/topic/allRoomsInfo", JSON.toJSONString(manager.getAllRoomsInfo()));
    }

    private void broadcastRoomInfo(String roomName) {
        sender.convertAndSend("/topic/roomInfo/" + roomName, JSON.toJSONString(manager.getRoomData(roomName)));
    }
}
