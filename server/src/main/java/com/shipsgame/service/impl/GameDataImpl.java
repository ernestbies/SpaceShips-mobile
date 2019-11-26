package com.shipsgame.service.impl;

import com.shipsgame.domain.dto.StatusDto;
import com.shipsgame.domain.model.Game;
import com.shipsgame.service.GameDataService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameDataImpl implements GameDataService {

    private List<Game> games = null; //list of all users games

    //checking if there is a set of user games created
    //the set of games must only be created once
    private static GameDataImpl instance = null;

    //static method to get instance
    public static GameDataImpl getInstance() {
        if (instance == null) {
            instance = new GameDataImpl();
        }
        return instance;
    }

    //constructor which creates list of games
    public GameDataImpl() {
        File file = new File("games.dat");
        if (file.exists()) {
        } else {
            games = new ArrayList<Game>();
        }
    }

    //method to get user game
    @Override
    public StatusDto getGame(String user) {
        for (Game g : games) {
            if (g.getUser().contains(user)) {
                return g.getStatus();
            }
        }
        return (new StatusDto("NOGAME", "", 0, 0, ""));
    }

    //method to create new game for a user
    @Override
    public StatusDto newGame(String user) {
        Game newGame = new Game(user);
        for (Game g : games) {
            if (g.getUser().contains(user)) {
                int gameIndex = games.indexOf(g);
                games.set(gameIndex, newGame);
                return (new StatusDto("NEWGAME", "", 0, newGame.getSteps(), new String(newGame.getBoard())));
            }
        }
        games.add(newGame);
        return (new StatusDto("NEWGAME", "", 0, newGame.getSteps(), new String(newGame.getBoard())));
    }

    //method to check shot, String shot is a position for example: 00, 10, 11, 22...
    @Override
    public StatusDto shotGame(String user, String shot) {
        for (Game g : games) {
            if (g.getUser().contains(user)) {
                StatusDto status = g.shot(shot);
                return status;
            }
        }
        return (new StatusDto("SHOT", "", 0, 0, ""));
    }
}
