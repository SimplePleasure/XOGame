package com.game.trial.base.gameDetails;

import com.game.trial.base.gameDetails.compute.Processor;
import com.game.trial.base.gameDetails.compute.ResultCheck;
import com.game.trial.base.gameDetails.turn.PlayerSymbol;
import com.game.trial.exception.exceptions.ChoosedPointIsBusyException;
import com.game.trial.exception.exceptions.WrongUserTurn;
import com.game.trial.request.Step;

import java.time.LocalDateTime;
import java.util.ArrayDeque;

public class XOGame {

    private final int GAME_DURATION_IN_MIN = 3;
    private final int pointsToWin;
    private final PlayerSymbol[][] battleField;
    private final ArrayDeque<Player> players;
    private final Processor processor;
    private volatile int availableTurnsCount;
    private final LocalDateTime expireTime;


    public XOGame(int sideSize, int pointsToWin, ArrayDeque<Player> players, Processor processor) {
        this.pointsToWin = pointsToWin;
        this.players = players;
        this.processor = processor;
        availableTurnsCount = sideSize * sideSize;
        battleField = new PlayerSymbol[sideSize][sideSize];
        expireTime = LocalDateTime.now().plusMinutes(GAME_DURATION_IN_MIN);
    }



    public ResultCheck makeStep(Step step, Player player) {
        if (isRightPlayerGetTurn(player)) {
            PlayerSymbol symbol = getPlayerSymbol();
            if (battleField[step.getyAxis()][step.getxAxis()] == null && availableTurnsCount > 0) {
                battleField[step.getyAxis()][step.getxAxis()] = symbol;
                moveTurn();
                return getBattleField();
            }
            throw new ChoosedPointIsBusyException("The window is not free");
        }
        throw new WrongUserTurn("Wait your turn, please.");
    }

    public ResultCheck getBattleField() {
        ResultCheck result = processor.checkBF(battleField, pointsToWin, availableTurnsCount);
        if (result.getStatus() == GameStatus.FINISH) {
            players.forEach(Player::setInGameFalse);
        }
        return result;
    }


    private boolean isRightPlayerGetTurn(Player currentPlayer) {
        return players.getFirst().equals(currentPlayer);
    }
    private PlayerSymbol getPlayerSymbol() {
        return players.getFirst().getPlayerTurn().getPlayerSymbol();
    }

    private void moveTurn() {
        availableTurnsCount--;
        Player player = players.pollFirst();
        players.addLast(player);
    }
}
