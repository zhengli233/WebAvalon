package com.webAvalon.controller;

import com.alibaba.fastjson.JSON;
import com.webAvalon.data.MissionSuccessVoteData;
import com.webAvalon.game.MissionManager;
import com.webAvalon.game.RoomManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@MessageMapping("/MissionSuccessVote")
public class MissionSuccessVoteHandler {
    @Resource
    private SimpMessagingTemplate sender;

    @MessageMapping("/start")
    public void start(String msg) {
        MissionSuccessVoteData data = JSON.parseObject(msg, MissionSuccessVoteData.class);
        String room = data.getRoom();
        RoomManager.getInstance().getDealer(room).setMissionSuccessVote(data.getPlayerNameList());
    }

    @MessageMapping("/vote")
    public void vote(String msg) {
        MissionSuccessVoteData data = JSON.parseObject(msg, MissionSuccessVoteData.class);
        MissionManager dealer = RoomManager.getInstance().getDealer(data.getRoom());
        boolean finish = dealer.missionSuccessVote(data.getPlayerNameList().get(0), data.getVote());
        if (finish) {
            broadcastMissionSuccessVoteResult(dealer.getMissionSuccessVotes());
        }
    }

    private void broadcastMissionSuccessVoteResult(List<Boolean> result) {
        sender.convertAndSend("/topic/missionSuccessVoteResult", JSON.toJSONString(result));
    }
}
