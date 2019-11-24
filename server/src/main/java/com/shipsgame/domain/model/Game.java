package com.shipsgame.domain.model;

import com.shipsgame.domain.dto.StatusDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game implements Serializable {
    private int steps; //number of steps
    private String user; //username
    private char[] board; //current state of board
    private List<Ship> shipsList; //list of user's ships
    private int[][] boardNumbers; //number of fields occupied by ships around the selected position

    //constructor
    public Game(String user) {
        this.user = user;
        this.steps = 0;
        this.board = new char[81];
        this.shipsList = new ArrayList<>();
        this.boardNumbers = new int[9][9];
    }

    //function to get game status
    public StatusDto getStatus() {
        return (new StatusDto("LOADGAME", "", 0, steps, new String(board)));
    }

    //function to get number of steps
    public int getSteps() {
        return steps;
    }

    //function to get username
    public String getUser() {
        return user;
    }

    //function to get current state of board
    public char[] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "Game{" +
                "steps=" + steps +
                ", user='" + user + '\'' +
                ", board=" + Arrays.toString(board) +
                ", shipsList=" + shipsList +
                ", boardNumbers=" + Arrays.toString(boardNumbers) +
                '}';
    }
}
