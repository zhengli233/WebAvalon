package com.webAvalon.handler;

import com.alibaba.fastjson.JSON;
import com.webAvalon.data.VoteSuccessJsonData;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@MessageMapping("/MissionSuccessVote")
public class MissionSuccessVoteHandler {
    private List<Integer> votePlayerIndexes;
    private boolean[] voteResult = new boolean[10];
    private int gameRound;
    private boolean[] voted = new boolean[10];

    @MessageMapping("/start")
    public void start(String msg) {
        VoteSuccessJsonData data = JSON.parseObject(msg, VoteSuccessJsonData.class);
        votePlayerIndexes = data.getVotePlayerIndexes();
        gameRound = data.getGameRound();
    }

    @MessageMapping("/vote")
    public void vote(String msg) {
        VoteSuccessJsonData data = JSON.parseObject(msg, VoteSuccessJsonData.class);
        voteResult[data.getPlayerIndex() - 1] = data.getVote();
        voted[data.getPlayerIndex() - 1] = true;
        for (int i = 0; i < votePlayerIndexes.size(); i++) {
            if (voted[votePlayerIndexes.get(i)] == false) {
                return;
            }
        }
        boolean result = true;
        for (int i = 0; i < votePlayerIndexes.size(); i++) {
            if (voteResult[votePlayerIndexes.get(i)] == false) {
                result = false;
                break;
            }
        }
        // TODO: broadcast result
    }
}
