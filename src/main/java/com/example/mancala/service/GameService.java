package com.example.mancala.service;

import com.example.mancala.entity.PlayerBoard;

import java.util.List;
import java.util.Map;

public interface GameService {

    void newGame(Integer pitsAmount, Integer cellCount);
    Integer getPlayerCount();
    void setStarterPlayer(Integer starterPlayer);
    void distributeCell(Integer cellNumber);
    Integer getNextPlayer();
    Boolean gameIsOver();
    List<PlayerBoard> getPlayers();
    void resetGame();
    void checkCellValidation(Integer cell);
    void checkGameValidation();
    void checkStarterPlayerValidation(Integer playerIndex);
    void collectFromPits();
}
