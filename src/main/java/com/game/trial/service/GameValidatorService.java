package com.game.trial.service;

import com.game.trial.base.IResponse;
import com.game.trial.base.gameDetails.GameWaitingStart;
import com.game.trial.base.gameDetails.Player;
import com.game.trial.exception.exceptions.GameJoiningException;
import com.game.trial.request.GameRegisterRequest;
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
public class GameValidatorService {

    @Value("${project.xo.wait-player-lifecycle-in-seconds}")
    private int lifecycleInSeconds;

    // TODO: 14.04.2021 deleting last requests
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
            System.err.println("\t -------> GameValidatorService#checkGames invoke lock");
            lock.lock();
            try {
                try {
                    if (gamesWaitingPlayers.size() > 0) {
                        System.out.println("\t -------> await " + lifecycleInSeconds);
                        condition.await(lifecycleInSeconds, TimeUnit.SECONDS);
                    } else {
                        System.out.println("\t -------> await out of time");
                        condition.await();
                    }
                } catch (InterruptedException e) {
                    continue;
                }
                System.out.println("\t -------> GameValidatorService thread start cleaning. map size:" + gamesWaitingPlayers.size());
                gamesWaitingPlayers.entrySet()
                        .stream()
                        .filter(x -> x.getValue().isExpired())
                        .forEach(x -> gamesWaitingPlayers.remove(x.getKey()));

            } finally {
                System.err.println("\t -------> GameValidatorService#checkGames invoke unlock");
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

    public String registerNewGame(GameRegisterRequest gr) {
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
        return gw.getId();
    }

    public GameWaitingStart joinToTheGame(Player player, String gameId) {
        GameWaitingStart gw;
        lock.lock();
        try {
            gw = gamesWaitingPlayers.get(gameId);
            if (gw == null) {
                throw new GameJoiningException();
            }
            gamesWaitingPlayers.remove(gameId);
        } finally {
            lock.unlock();
        }
        gw.addPlayer(player);
        return gw;
    }
}
