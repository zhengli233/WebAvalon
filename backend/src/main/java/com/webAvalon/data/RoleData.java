package com.webAvalon.data;

public class RoleData {
    private String roleName;
    private String playerName;

    public RoleData() {

    }

    public RoleData(String roleName, String playerName) {
        this.roleName = roleName;
        this.playerName = playerName;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
