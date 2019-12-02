package com.space.ships;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ServerResponse {

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("shipName")
    @Expose
    private String shipName;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("steps")
    @Expose
    private int steps;

    @SerializedName("board")
    @Expose
    private String board;

    public String getCode() {
        return code;
    }

    public String getShipName() {
        return shipName;
    }

    public int getType() {
        return type;
    }

    public int getSteps() {
        return steps;
    }

    public String getBoard() {
        return board;
    }
}
