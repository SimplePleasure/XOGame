package com.game.trial.base;

public interface IContain {

    boolean isContainRecord(String gameId);
    IResponse getGameInfo(String gameId);
}