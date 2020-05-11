package com.webAvalon.controller;

import com.alibaba.fastjson.JSON;
import com.webAvalon.data.RuleData;
import com.webAvalon.game.RuleManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/rule")
public class RuleHandler {
    @MessageMapping("getRule")
    @SendTo("/topic/rule")
    public String getRule(String msg) {
        RuleData rule = JSON.parseObject(msg, RuleData.class);
        RuleData result = RuleManager.getRule(rule.getPlayerNumber());
        return JSON.toJSONString(result);
    }
}
