package com.game.trial.base.gameDetails.turn;

public enum PlayerTurn {

    PLAYER_FIRST(PlayerSymbol.X),
    PLAYER_SECOND(PlayerSymbol.O);

    private PlayerSymbol playerSymbol;

    PlayerTurn(PlayerSymbol playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public PlayerSymbol getPlayerSymbol() {
        return playerSymbol;
    }
}
