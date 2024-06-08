package com.example.mancala.entity.helper;

import com.example.mancala.entity.PlayerBoard;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMancala {

    private String valuesSecondPlayer;
    private String valuesFirstPlayer;
    private String nextPlayer;
    private String content;

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public String getValuesFirstPlayer() {
        return valuesFirstPlayer;
    }

    public void setValuesFirstPlayer(String valuesFirstPlayer) {
        this.valuesFirstPlayer = valuesFirstPlayer;
    }

    public String getValuesSecondPlayer() {
        return valuesSecondPlayer;
    }

    public void setValuesSecondPlayer(String valuesSecondPlayer) {
        this.valuesSecondPlayer = valuesSecondPlayer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
