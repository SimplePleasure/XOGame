package com.game.trial.service;

import com.game.trial.base.gameDetails.GameWaitingStart;
import com.game.trial.base.gameDetails.Player;
import com.game.trial.exception.exceptions.GameJoiningException;
import com.game.trial.request.GameRegisterRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class GameValidatorService {

    private final Object monitor;
    // TODO: 14.04.2021 deleting last requests
    private HashMap<String, GameWaitingStart> gamesWaitingPlayers;
    private ScheduledExecutorService scheduler;

    public GameValidatorService() {
        monitor = new Object();
        this.gamesWaitingPlayers = new HashMap<>();
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }





















    public String registerNewGame(GameRegisterRequest gr) {

        GameWaitingStart gw = new GameWaitingStart(UUID.randomUUID().toString().substring(0, 8),
                gr.getGamePrefs().getSideSize(), gr.getGamePrefs().getPointsToWin(), gr.getPlayer());
        synchronized (monitor) {
            gamesWaitingPlayers.put(gw.getId(), gw);
        }
        return gw.getId();
    }

    public GameWaitingStart joinToTheGame(Player player, String gameId) {

        GameWaitingStart gw;
        synchronized (monitor) {
            gw = gamesWaitingPlayers.get(gameId);
            if (gw == null) {
                throw new GameJoiningException();
            }
            gamesWaitingPlayers.remove(gameId);
        }
        gw.addPlayer(player);
        return gw;
    }
}
