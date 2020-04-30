package com.webAvalon.scheduled;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledSender {
    @Scheduled(cron = "0/5 * *  * * ? ")
    @SendTo("/topic/result")
    public String task() {
        System.out.println("sender!!");
        return "5s past";
    }
}
