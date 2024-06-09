package com.example.mancala.integrationTest;

import com.example.mancala.entity.MancalaBoard;
import com.example.mancala.entity.Pit;
import com.example.mancala.entity.PlayerBoard;
import com.example.mancala.repo.BoardRepo;
import com.example.mancala.repo.MancalaBoardRepo;
import com.example.mancala.repo.PLayerBoardRepo;
import com.example.mancala.repo.PlayerRepo;
import com.example.mancala.service.GameService;
import com.example.mancala.service.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GameServiceTest {


    public MancalaBoard initialMancalaBoard() {
        List<Pit> pitsFirst = new ArrayList<>();
        List<Pit> pitssecond = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            pitsFirst.add(new Pit(6));
            pitssecond.add(new Pit(6));
        }

        List<PlayerBoard> playerBoardList = new ArrayList<>();
        playerBoardList.add(new PlayerBoard(pitsFirst));
        playerBoardList.add(new PlayerBoard(pitssecond));

        return new MancalaBoard(playerBoardList);
    }

    @Test
    public void play() {

        MancalaBoard mancalaBoard = initialMancalaBoard();

        BoardRepo boardRepo = mock(MancalaBoardRepo.class);
        PlayerRepo playerRepo = mock(PLayerBoardRepo.class);
        GameService gameService = new GameServiceImpl(boardRepo, playerRepo);

        when((boardRepo.getMancala())).thenReturn(mancalaBoard);
        when((boardRepo.getPlayers())).thenReturn(mancalaBoard.getPlayers());
        doCallRealMethod().when(playerRepo).distribute(any(), any(), any());
        doCallRealMethod().when(playerRepo).addAnotherPlayerAmount(any(), any());

        gameService.distributeCell(1);

        List<Pit> firstPitAfterChange = gameService.getPlayers().get(0).getPits();
        assertEquals(firstPitAfterChange.get(0).getAmount(), 0);
        for (int i = 1; i < 6; i++) {
            assertEquals(firstPitAfterChange.get(i).getAmount(), 7);
        }
        List<Pit> secondPitAfterChange = gameService.getPlayers().get(1).getPits();
        for (Pit pit : secondPitAfterChange) {
            assertEquals(pit.getAmount(), 6);
        }

        gameService.distributeCell(2);

        firstPitAfterChange = gameService.getPlayers().get(0).getPits();
        assertEquals(firstPitAfterChange.get(0).getAmount(), 0);
        assertEquals(firstPitAfterChange.get(1).getAmount(), 0);
        for (int i = 2; i < 6; i++) {
            assertEquals(firstPitAfterChange.get(i).getAmount(), 8);
        }
        secondPitAfterChange = gameService.getPlayers().get(1).getPits();
        assertEquals(secondPitAfterChange.get(0).getAmount(), 7);
        assertEquals(secondPitAfterChange.get(1).getAmount(), 7);
        for (int i = 2; i < 6; i++) {
            assertEquals(secondPitAfterChange.get(i).getAmount(), 6);
        }
    }
}
