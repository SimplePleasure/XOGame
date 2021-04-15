package com.game.trial.service;

import com.game.trial.base.gameDetails.GameWaitingStart;
import com.game.trial.base.gameDetails.Player;
import com.game.trial.base.gameDetails.compute.Processor;
import com.game.trial.request.base.Step;
import com.game.trial.base.gameDetails.XOGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameEngine {

    // TODO: 14.04.2021 deleting last requests
    // TODO: 14.04.2021  Сделать бы только безопасную коллекцию либо реализовать её синхронизацию
    private final Map<String, XOGame> duringGame;
    private final Processor processor;

    public GameEngine(@Autowired Processor processor) {
        this.duringGame = new HashMap<>();
        this.processor = processor;
    }

    public void createNewGame(GameWaitingStart game) {
        XOGame xo = new XOGame(game.getId(), game.getSideSize(), game.getPointsToWin(), game.getPlayers());
        synchronized (duringGame) {
            duringGame.put(game.getId(), xo);
        }
    }

    public boolean makeStep(String gameId, Step step, Player player) {
        XOGame game = duringGame.get(gameId);
        return game.makeStep(step, player, processor);
    }
}
