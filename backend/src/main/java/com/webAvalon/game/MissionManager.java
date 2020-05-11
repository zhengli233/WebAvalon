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

    public MissionManager(RoomData roomData) {
        this.roomData = roomData;
        this.missionEnableVotes = new HashMap<>();
    }

    public void setMissionEnableVote(List<String> playerNameList) {
        for (String playerName :
                playerNameList) {
            if (!roomData.containsPlayerName(playerName)) {
                return;
            }
        }
        this.missionCandidates = playerNameList;
        missionEnableVotes.clear();
    }

    public boolean missionEnableVote(String playerName, boolean vote) {
        missionEnableVotes.put(playerName, vote);
        if (missionEnableVotes.size() == roomData.getPlayerNames().size()) {
            return true;
        } else {
            return false;
        }
    }

    public Map<String, Boolean> getMissionEnableVotes() {
        return missionEnableVotes;
    }

    public void setMissionSuccessVote(List<String> playerNameList) {
        this.missionCandidates = playerNameList;
        missionSuccessVotes.clear();
    }

    public boolean missionSuccessVote(String playerName, boolean vote) {
        missionSuccessVotes.put(playerName, vote);
        if (missionSuccessVotes.size() == missionCandidates.size()) {
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

    public void clear() {
        missionCandidates.clear();
        missionEnableVotes.clear();
        missionSuccessVotes.clear();
    }
}
