package com.webAvalon.handler;

import com.alibaba.fastjson.JSON;
import com.webAvalon.data.PlayerData;
import com.webAvalon.data.RoomData;
import com.webAvalon.game.RoomManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/host")
public class HostHandler {
    private RoomManager manager = RoomManager.getInstance();

    @MessageMapping("/create")
    @SendToUser("/topic/roomInfo")
    public String createRoom(String msg) {
        System.out.println(msg);
        RoomData roomData = JSON.parseObject(msg, RoomData.class);
        int players = roomData.getRoomSize();
        PlayerData player = roomData.getPlayers().get(0);
        RoomData newRoom = manager.createRoom(players, player);
        return JSON.toJSONString(newRoom);
    }

    @MessageMapping("/enter")
    @SendTo("/topic/roomInfo")
    public String enter(String msg) {
        System.out.println(msg);
        RoomData roomData = JSON.parseObject(msg, RoomData.class);
        String room = roomData.getRoom();
        PlayerData player = roomData.getPlayers().get(0);
        roomData = manager.enterRoom(room, player);
        return JSON.toJSONString(roomData);
    }
}
