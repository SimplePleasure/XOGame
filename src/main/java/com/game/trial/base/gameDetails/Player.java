package com.game.trial.base.gameDetails;

import com.game.trial.base.gameDetails.turn.PlayerTurn;

public class Player {

    private String name;
    private boolean inGame;
    private PlayerTurn playerTurn;

    public Player() {
        inGame = false;
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
        inGame = true;
    }

    public boolean isInGame() {
        return inGame;
    }
    public void setInGameFalse() {
        inGame = false;
        playerTurn = null;
    }
}
