package com.game.trial.base.gameDetails;

import com.game.trial.base.gameDetails.turn.PlayerTurn;

public class Player {

    private String name;

    private boolean inGame;
    private String gameId;
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
    public boolean isInGame() {
        return inGame;
    }
    public String getGameId() {
        return gameId;
    }

    protected void setGame(String gameId, PlayerTurn playerTurn) {
        this.gameId = gameId;
        this.playerTurn = playerTurn;
        inGame = true;
    }

    public void setInGameFalse() {
        inGame = false;
        playerTurn = null;
    }
}
