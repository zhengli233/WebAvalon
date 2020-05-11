package com.webAvalon.game;

import com.webAvalon.data.PlayerData;
import com.webAvalon.data.RoleData;
import com.webAvalon.data.RuleData;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleManager {
    public static RuleData getRule(int playerNumber) {
        RuleData rule = new RuleData();
        rule.setPlayerNumber(playerNumber);
        rule.addRole("梅林");
        rule.addRole("派西维尔");
        rule.addRole("莫甘娜");
        rule.addRole("刺客");
        switch (playerNumber) {
            case 5:
                rule.addRole("忠臣");
                rule.addCandidateNumberByRound(2);
                rule.addCandidateNumberByRound(3);
                rule.addCandidateNumberByRound(2);
                rule.addCandidateNumberByRound(3);
                rule.addCandidateNumberByRound(3);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                break;
            case 6:
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addCandidateNumberByRound(2);
                rule.addCandidateNumberByRound(3);
                rule.addCandidateNumberByRound(4);
                rule.addCandidateNumberByRound(3);
                rule.addCandidateNumberByRound(4);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                break;
            case 7:
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addRole("奥伯伦");
                rule.addCandidateNumberByRound(2);
                rule.addCandidateNumberByRound(3);
                rule.addCandidateNumberByRound(3);
                rule.addCandidateNumberByRound(4);
                rule.addCandidateNumberByRound(4);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(1);
                rule.addFailureVoteToleranceByRound(0);
                break;
            case 8:
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addRole("爪牙");
                rule.addCandidateNumberByRound(3);
                rule.addCandidateNumberByRound(4);
                rule.addCandidateNumberByRound(4);
                rule.addCandidateNumberByRound(5);
                rule.addCandidateNumberByRound(5);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(1);
                rule.addFailureVoteToleranceByRound(0);
                break;
            case 9:
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addRole("莫德雷德");
                rule.addCandidateNumberByRound(3);
                rule.addCandidateNumberByRound(4);
                rule.addCandidateNumberByRound(4);
                rule.addCandidateNumberByRound(5);
                rule.addCandidateNumberByRound(5);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(1);
                rule.addFailureVoteToleranceByRound(0);
                break;
            case 10:
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addRole("忠臣");
                rule.addRole("莫德雷德");
                rule.addRole("奥伯伦");
                rule.addCandidateNumberByRound(3);
                rule.addCandidateNumberByRound(4);
                rule.addCandidateNumberByRound(4);
                rule.addCandidateNumberByRound(5);
                rule.addCandidateNumberByRound(5);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(0);
                rule.addFailureVoteToleranceByRound(1);
                rule.addFailureVoteToleranceByRound(0);
                break;
            default:
                return null;
        }
        return rule;
    }

    public static void setRoles(List<PlayerData> players) {
        RuleData rule = getRule(players.size());
        List<String> roles = rule.getRoles();
        Collections.shuffle(roles);
        List<RoleData> merlin = new ArrayList<>();
        List<RoleData> percival = new ArrayList<>();
        List<RoleData> evil = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            String role = roles.get(i);
            players.get(i).setRole(role);
            switch (role) {
                case "梅林":
                    percival.add(new RoleData("梅林或莫甘娜", players.get(i).getName()));
                    break;
                case "莫甘娜":
                    percival.add(new RoleData("梅林或莫甘娜", players.get(i).getName()));
                    merlin.add(new RoleData("坏人", players.get(i).getName()));
                    evil.add(new RoleData("坏人队友", players.get(i).getName()));
                    break;
                case "刺客":
                case "爪牙":
                    merlin.add(new RoleData("坏人", players.get(i).getName()));
                    evil.add(new RoleData("坏人队友", players.get(i).getName()));
                    break;
                case "奥伯伦":
                    merlin.add(new RoleData("坏人", players.get(i).getName()));
                    break;
                case "莫德雷德":
                    evil.add(new RoleData("坏人队友", players.get(i).getName()));
                    break;
            }
        }
        for (PlayerData player:
             players) {
            switch(player.getRole()) {
                case "梅林":
                    player.setInfo(merlin);
                    break;
                case "派西维尔":
                    player.setInfo(percival);
                    break;
                case "刺客":
                case "爪牙":
                case "莫甘娜":
                case "莫德雷德":
                    player.setInfo(evil);
                    break;
            }
        }
    }
}
