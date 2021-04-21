package com.game.trial.service;

import com.game.trial.base.IContain;
import com.game.trial.base.IResponse;
import com.game.trial.base.gameDetails.GameWaitingStart;
import com.game.trial.base.gameDetails.compute.ValidationStatus;
import com.game.trial.exception.exceptions.NonExistentGameException;
import com.game.trial.request.GameRegisterRequest;
import com.game.trial.request.JoinGameRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class GameValidatorService implements IContain {

    @Value("${project.xo.wait-player-lifecycle-in-seconds}")
    private int lifecycleInSeconds;

    private HashMap<String, GameWaitingStart> gamesWaitingPlayers;
    private final Lock lock;
    private final Condition condition;
    private ExecutorService scheduler;


    public GameValidatorService() {
        this.gamesWaitingPlayers = new HashMap<>();
        lock = new ReentrantLock();
        condition = lock.newCondition();
        scheduler = Executors.newSingleThreadExecutor();
        scheduler.submit(this::checkGames);
    }

    public void checkGames() {
        for (; ; ) {
            lock.lock();
            try {
                try {
                    if (gamesWaitingPlayers.size() > 0) {
                        condition.await(lifecycleInSeconds, TimeUnit.SECONDS);
                    } else {
                        condition.await();
                    }
                } catch (InterruptedException e) {
                    continue;
                }
                gamesWaitingPlayers.entrySet()
                        .stream()
                        .filter(x -> x.getValue().isExpired())
                        .peek(x -> x.getValue().setPlayerGameStatusFalse())
                        .forEach(x -> gamesWaitingPlayers.remove(x.getKey()));

            } finally {
                lock.unlock();
            }
        }
    }

    public List<IResponse> getActiveGamesWaitingStart() {
        return gamesWaitingPlayers.values()
                .stream()
                .filter(gameWaitingStart -> !gameWaitingStart.isExpired())
                .collect(Collectors.toList());
    }

    public IResponse registerNewGame(GameRegisterRequest gr) {
        LocalDateTime expired = LocalDateTime.now().plusSeconds(lifecycleInSeconds);
        GameWaitingStart gw = new GameWaitingStart(UUID.randomUUID().toString().substring(0, 8),
                gr.getGamePrefs().getSideSize(), gr.getGamePrefs().getPointsToWin(), gr.getPlayer(), expired);
        lock.lock();
        try {
            gamesWaitingPlayers.put(gw.getId(), gw);
            condition.signal();
        } finally {
            lock.unlock();
        }
        return gw;
    }

    public GameWaitingStart joinToTheGame(JoinGameRequest request) {
        GameWaitingStart gw;

        lock.lock();
        try {
            gw = gamesWaitingPlayers.get(request.getGameId());
            if (gw == null) {
                throw new NonExistentGameException();
            }
            gamesWaitingPlayers.remove(request.getGameId());
        } finally {
            lock.unlock();
        }
        gw.addPlayer(request.getPlayer());
        return gw;
    }


    @Override
    public boolean isContainRecord(String gameId) {
        return gamesWaitingPlayers.containsKey(gameId);
    }

    @Override
    public ValidationStatus getGameInfo(String gameId) {
        GameWaitingStart game = gamesWaitingPlayers.get(gameId);
        return new ValidationStatus()
                .setFieldSize(game.getSideSize())
                .setPointsToWin(game.getPointsToWin())
                .setGameWaitingTime(game.waitingTimeSeconds());


    }
}
