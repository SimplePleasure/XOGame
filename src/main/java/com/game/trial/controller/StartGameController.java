package com.game.trial.controller;

import com.game.trial.base.IResponse;
import com.game.trial.base.Session;
import com.game.trial.base.gameDetails.GameWaitingStart;
import com.game.trial.base.gameDetails.compute.ResultCheck;
import com.game.trial.exception.exceptions.NonExistentGameException;
import com.game.trial.request.GameRegisterRequest;
import com.game.trial.request.JoinGameRequest;
import com.game.trial.request.TurnRequest;
import com.game.trial.response.ResponseTemplate;
import com.game.trial.service.GameEngineService;
import com.game.trial.service.GameValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StartGameController {

    private final Session session;
    private final GameEngineService gameEngineService;
    private final GameValidatorService gameValidatorService;

    public StartGameController(@Autowired GameValidatorService gameValidatorService, @Autowired GameEngineService gameEngineService, @Autowired Session s) {
        this.gameValidatorService = gameValidatorService;
        this.gameEngineService = gameEngineService;
        session = s;
    }

    @GetMapping(value = "/avaliablelist")
    public ResponseEntity<List<IResponse>> getActiveGames() {
        return new ResponseEntity<>(gameValidatorService.getActiveGamesWaitingStart(), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<IResponse> regGame(@RequestBody GameRegisterRequest settings) {
        if (session.getPlayer() == null || session.getPlayer().isInGame()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseTemplate(false)
                    .addHint("player", "player must be authenticated and finish all games"));
        }
        settings.setPlayer(session.getPlayer());
        IResponse game = gameValidatorService.registerNewGame(settings);
        return ResponseEntity.status(HttpStatus.OK).body(game);
    }

    @PostMapping(value = "/join")
    public ResponseEntity<IResponse> joinGame(@RequestBody JoinGameRequest join) {
//        if (session.getPlayer() == null || session.getPlayer().isInGame()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseTemplate(false)
//                      .addHint("player", "player must be authenticated and finish all games"));
//        }
        join.setPlayer(session.getPlayer());
        GameWaitingStart gameWaitingStart = gameValidatorService.joinToTheGame(join);
        gameEngineService.createNewGame(gameWaitingStart);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseTemplate(true).addHint("gameId", gameWaitingStart.getId()));
    }

    @PostMapping(value = "/turn")
    public ResponseEntity<IResponse> makeAMove(@RequestBody TurnRequest step) {
        ResultCheck result = gameEngineService.makeStep(step.getGameId(), step.getStep(), session.getPlayer());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /*

     */
    @GetMapping(value = "/check")
    public ResponseEntity<IResponse> check(@RequestParam String gameId) {
        IResponse response = List.of(gameEngineService, gameValidatorService)
                        .stream()
                        .filter(x -> x.isContainRecord(gameId))
                        .findFirst()
                        .orElseThrow(NonExistentGameException::new)
                        .getGameInfo(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
