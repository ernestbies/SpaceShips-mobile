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

    String getCode() {
        return code;
    }

    String getShipName() {
        return shipName;
    }

    int getType() {
        return type;
    }

    int getSteps() {
        return steps;
    }

    String getBoard() {
        return board;
    }
}
