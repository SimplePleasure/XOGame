package com.game.trial.base.gameDetails.compute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.trial.base.IResponse;
import com.game.trial.base.gameDetails.GameStatus;
import com.game.trial.base.gameDetails.turn.PlayerSymbol;

public class ResultCheck implements IResponse {

    private final int scoresToWin;
    private final int availableTurns;
    private final PlayerSymbol[][] battlefield;

    private GameStatus status;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private PlayerSymbol winnerSymbol;

    protected ResultCheck(PlayerSymbol[][] battlefield, int scoresToWin, int availableTurns) {
        this.availableTurns = availableTurns;
        this.battlefield = battlefield;
        this.scoresToWin = scoresToWin;
        status = GameStatus.PLAY;
    }

    public GameStatus getStatus() {
        return status;
    }
    public PlayerSymbol getWinnerSymbol() {
        return winnerSymbol;
    }
    public PlayerSymbol[][] getBattlefield() {
        return battlefield;
    }
    public int getScoresToWin() {
        return scoresToWin;
    }
    public int getAvailableTurns() {
        return availableTurns;
    }

    protected ResultCheck setWinnerSymbol(PlayerSymbol winnerSymbol) {
        this.winnerSymbol = winnerSymbol;
        status = GameStatus.FINISH;
        return this;
    }
}
