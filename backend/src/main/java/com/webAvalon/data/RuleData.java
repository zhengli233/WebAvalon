package com.webAvalon.data;

import org.apache.tomcat.util.digester.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleData {
    // 这些信息只是游戏规则，玩家的角色分配不在其中
    private int playerNumber;                           // 游戏人数，一旦游戏人数确定，下面3个就能确定了
    private List<String> roles;                         // 角色配置，长度为游戏人数
    private List<Integer> candidateNumberByRound;       // 每轮任务需要的玩家人数，哪些玩家做这个任务是要经过讨论和投票的，过半数同意后才能让这些玩家做任务
    private List<Integer> failureVoteToleranceByRound;  // 每轮任务允许出现的失败票个数，当确定了做任务的玩家，这些玩家会以匿名形式投成功或者失败票，出现超过允许的失败票个数即视为该轮任务失败

    public RuleData() {
        roles = new ArrayList<>();
        candidateNumberByRound = new ArrayList<>();
        failureVoteToleranceByRound = new ArrayList<>();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<Integer> getCandidateNumberByRound() {
        return candidateNumberByRound;
    }

    public List<Integer> getFailureVoteToleranceByRound() {
        return failureVoteToleranceByRound;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    public void setCandidateNumberByRound(List<Integer> candidateNumberByRound) {
        this.candidateNumberByRound = candidateNumberByRound;
    }

    public void addCandidateNumberByRound(int number) {
        this.candidateNumberByRound.add(number);
    }

    public void setFailureVoteToleranceByRound(List<Integer> failureVoteToleranceByRound) {
        this.failureVoteToleranceByRound = failureVoteToleranceByRound;
    }

    public void addFailureVoteToleranceByRound(int number) {
        this.failureVoteToleranceByRound.add(number);
    }
}
