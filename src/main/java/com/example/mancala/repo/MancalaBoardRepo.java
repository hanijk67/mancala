package com.example.mancala.repo;

import com.example.mancala.serviceEndPoints.eception.MancalaException;
import com.example.mancala.entity.MancalaBoard;
import com.example.mancala.entity.PlayerBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MancalaBoardRepo implements BoardRepo {
    private MancalaBoard mancalaBoard;

    private Integer cellCount;
    @Value("${mancala.palayer.count}")
    private Integer playerCount;
    private Integer playerTurnIndex = 0;
    private PlayerRepo playerBoardRepo;


    @Autowired
    public MancalaBoardRepo(PlayerRepo playerBoardRepo) {
        this.playerBoardRepo = playerBoardRepo;
    }

    public MancalaBoard createNewMancala(Integer pitsAmount, Integer cellCount) {
        List<PlayerBoard> players = new ArrayList<>();
        this.cellCount = cellCount;
        for (int i = 0; i < playerCount; i++) {
            players.add(playerBoardRepo.createPlayerBoard(pitsAmount, cellCount));
        }
        mancalaBoard = new MancalaBoard(players);
        playerTurnIndex = 0;

        return mancalaBoard;
    }

    @Override
    public void playerTurn(Integer playerNumber) {
        mancalaBoard.getPlayers().forEach(playerBoard -> playerBoardRepo.setTurn(playerBoard, false));
        playerBoardRepo.setTurn(mancalaBoard.getPlayers().get(playerNumber), true);
        playerTurnIndex = playerNumber;
    }

    @Override
    public Integer getPlayerTurnIndex() {
        return playerTurnIndex;
    }

    @Override
    public void setPlayerTurnIndex(Integer playerTurnIndex) {
        this.playerTurnIndex = playerTurnIndex;
    }

    @Override
    public List<PlayerBoard> getPlayers() {
        return mancalaBoard.getPlayers();
    }

    @Override
    public Integer getPlayerCount() {
        return playerCount;
    }

    public Integer getCellCount() {
        return cellCount;
    }

    @Override
    public void destroyMancala() {
        mancalaBoard = null;
    }

    @Override
    public MancalaBoard getMancala() {
        return mancalaBoard;
    }
}
