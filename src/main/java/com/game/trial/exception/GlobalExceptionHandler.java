package com.game.trial.exception;

import com.game.trial.base.IResponse;
import com.game.trial.exception.exceptions.GameJoiningException;
import com.game.trial.response.ResponseTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({GameJoiningException.class})
    public final ResponseEntity<IResponse> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof GameJoiningException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            GameJoiningException e = (GameJoiningException) ex;
            return handleGameJoiningException(status, request, headers);
        } else {
            ResponseTemplate body = new ResponseTemplate(false);
            body.addHint("unexpected error", ex.getLocalizedMessage());
            return handleEx(body, HttpStatus.NOT_IMPLEMENTED, headers);
        }

    }

    protected ResponseEntity<IResponse> handleGameJoiningException(HttpStatus status, WebRequest request, HttpHeaders headers) {
        return handleEx(new ResponseTemplate(false), status, headers);
    }

    protected ResponseEntity<IResponse> handleEx(IResponse body, HttpStatus status, HttpHeaders headers) {
        return new ResponseEntity<IResponse>(body, headers, status);
    }
}
