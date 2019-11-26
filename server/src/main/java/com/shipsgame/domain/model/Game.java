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

    //function which generates ships on board
    private void boardGenerating() {
        int length, orientation, pozX, pozY, number;
        int xmin, xmax, ymin, ymax;
        boolean find;

        for (int i = 0; i < 81; i++) {
            board[i] = ' ';
        }

        //creating spaceships and adding them to list
        shipsList.add(new Ship("Transportowiec", 4));
        shipsList.add(new Ship("Transportowiec", 4));
        shipsList.add(new Ship("Samolot kosmiczny", 3));
        shipsList.add(new Ship("Samolot kosmiczny", 3));
        shipsList.add(new Ship("Samolot kosmiczny", 3));
        shipsList.add(new Ship("Wahadłowiec", 2));
        shipsList.add(new Ship("Wahadłowiec", 2));
        shipsList.add(new Ship("Wahadłowiec", 2));
        shipsList.add(new Ship("Szturmowiec", 1));
        shipsList.add(new Ship("Szturmowiec", 1));

        for (int i = 0; i < shipsList.size(); i++) {
            find = false;
            //find = true - found positions at which the ship will not go beyond the boards
            //find = false - NOT found positions at which the ship will not go beyond the boards
            while (!find) {
                orientation = (int) (Math.random() * 2);
                //orientation - 0: horizontal, 1: vertical
                pozX = (int) (Math.random() * 9);
                //generates random position X for ship on board
                pozY = (int) (Math.random() * 9);
                //generates random position Y for ship on board

                if (orientation == 0) {
                    if ((pozX + shipsList.get(i).getType()) < 9) {
                        List<String> tmpPos = new ArrayList<>();
                        find = true;
                        //find = true - found positions for the ship successfully, fits on the board
                        length = 0;
                        //temporary variable for length of ships depended on ship type
                        //for example: type = 4 (length: 0, 1, 2, 3), type = 2 (length: 0,1)
                        while ((length < shipsList.get(i).getType()) & find) {
                            //check if positions on board are not occupied by other ships
                            number = pozX + length;
                            if (boardNumbers[number][pozY] != 0) {
                                //boardNumbers[number][pozY] == 0 - position is not occupied
                                //boardNumbers[number][pozY] != 0 - position is occupied
                                find = false;
                            }
                            if (number - 1 >= 0) {
                                //ships can't touch each others, check if position is not occupied and fits on the board
                                if (boardNumbers[number - 1][pozY] != 0) {
                                    find = false;
                                }
                            }
                            if (number + 1 < 9) {
                                if (boardNumbers[number + 1][pozY] != 0) {
                                    find = false;
                                }
                            }
                            if (pozY - 1 >= 0) {
                                if (boardNumbers[number][pozY - 1] != 0) {
                                    find = false;
                                }
                            }
                            if (pozY + 1 < 9) {
                                if (boardNumbers[number][pozY + 1] != 0) {
                                    find = false;
                                }
                            }
                            length++;
                            //I need check the entire length of the ship depended on its type
                        }
                        if (find) {
                            //if (find) - I checked positions, positions are not occupied, ship can be placed on board
                            for (int m = 0; m < shipsList.get(i).getType(); m++) {
                                number = pozX + m;
                                boardNumbers[number][pozY] = (shipsList.get(i).getType()) * (-1);
                                //multiply by (-1) because later I must know what positions are occupied by ships
                                //negative numbers represents the postions of ships
                                //positive numbers represents the number of occupied positions around
                                tmpPos.add("" + number + "" + pozY);
                                //every ship has own list positions which occupies on board (tmpPos)
                            }
                            shipsList.get(i).setPositions(tmpPos);
                            //add ships positions to list
                        }
                    }
                } else {
                    //Same operations for vertical orientation
                    if ((pozY + shipsList.get(i).getType()) < 9) {
                        List<String> tmpPos = new ArrayList<>();
                        find = true;
                        length = 0;
                        while ((length < shipsList.get(i).getType()) & find) {
                            number = pozY + length;
                            if (boardNumbers[pozX][number] != 0) {
                                find = false;
                            }
                            if (number - 1 >= 0) {
                                if (boardNumbers[pozX][number - 1] != 0) {
                                    find = false;
                                }
                            }
                            if (number + 1 < 9) {
                                if (boardNumbers[pozX][number + 1] != 0) {
                                    find = false;
                                }
                            }
                            if (pozX - 1 >= 0) {
                                if (boardNumbers[pozX - 1][number] != 0) {
                                    find = false;
                                }
                            }
                            if (pozX + 1 < 9) {
                                if (boardNumbers[pozX + 1][number] != 0) {
                                    find = false;
                                }
                            }
                            length++;
                        }
                        if (find) {
                            for (int m = 0; m < shipsList.get(i).getType(); m++) {
                                number = pozY + m;
                                boardNumbers[pozX][number] = (shipsList.get(i).getType()) * (-1);
                                tmpPos.add("" + pozX + "" + number);
                            }
                            shipsList.get(i).setPositions(tmpPos);
                        }
                    }
                }
            }
        }

        //for each ship, calculating how many ships are around
        //xmin - xmax -> range checked for position X
        //ymin - ymax -> range checked for position Y
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (boardNumbers[x][y] >= 0) {
                    ymin = y - 1;
                    ymax = y + 1;
                    if (ymin < 0) {
                        ymin = 0;
                    }
                    if (ymax > 8) {
                        ymax = 8;
                    }
                    xmin = x - 1;
                    xmax = x + 1;
                    if (xmin < 0) {
                        xmin = 0;
                    }
                    if (xmax > 8) {
                        xmax = 8;
                    }

                    number = 0;
                    for (int yp = ymin; yp <= ymax; yp++) {
                        for (int xp = xmin; xp <= xmax; xp++) {
                            if (!(xp == x && yp == y)) {
                                if (boardNumbers[xp][yp] < 0) {
                                    number++;
                                }
                            }
                        }
                    }
                    //number - how many positions are occupied by ships
                    boardNumbers[x][y] = number;
                }
            }
        }
    }

    //toString() function
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
