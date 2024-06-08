package com.example.mancala.unitTest;


import com.example.mancala.entity.PlayerBoard;
import com.example.mancala.repo.PLayerBoardRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerBoardRepoTest {

    PLayerBoardRepo pLayerBoardRepo = new PLayerBoardRepo();

    @Test
    public void createPlayerBoardTest() {
        PlayerBoard playerBoard = pLayerBoardRepo.createPlayerBoard(6, 6);
        assertEquals(playerBoard.getPits().size(), 6);
        playerBoard.getPits().forEach(pit -> assertEquals(pit.getAmount(), 6));
    }

    @Test
    public void distributeTest() {
        PlayerBoard playerBoard = pLayerBoardRepo.createPlayerBoard(6, 6);
        // cells [ {6}, {6}, {6}, {6}, {6}, {6} ||  0]

        //play with first cell
        Integer remain = pLayerBoardRepo.distribute(playerBoard, 1, null);
        assertEquals(remain, 0);
        assertEquals(playerBoard.getStoreAmount(), 1);
        assertEquals(playerBoard.getPits().get(0).getAmount(), 0);
        for (int i = 1; i < 6; i++) {
            assertEquals(playerBoard.getPits().get(i).getAmount(), 7);
        }
        //cells [ {0}, {7}, {7}, {7}, {7}, {7} ||  1]

        //play with second cell
        remain = pLayerBoardRepo.distribute(playerBoard, 2, null);
        assertEquals(remain,2);
        assertEquals(playerBoard.getStoreAmount(), 2);
        assertEquals(playerBoard.getPits().get(0).getAmount(), 0);
        assertEquals(playerBoard.getPits().get(1).getAmount(), 0);
        for (int i = 2; i < 6; i++) {
            assertEquals(playerBoard.getPits().get(i).getAmount(), 8);
        }
        //cells [ {0}, {0}, {8}, {8}, {8}, {8} ||  2]

        //play after a loop with 2 remain amount
        remain = pLayerBoardRepo.distribute(playerBoard, 1, 2);
        assertEquals(remain, -1);
        assertEquals(playerBoard.getStoreAmount(), 2);
        assertEquals(playerBoard.getPits().get(0).getAmount(), 1);
        assertEquals(playerBoard.getPits().get(1).getAmount(), 1);
        for (int i = 2; i < 6; i++) {
            assertEquals(playerBoard.getPits().get(i).getAmount(), 8);
        }
        //cells [ {1}, {1}, {8}, {8}, {8}, {8} ||  2]
    }

}
