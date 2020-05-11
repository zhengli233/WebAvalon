package com.webAvalon.data;

import org.apache.tomcat.util.digester.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleData {
    private int playerNumber;
    private List<String> roles;
    private List<Integer> candidateNumberByRound;
    private List<Integer> failureVoteToleranceByRound;

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
