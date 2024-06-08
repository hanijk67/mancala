package com.example.mancala.entity;

import java.util.ArrayList;
import java.util.List;

public class MancalaBoard {

    private List<PlayerBoard> players = new ArrayList<>();

    public MancalaBoard(List<PlayerBoard> players) {
        this.players = players;
    }

    public List<PlayerBoard> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerBoard> players) {
        this.players = players;
    }
}
