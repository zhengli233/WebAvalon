package com.webAvalon.verify;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageVerify {
    @MessageMapping("/topic/missionEnableVoteResult")
    public void test(String msg) {
        System.out.println(msg);
    }

    @MessageMapping("/topic/roomInfo")
    public void testRoomInfo(String msg) {
        System.out.println(msg);
    }
}
