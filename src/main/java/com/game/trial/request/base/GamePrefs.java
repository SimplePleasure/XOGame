package com.game.trial.request.base;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.trial.exception.exceptions.WrongInitPropertiesException;

public class GamePrefs {

    @JsonProperty("side-size")
    private int sideSize;
    @JsonProperty("points-count")
    private int pointsToWin;

    public GamePrefs() {

    }
    public GamePrefs(int sideSize, int pointsToWin) {
        if (pointsToWin > sideSize || sideSize < 3) {
            throw new WrongInitPropertiesException();
        }
        this.sideSize = sideSize;
        this.pointsToWin = pointsToWin;
    }

    public int getSideSize() {
        return sideSize;
    }
    public void setSideSize(int sideSize) {
        this.sideSize = sideSize;
    }
    public int getPointsToWin() {
        return pointsToWin;
    }
    public void setPointsToWin(int pointsToWin) {
        this.pointsToWin = pointsToWin;
    }
}
