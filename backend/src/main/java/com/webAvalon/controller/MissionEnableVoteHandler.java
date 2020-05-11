package com.webAvalon.controller;

import com.alibaba.fastjson.JSON;
import com.webAvalon.data.PlayerData;
import com.webAvalon.game.MissionManager;
import com.webAvalon.game.RoomManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

import com.webAvalon.data.MissionEnableVoteData;

import javax.annotation.Resource;

@Controller
@MessageMapping("/MissionEnableVote")
public class MissionEnableVoteHandler {
    @Resource
    private SimpMessagingTemplate sender;

    @MessageMapping("/start")
    public void start(String msg) {
        MissionEnableVoteData data = JSON.parseObject(msg, MissionEnableVoteData.class);
        String room = data.getRoom();
        List<String> playerNameList = data.getPlayerNameList();
        RoomManager.getInstance().getDealer(room).setMissionEnableVote(playerNameList);
    }

    @MessageMapping("/vote")
    public void vote(String msg) {
        MissionEnableVoteData data = JSON.parseObject(msg, MissionEnableVoteData.class);
        MissionManager dealer = RoomManager.getInstance().getDealer(data.getRoom());
        boolean finish = dealer.missionEnableVote(data.getPlayerNameList().get(0), data.getVote());
        if (finish) {
            broadcastMissionEnableResult(dealer.getMissionEnableVotes());
        }
    }

    private void broadcastMissionEnableResult(Map<String, Boolean> result) {
        sender.convertAndSend("/topic/missionEnableVoteResult", JSON.toJSONString(result));
    }
}
