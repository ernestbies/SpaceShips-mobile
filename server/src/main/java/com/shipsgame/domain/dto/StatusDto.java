package com.shipsgame.domain.dto;

public class StatusDto {
    private int steps;
    private String code;
    private String shipName;
    private String board;
    private int type;

    public StatusDto(String code, String shipName, int type, int steps, String board){
        this.code = code;
        this.shipName = shipName;
        this.type = type;
        this.steps = steps;
        this.board = board;
    }

    public int getSteps() {
        return steps;
    }

    public String getCode() {
        return code;
    }

    public String getShipName() {
        return shipName;
    }

    public int getType() {
        return type;
    }

    public String getBoard() {
        return board;
    }

}
