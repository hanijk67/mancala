package com.example.mancala.serviceEndPoints;


import com.example.mancala.entity.Pit;
import com.example.mancala.entity.PlayerBoard;
import com.example.mancala.entity.helper.ResponseMancala;
import com.example.mancala.service.GameService;
import com.example.mancala.serviceEndPoints.eception.ExceptionHandlerClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExceptionHandlerClasses
@RestController
@RequestMapping("/mancala")
public class MancalaEndPoints {


    public GameService gameService;

    @Autowired
    public MancalaEndPoints(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping("/start/{cellCount}/{pitAmount}")
    public ResponseEntity<String> startGame(@PathVariable Integer cellCount, @PathVariable Integer pitAmount) {
        gameService.newGame(pitAmount, cellCount);
        return new ResponseEntity<>("Mancala is Start with " + gameService.getPlayerCount() + " Player.", HttpStatus.OK);
    }

    @PutMapping("/play/starterPlayer/{playerNumber}")
    public ResponseEntity<String> starterPlayer(@PathVariable Integer playerNumber) {
        gameService.checkGameValidation();
        gameService.checkStarterPlayerValidation(playerNumber);
        gameService.setStarterPlayer(playerNumber - 1);
        return new ResponseEntity<>("Player " + (playerNumber == 1 ? "first player" : "second player") + " must Start.", HttpStatus.OK);
    }


    @PutMapping("/play/{cellNumber}")
    public ResponseEntity<ResponseMancala> play(@PathVariable Integer cellNumber) {
        gameService.checkCellValidation(cellNumber);
        gameService.checkGameValidation();
        ResponseMancala responseMancala;
        gameService.distributeCell(cellNumber);

        List<PlayerBoard> playerBoardList = gameService.getPlayers();
        if (gameService.gameIsOver()) {
            gameService.collectFromPits();
            responseMancala = createGameOverResponse(playerBoardList);
            gameService.resetGame();
        } else {
            responseMancala = createNextMancalaResponse(gameService.getNextPlayer(), playerBoardList);
        }
        return new ResponseEntity<>(responseMancala, HttpStatus.OK);

    }

    private List<Pit> getReversPits(PlayerBoard playerBoard) {
        List<Pit> pits = new ArrayList<>(playerBoard.getPits());
        Collections.reverse(pits);

        return pits;
    }

    private ResponseMancala createNextMancalaResponse(Integer nextPlayer, List<PlayerBoard> playerBoardList) {
        ResponseMancala responseMancala = new ResponseMancala();
        PlayerBoard firstPlayer = playerBoardList.get(0);
        PlayerBoard secondPlayer = playerBoardList.get(1);
        responseMancala.setNextPlayer(nextPlayer == 0 ? "first player" : "second player");

        List<Pit> pits = getReversPits(secondPlayer);
        String firstAmount = firstPlayer.getStoreAmount() + "";
        String secondAmount = secondPlayer.getStoreAmount() + "";
        responseMancala.setValuesSecondPlayer(secondAmount + "  || " + pits.toString().replace("[", "").replace("]", "") + " ||  " + firstAmount);
        responseMancala.setValuesFirstPlayer(secondAmount.replaceAll(".", " ") + "   || " + firstPlayer.getPits().toString().replace("[", "").replace("]", "") + " ||  " + firstAmount.replaceAll(".", " "));


        return responseMancala;
    }

    private ResponseMancala createGameOverResponse(List<PlayerBoard> playerBoardList) {
        ResponseMancala responseMancala = new ResponseMancala();
        Integer firstPlayerPoints = playerBoardList.get(0).getStoreAmount();
        Integer secondPlayerPoints = playerBoardList.get(1).getStoreAmount();
        responseMancala.setValuesFirstPlayer(firstPlayerPoints.toString());
        responseMancala.setValuesSecondPlayer(secondPlayerPoints.toString());
        responseMancala.setContent("game over, winner is : " + (firstPlayerPoints > secondPlayerPoints ? " firstPlayer and points is " + firstPlayerPoints : " secondPlayer and points is " + secondPlayerPoints) + " and looser points is : " + (firstPlayerPoints < secondPlayerPoints ? firstPlayerPoints : secondPlayerPoints));

        return responseMancala;
    }

}
