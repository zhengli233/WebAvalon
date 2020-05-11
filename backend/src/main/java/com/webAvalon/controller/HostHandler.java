package com.webAvalon.controller;

import com.alibaba.fastjson.JSON;
import com.webAvalon.data.PlayerData;
import com.webAvalon.data.RoomData;
import com.webAvalon.game.RoomManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@MessageMapping("/host")
public class HostHandler {
    private RoomManager manager = RoomManager.getInstance();
    @Resource
    private SimpMessagingTemplate sender;


    @MessageMapping("/enterRoom")
    @SendToUser("/topic/roomInfo")
    public String enterRoom(String msg) {
        System.out.println(msg);
        RoomData roomData = JSON.parseObject(msg, RoomData.class);
        String room = roomData.getRoom();
        String playerName = roomData.getPlayerNames().get(0);
        roomData = manager.enterRoom(room, playerName);
        broadcastAllRoomsInfo();
        return JSON.toJSONString(roomData);
    }

    @MessageMapping("/getAllRooms")
    public void getAllRoomsInfo() {
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
}
