package com.example.mancala.repo;

import com.example.mancala.entity.Pit;
import com.example.mancala.entity.PlayerBoard;
import com.example.mancala.serviceEndPoints.eception.MancalaException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PLayerBoardRepo implements PlayerRepo {

    @Override
    public PlayerBoard createPlayerBoard(Integer pitAmount, Integer cellCount) {

        List<Pit> pits = new ArrayList<>();
        for (int i = 0; i < cellCount; i++) {
            pits.add(new Pit(pitAmount));
        }
        return new PlayerBoard(pits);
    }

    @Override
    public void purgeReward(PlayerBoard playerBoard, Integer amount) {
        addToStore(playerBoard, amount);
        playerBoard.setYourTurn(false);
        resetPurge(playerBoard);
    }

    private void addToStore(PlayerBoard playerBoard, Integer amount) {
        playerBoard.setStoreAmount(playerBoard.getStoreAmount() + amount);
    }

    @Override
    public void plusOneToStore(PlayerBoard playerBoard) {
        addToStore(playerBoard, 1);
    }


    private void checkPurgeCell(PlayerBoard playerBoard, Integer amount, Integer cellNumber) {
        if (amount == 1 && playerBoard.getYourTurn() &&
                cellNumber < playerBoard.getPits().size() - 1 &&
                playerBoard.getPits().get(cellNumber + 1).getEmpty()) {
            plusOneToStore(playerBoard);
            playerBoard.setPurgeIndex(cellNumber + 2);
            throw new MancalaException.PurgeFromOppositeException();
        }
    }

    @Override
    public Integer purgeFromCell(PlayerBoard playerBoard, Integer cell) {
        return playerBoard.getPits().get(cell - 1).take();
    }

    @Override
    public Integer distribute(PlayerBoard playerBoard, Integer cellNumber, Integer leftOver) {
        resetPurge(playerBoard);
        --cellNumber;
        if (leftOver == null && playerBoard.getPits().get(cellNumber).getEmpty()) {
            throw new MancalaException.CellIsEmptyException();
        }
        Integer amount;
        if (leftOver != null) {
            amount = leftOver;
            --cellNumber;
        } else {
            amount = playerBoard.getPits().get(cellNumber).take();
        }
        checkPurgeCell(playerBoard, amount, cellNumber);
        for (int i = cellNumber + 1; i < playerBoard.getPits().size(); i++) {
            checkPurgeCell(playerBoard, amount, i - 1);
            playerBoard.getPits().get(i).add();
            if (amount - 1 == 0) {
                return -1;
            }
            --amount;
        }
        if (amount > 0) {
            plusOneToStore(playerBoard);
            --amount;
        }
        return amount;
    }

    @Override
    public Integer addAnotherPlayerAmount(PlayerBoard playerBoard, Integer amount) {
        for (int i = 0; i < playerBoard.getPits().size(); i++) {
            playerBoard.getPits().get(i).add();
            if (amount - 1 == 0) {
                return -1;
            }
            --amount;
        }
        return amount;
    }

    @Override
    public void resetPurge(PlayerBoard playerBoard) {
        playerBoard.setPurgeIndex(null);
    }

    @Override
    public boolean isNotEmpty(PlayerBoard playerBoard) {
        return playerBoard.getPits().stream().allMatch(Pit::getEmpty);
    }

    @Override
    public void setTurn(PlayerBoard playerBoard, Boolean isYourTurn) {
        playerBoard.setYourTurn(isYourTurn);
    }

    @Override
    public Integer findPlayerPurgeIndex(PlayerBoard playerBoard) {
        return playerBoard.getPurgeIndex();
    }
}
