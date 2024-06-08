package com.example.mancala.repo;

import com.example.mancala.entity.MancalaBoard;
import com.example.mancala.entity.PlayerBoard;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BoardRepo {

   MancalaBoard createNewMancala(Integer pitsAmount, Integer cellCount);
   void playerTurn(Integer playerNumber);
   Integer getPlayerTurnIndex();
   void setPlayerTurnIndex(Integer playerTurnIndex);
   List<PlayerBoard> getPlayers();
   Integer getPlayerCount();
   Integer getCellCount();
   void destroyMancala();

   MancalaBoard getMancala();

}
