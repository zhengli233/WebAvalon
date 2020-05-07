package com.webAvalon.data;

import java.util.List;

public class PlayerData {
    private int index;
    private String name;
    private String role;
    private boolean ready;
    private List<PlayerData> info;

    public int getIndex() {
        return index;
    }

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

    public List<PlayerData> getInfo() {
        return info;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setInfo(List<PlayerData> info) {
        this.info = info;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void addInfo(PlayerData data) {
        this.info.add(data);
    }
}
