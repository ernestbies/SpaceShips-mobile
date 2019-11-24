package com.shipsgame.domain.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Ship implements Serializable {

    private int type;
    private String name;
    private List<String> positions = new ArrayList<>();

    public Ship(String name, int type) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", positions=" + positions +
                '}';
    }
}
