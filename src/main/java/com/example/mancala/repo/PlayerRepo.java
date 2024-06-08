package com.example.mancala.repo;


import com.example.mancala.entity.PlayerBoard;

public interface PlayerRepo {
    PlayerBoard createPlayerBoard (Integer pitAmount, Integer cellCount);
    public void purgeReward(PlayerBoard playerBoard, Integer amount);
    public void plusOneToStore(PlayerBoard playerBoard);
    public Integer purgeFromCell(PlayerBoard playerBoard, Integer cell);
    public Integer distribute(PlayerBoard playerBoard, Integer cellNumber, Integer leftOver);
    public Integer addAnotherPlayerAmount(PlayerBoard playerBoard, Integer amount);
    public void resetPurge(PlayerBoard playerBoard);
    public boolean isNotEmpty(PlayerBoard playerBoard);
    public void setTurn(PlayerBoard playerBoard, Boolean isYourTurn);
    Integer findPlayerPurgeIndex(PlayerBoard playerBoard);
}
