package com.example.demo.handler;

import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.response.ErrorMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class PlayerExceptionHandler {
    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> catchPlayerNotFoundException() {
        ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse();
        errorMessageResponse.setMessage("Player with this id not found");
        log.error(errorMessageResponse.getMessage());
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.NOT_FOUND);
    }
}
