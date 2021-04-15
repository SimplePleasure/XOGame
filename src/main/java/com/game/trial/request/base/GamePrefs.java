package com.game.trial.request.base;


public class GamePrefs {

    private int sideSize;
    private int pointsToWin;

    public GamePrefs() {

    }
    public GamePrefs(int sideSize, int pointsToWin) {
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
