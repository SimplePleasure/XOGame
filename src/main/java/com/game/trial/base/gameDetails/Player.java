package com.game.trial.base.gameDetails;

import com.game.trial.base.gameDetails.turn.PlayerTurn;

public class Player {

    private String name;
    private PlayerTurn playerTurn;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    protected PlayerTurn getPlayerTurn() {
        return playerTurn;
    }
    protected void setPlayerTurn(PlayerTurn playerTurn) {
        this.playerTurn = playerTurn;
    }
}
