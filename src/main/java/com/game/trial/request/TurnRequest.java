package com.game.trial.request;

import com.game.trial.request.base.Step;
import org.springframework.stereotype.Component;

@Component
public class TurnRequest {

    private Step step;
    private String gameId;

    public TurnRequest() {
    }

    public Step getStep() {
        return step;
    }
    public void setStep(Step step) {
        this.step = step;
    }
    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
