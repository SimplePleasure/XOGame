package com.game.trial.request;

import com.game.trial.base.gameDetails.Player;
import com.game.trial.request.base.GamePrefs;
import org.springframework.stereotype.Component;

@Component
public class GameRegisterRequest {

    private Player player;
    private GamePrefs gamePrefs;

    public GameRegisterRequest() {
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public GamePrefs getGamePrefs() {
        return gamePrefs;
    }
    public void setGamePrefsRequest(GamePrefs gameRegistrator) {
        this.gamePrefs = gameRegistrator;
    }
}
