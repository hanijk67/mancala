package com.example.mancala.serviceEndPoints.eception;

import com.example.mancala.entity.helper.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice(annotations = {ExceptionHandlerClasses.class}, basePackages = "com/example/mancala")
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            MancalaException.CellIsEmptyException.class,
            MancalaException.GameNotStartedException.class,
            MancalaException.CellIsNotValidException.class,
            MancalaException.PlayerNumberIsNotValidException.class,
    }
    )
    public ResponseEntity<ErrorMessage> badRequestException(RuntimeException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE.name(),
                new Date(),
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }


}
