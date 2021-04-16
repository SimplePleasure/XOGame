package com.game.trial.controller;

import com.game.trial.base.IResponse;
import com.game.trial.base.Session;
import com.game.trial.base.gameDetails.GameWaitingStart;
import com.game.trial.request.GameRegisterRequest;
import com.game.trial.request.JoinGameRequest;
import com.game.trial.request.TurnRequest;
import com.game.trial.response.ResponseTemplate;
import com.game.trial.service.GameEngine;
import com.game.trial.service.GameValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StartGameController {

    private Session session;
    private final GameEngine gameEngine;
    private final GameValidatorService gameValidatorService;

    public StartGameController(@Autowired GameValidatorService gameValidatorService, @Autowired GameEngine gameEngine, @Autowired Session s) {
        this.gameValidatorService = gameValidatorService;
        this.gameEngine = gameEngine;
        session = s;
    }


    @PostMapping(value = "/register")
    public ResponseEntity<IResponse> regGame(@RequestBody GameRegisterRequest settings) {
        System.out.println("controller::regGame player:" + session.getPlayer());
        settings.setPlayer(session.getPlayer());
        String gameId = gameValidatorService.registerNewGame(settings);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseTemplate(true).addHint("gameId", gameId));
    }

    @PostMapping(value = "/join")
    public ResponseEntity<IResponse> joinGame(@RequestBody JoinGameRequest join) {
        join.setPlayer(session.getPlayer());
        GameWaitingStart gameWaitingStart = gameValidatorService.joinToTheGame(join.getPlayer(), join.getGameId());
        gameEngine.createNewGame(gameWaitingStart);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseTemplate(true).addHint("gameId", gameWaitingStart.getId()));
    }

    @GetMapping(value = "/avaliablelist")
    public ResponseEntity<List<IResponse>> getActiveGames() {
        return new ResponseEntity<>(gameValidatorService.getActiveGamesWaitingStart(), HttpStatus.OK);
    }





    @PostMapping(value = "/turn")
    public ResponseEntity<IResponse> makeAMove(@RequestBody TurnRequest step) {
        boolean result = gameEngine.makeStep(step.getGameId(), step.getStep(), session.getPlayer());
        HttpStatus status = result ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ResponseTemplate(result), status);
    }


}
