package com.game.trial.base.gameDetails.turn;

public enum PlayerSymbol {

    X("X"),
    O("O");

    private String symbol;

    PlayerSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
