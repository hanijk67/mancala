package com.example.mancala.entity;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {

    private Integer storeAmount;
    private Boolean isYourTurn;
    private Integer purgeIndex;
    private List<Pit> pits = new ArrayList<>();


//    public PlayerBoard(Integer pitAmount, Integer cellCount) {
    public PlayerBoard(List<Pit> pits) {
        this.pits = pits;
        storeAmount = 0;
        isYourTurn = false;

    }

    public Integer getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(Integer storeAmount) {
        this.storeAmount = storeAmount;
    }

    public List<Pit> getPits() {
        return pits;
    }


    public void setYourTurn(Boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public Integer getPurgeIndex() {
        return purgeIndex;
    }

    public Boolean getYourTurn() {
        return isYourTurn;
    }

    public void setPurgeIndex(Integer purgeIndex) {
        this.purgeIndex = purgeIndex;
    }
}
