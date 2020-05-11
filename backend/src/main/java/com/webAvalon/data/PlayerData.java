package com.webAvalon.data;

import java.util.List;

public class PlayerData {
    private String name;
    private String role;
    private boolean ready;
    private List<RoleData> info;

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public boolean isReady() {
        return ready;
    }

    public boolean getReady() {
        return ready;
    }

    public List<RoleData> getInfo() {
        return info;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setInfo(List<RoleData> info) {
        this.info = info;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void addInfo(RoleData data) {
        this.info.add(data);
    }
}
