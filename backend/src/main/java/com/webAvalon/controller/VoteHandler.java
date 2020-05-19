package com.webAvalon.controller;

import com.alibaba.fastjson.JSON;
import com.webAvalon.game.MissionManager;
import com.webAvalon.game.RoomManager;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

import com.webAvalon.data.VoteData;

import javax.annotation.Resource;

@Controller
public class VoteHandler {
    @Resource
    private SimpMessagingTemplate sender;

    @MessageMapping("/setCandidates/{room}")
    public void setCandidates(@DestinationVariable String room, String msg) {
        VoteData data = JSON.parseObject(msg, VoteData.class);
        List<String> playerNameList = data.getCandidateList();
        boolean status = RoomManager.getInstance().getDealer(room).setCandidates(playerNameList);
        broadcastCandidateList(room, status);
    }

    @MessageMapping("/missionEnableVote/{room}")
    public void missionEnableVote(@DestinationVariable String room, String msg) {
        VoteData data = JSON.parseObject(msg, VoteData.class);
        MissionManager dealer = RoomManager.getInstance().getDealer(room);
        boolean finish = dealer.missionEnableVote(data.getPlayerName(), data.getVote());
        if (finish) {
            broadcastMissionEnableResult(room, dealer.getMissionEnableVotes());
        }
    }

    @MessageMapping("/missionSuccessVote/{room}")
    public void missionSuccessVote(@DestinationVariable String room, String msg) {
        VoteData data = JSON.parseObject(msg, VoteData.class);
        MissionManager dealer = RoomManager.getInstance().getDealer(data.getRoom());
        boolean finish = dealer.missionSuccessVote(data.getPlayerName(), data.getVote());
        if (finish) {
            broadcastMissionSuccessVoteResult(room, dealer.getMissionSuccessVotes());
        }
    }

    private void broadcastMissionEnableResult(String room, Map<String, Boolean> result) {
        sender.convertAndSend("/topic/missionEnableVoteResult/" + room, JSON.toJSONString(result));
    }

    private void broadcastCandidateList(String room, boolean status) {
        if(!status) {
            sender.convertAndSend("/topic/candidateList/" + room, "");
        }
        MissionManager dealer = RoomManager.getInstance().getDealer(room);
        sender.convertAndSend("/topic/candidateList/" + room, JSON.toJSONString(dealer.getMissionCandidates()));
    }

    private void broadcastMissionSuccessVoteResult(String room, List<Boolean> result) {
        sender.convertAndSend("/topic/missionSuccessVoteResult/" + room, JSON.toJSONString(result));
    }
}
