package com.example.mancala.service;

import com.example.mancala.repo.PlayerRepo;
import com.example.mancala.serviceEndPoints.eception.MancalaException;
import com.example.mancala.entity.PlayerBoard;
import com.example.mancala.repo.BoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private BoardRepo mancalaBoardRepo;
    private PlayerRepo playerBoardRepo;


    @Autowired
    public GameServiceImpl(BoardRepo mancalaBoardRepo, PlayerRepo playerBoardRepo) {
        this.mancalaBoardRepo = mancalaBoardRepo;
        this.playerBoardRepo = playerBoardRepo;
    }

    @Override
    public Integer getPlayerCount() {
        return mancalaBoardRepo.getPlayerCount();
    }

    @Override
    public void newGame(Integer pitAmount, Integer cellCount) {
        mancalaBoardRepo.createNewMancala(pitAmount, cellCount);
    }

    @Override
    public void setStarterPlayer(Integer starterPlayer) {
        mancalaBoardRepo.playerTurn(starterPlayer);
    }

    private Integer findNextPLayer() {
        Integer nextPlayer = mancalaBoardRepo.getPlayerTurnIndex() + 1;
        if (mancalaBoardRepo.getPlayerTurnIndex() == mancalaBoardRepo.getPlayerCount() - 1) {
            nextPlayer = 0;
        }
        return nextPlayer;
    }

    private Integer findOppositeIndex(Integer index) {
        return mancalaBoardRepo.getCellCount() - index + 1;
    }


    private void purgeCell() {
        Integer nextPlayer = findNextPLayer();
        Integer purgeIndex = playerBoardRepo.findPlayerPurgeIndex(mancalaBoardRepo.getMancala().getPlayers().get(mancalaBoardRepo.getPlayerTurnIndex()));
        Integer amountOppositeCell = playerBoardRepo.purgeFromCell(mancalaBoardRepo.getMancala().getPlayers().get(nextPlayer), findOppositeIndex(purgeIndex));
        playerBoardRepo.purgeReward(mancalaBoardRepo.getMancala().getPlayers().get(mancalaBoardRepo.getPlayerTurnIndex()), amountOppositeCell);
        mancalaBoardRepo.setPlayerTurnIndex(nextPlayer);
        playerBoardRepo.setTurn(mancalaBoardRepo.getMancala().getPlayers().get(mancalaBoardRepo.getPlayerTurnIndex()), true);
    }

    @Override
    public void distributeCell(Integer cellNumber) {
        distributeCell(cellNumber, null);
    }

    public Integer distributeCell(Integer cellNumber, Integer leftOver) {
        try {
            leftOver = playerBoardRepo.distribute(mancalaBoardRepo.getMancala().getPlayers().get(mancalaBoardRepo.getPlayerTurnIndex()), cellNumber, leftOver);
            if (leftOver > 0) {
                leftOver = playerBoardRepo.addAnotherPlayerAmount(mancalaBoardRepo.getMancala().getPlayers().get(findNextPLayer()), leftOver);
                if (leftOver > 0) {
                    leftOver = distributeCell(1, leftOver);
                } else {
                    mancalaBoardRepo.playerTurn(findNextPLayer());
                }
            } else if (leftOver == -1) {
                mancalaBoardRepo.playerTurn(findNextPLayer());
            } else if (leftOver == 0) {
                mancalaBoardRepo.playerTurn(mancalaBoardRepo.getPlayerTurnIndex());
            }
        } catch (Exception e) {
            if (e instanceof MancalaException.CellIsEmptyException) {
                throw new MancalaException.CellIsEmptyException("not valid Cell, please choose another cell.");
            }
            if (e instanceof MancalaException.PurgeFromOppositeException) {
                purgeCell();
            }
        }
        return leftOver;
    }

    @Override
    public Integer getNextPlayer() {
        return mancalaBoardRepo.getPlayerTurnIndex();
    }
    @Override
    public Boolean gameIsOver() {
        return mancalaBoardRepo.getMancala().getPlayers().stream().anyMatch(playerBoard -> playerBoardRepo.isNotEmpty(playerBoard));
    }

    @Override
    public List<PlayerBoard> getPlayers() {
        return mancalaBoardRepo.getPlayers();
    }

    @Override
    public void resetGame() {
        mancalaBoardRepo.destroyMancala();
    }

    @Override
    public void checkCellValidation(Integer cell) {
        if (mancalaBoardRepo.getCellCount() < cell || cell < 1) {
            throw new MancalaException.CellIsNotValidException(" please choose valid cell between 1 and " + mancalaBoardRepo.getCellCount());
        }
    }
    @Override
    public void checkGameValidation() {
        if (mancalaBoardRepo.getMancala() == null) {
            throw new MancalaException.GameNotStartedException("game is not start yet please start from new Game.");
        }
    }

    @Override
    public void checkStarterPlayerValidation(Integer playerIndex) {
        if (playerIndex < 1 || playerIndex > mancalaBoardRepo.getPlayerCount()) {
            throw new MancalaException.PlayerNumberIsNotValidException("please choose a number between 1 and "+mancalaBoardRepo.getPlayerCount());
        }
    }

    @Override
    public void collectFromPits() {
        mancalaBoardRepo.getMancala().getPlayers().forEach(player -> {
            Integer sumOfPits = player.getPits().stream().mapToInt(pit -> pit.take()).sum();
            playerBoardRepo.purgeReward(player, sumOfPits);
        });
    }

}
