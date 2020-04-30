package com.webAvalon.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Arrays;

import com.webAvalon.data.VoteEnableJsonData;

@Controller
@MessageMapping("/MissionEnableVote")
public class MissionEnableVoteHandler {
    private int playerNumber;
    private int gameRound;
    private boolean[] voted = new boolean[10];
    private boolean[] voteResult = new boolean[10];

    @MessageMapping("/start")
    public void start(String msg) {
        Arrays.fill(voted, false);
        VoteEnableJsonData data = JSON.parseObject(msg, VoteEnableJsonData.class);
        playerNumber = data.getPlayerNumber();
        gameRound = data.getGameRound();
    }

    @MessageMapping("/vote")
    public void vote(String msg) {
        VoteEnableJsonData data = JSON.parseObject(msg, VoteEnableJsonData.class);
        voted[data.getPlayerIndex() - 1] = true;
        voteResult[data.getPlayerIndex() - 1] = data.getVote();

        boolean finished = true;
        for (int i = 0; i < playerNumber; i++) {
            finished &= voted[i];
        }
        if (finished) {
            int agree = 0;
            for (int i = 0; i < playerNumber; i++) {
                if (voteResult[i]) {
                    agree += 1;
                }
            }
            boolean enable = agree > playerNumber / 2;
            // TODO: broadcast result
        }
    }

    @MessageMapping("/welcome")
    @SendTo("/topic/result")
    public String result(String msg) {
        System.out.println(msg);
        return "test";
    }
}
