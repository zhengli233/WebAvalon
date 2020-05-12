package com.webAvalon.controller;

import com.alibaba.fastjson.JSON;
import com.webAvalon.data.RuleData;
import com.webAvalon.game.Room;
import com.webAvalon.game.RoomManager;
import com.webAvalon.game.RuleManager;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@MessageMapping("/rule")
public class RuleHandler {
    @Resource
    private SimpMessagingTemplate sender;

    @MessageMapping("/getRule/{room}")
    public void getRule(@DestinationVariable String room, String msg) {
        RuleData rule = JSON.parseObject(msg, RuleData.class);
        RuleData result = RuleManager.getRule(rule.getPlayerNumber());
        sender.convertAndSend("/topic/rule/" + room, JSON.toJSONString(result));
    }

    @MessageMapping("/setRoles/{room}")
    public void setRoles(@DestinationVariable String room) {
        Room roomInstance = RoomManager.getInstance().getRoom(room);
        RuleManager.setRoles(roomInstance.getPlayers());
        sender.convertAndSend("/topic/setRoles/" + room, true);
    }
}
