package com.game.trial.base.gameDetails.compute;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.trial.base.IResponse;
import com.game.trial.base.gameDetails.GameStatus;

public class ValidationStatus implements IResponse {


    private String gameId;
    private final GameStatus status = GameStatus.WAIT;
    @JsonProperty("field-size")
    private int fieldSize;
    @JsonProperty("points-to-win")
    private int pointsToWin;
    @JsonProperty(value = "waiting")
    private int gameWaitingTime;

    public ValidationStatus setGameId(String gameId) {
        this.gameId = gameId;
        return this;
    }
    public ValidationStatus setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
        return this;
    }
    public ValidationStatus setPointsToWin(int pointsToWin) {
        this.pointsToWin = pointsToWin;
        return this;
    }
    public ValidationStatus setGameWaitingTime(int gameWaitingTime) {
        this.gameWaitingTime = gameWaitingTime;
        return this;
    }

    public String getGameId() {
        return gameId;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getPointsToWin() {
        return pointsToWin;
    }

    public int getGameWaitingTime() {
        return gameWaitingTime;
    }

    public GameStatus getStatus() {
        return status;
    }
}
