package com.game.trial.base.gameDetails.compute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.trial.base.IResponse;
import com.game.trial.base.gameDetails.turn.PlayerSymbol;

public class ResultCheck implements IResponse {


    private boolean hasWinner;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private PlayerSymbol winnerSymbol;
    private final int scoresToWin;
    private final PlayerSymbol[][] battlefield;

    protected ResultCheck(PlayerSymbol[][] battlefield, int scoresToWin) {
        this.battlefield = battlefield;
        this.scoresToWin = scoresToWin;
        hasWinner = false;
    }

    protected ResultCheck setWinnerSymbol(PlayerSymbol winnerSymbol) {
        hasWinner = true;
        this.winnerSymbol = winnerSymbol;
        return this;
    }

    public boolean isHasWinner() {
        return hasWinner;
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
}
