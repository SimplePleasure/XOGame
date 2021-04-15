package com.game.trial.request;

import com.game.trial.base.gameDetails.Player;
import org.springframework.stereotype.Component;

@Component
public class JoinGameRequest {

    private Player player;
    private String gameId;

    public JoinGameRequest() {
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
