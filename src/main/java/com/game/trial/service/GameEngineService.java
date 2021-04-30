package com.game.trial.service;

import com.game.trial.base.IContain;
import com.game.trial.base.gameDetails.GameWaitingStart;
import com.game.trial.base.gameDetails.Player;
import com.game.trial.base.gameDetails.compute.Processor;
import com.game.trial.base.gameDetails.compute.ResultCheck;
import com.game.trial.exception.exceptions.NonExistentGameException;
import com.game.trial.request.Step;
import com.game.trial.base.gameDetails.XOGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameEngineService implements IContain {

    // TODO: 14.04.2021 deleting last requests
    // TODO: 14.04.2021  Сделать бы только безопасную коллекцию либо реализовать её синхронизацию
    private final Map<String, XOGame> games;
    private final Processor processor;

    public GameEngineService(@Autowired Processor processor) {
        this.games = new HashMap<>();
        this.processor = processor;
    }

    public void createNewGame(GameWaitingStart game) {
        XOGame xo = new XOGame(game.getSideSize(), game.getPointsToWin(), game.getPlayers(), processor);
        synchronized (games) {
            games.put(game.getId(), xo);
        }
    }

    public ResultCheck makeStep(String gameId, Step step, Player player) {
        XOGame game;
        synchronized (games) {
            game = games.computeIfAbsent(gameId, x -> {throw new NonExistentGameException();});
        }
        return game.makeStep(step, player);
    }


    @Override
    public boolean isContainRecord(String gameId) {
        return games.containsKey(gameId);
    }

    @Override
    public ResultCheck getGameInfo(String gameId) {
        XOGame game;
        synchronized (games) {
            game = games.computeIfAbsent(gameId, x -> {throw new NonExistentGameException();});
        }
        return game.getBattleField();
    }


}
