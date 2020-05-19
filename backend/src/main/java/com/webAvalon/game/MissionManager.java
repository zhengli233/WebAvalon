package com.webAvalon.game;

import com.webAvalon.data.PlayerData;
import com.webAvalon.data.RoomData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionManager {
    private List<String> missionCandidates;
    private RoomData roomData;
    private Map<String, Boolean> missionEnableVotes;
    private Map<String, Boolean> missionSuccessVotes;
    private boolean isVoting;

    public MissionManager(RoomData roomData) {
        this.roomData = roomData;
        this.missionEnableVotes = new HashMap<>();
        this.missionSuccessVotes = new HashMap<>();
        this.isVoting = false;
    }

    public boolean setCandidates(List<String> playerNameList) {
        for (String playerName :
                playerNameList) {
            if (!roomData.containsPlayerName(playerName)) {
                return false;
            }
        }
        this.missionCandidates = playerNameList;
        missionEnableVotes.clear();
        missionSuccessVotes.clear();
        isVoting = true;
        return true;
    }

    public boolean missionEnableVote(String playerName, boolean vote) {
        if (!isVoting) {
            return false;
        }
        missionEnableVotes.put(playerName, vote);
        if (missionEnableVotes.size() == roomData.getPlayerNames().size()) {
            isVoting = false;
            return true;
        } else {
            return false;
        }
    }

    public Map<String, Boolean> getMissionEnableVotes() {
        return missionEnableVotes;
    }

    public boolean missionSuccessVote(String playerName, boolean vote) {
        if(!isVoting) {
            return false;
        }
        boolean contains = false;
        for (String candidateName :
                missionCandidates) {
            if (candidateName.equals(playerName)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            return false;
        }
        missionSuccessVotes.put(playerName, vote);
        if (missionSuccessVotes.size() == missionCandidates.size()) {
            isVoting = false;
            return true;
        } else {
            return false;
        }
    }

    public List<Boolean> getMissionSuccessVotes() {
        List<Boolean> result = new ArrayList<>();
        for (Boolean vote : missionSuccessVotes.values()) {
            result.add(vote);
        }
        return result;
    }

    public RoomData getRoomData() {
        return roomData;
    }

    public List<String> getMissionCandidates() {
        return missionCandidates;
    }

    public void clear() {
        missionCandidates.clear();
        missionEnableVotes.clear();
        missionSuccessVotes.clear();
        isVoting = false;
    }
}
