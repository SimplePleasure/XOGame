package com.game.trial.request;

import org.springframework.stereotype.Component;

@Component
public class Step {

    private int xAxis;
    private int yAxis;

    public Step() {
    }

    public int getxAxis() {
        return xAxis;
    }
    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }
    public int getyAxis() {
        return yAxis;
    }
    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }
}
