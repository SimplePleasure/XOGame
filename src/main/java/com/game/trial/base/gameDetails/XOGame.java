package com.game.trial.base.gameDetails;

import com.game.trial.base.gameDetails.compute.Processor;
import com.game.trial.base.gameDetails.compute.ResultCheck;
import com.game.trial.base.gameDetails.turn.PlayerSymbol;
import com.game.trial.exception.exceptions.ChoosedPointIsBusyException;
import com.game.trial.exception.exceptions.WrongUserTurn;
import com.game.trial.request.base.Step;

import java.util.ArrayDeque;

public class XOGame {

    private final int pointsToWin;
    private final PlayerSymbol[][] battleField;
    private final ArrayDeque<Player> players;
    private final Processor processor;
    private volatile int turnsCount;


    public XOGame(int sideSize, int pointsToWin, ArrayDeque<Player> players, Processor processor) {
        this.pointsToWin = pointsToWin;
        this.players = players;
        this.processor = processor;
        turnsCount = 0;
        battleField = new PlayerSymbol[sideSize][sideSize];

    }

    public ResultCheck makeStep(Step step, Player player) {
        if (isRightPlayerGetTurn(player)) {
            PlayerSymbol symbol = getPlayerSymbol();
            if (battleField[step.getyAxis()][step.getxAxis()] == null &&
                    turnsCount < (battleField.length*battleField.length)) {
                battleField[step.getyAxis()][step.getxAxis()] = symbol;
                moveTurn();
                return getBattleField();
            }
            throw new ChoosedPointIsBusyException();
        }
        throw new WrongUserTurn();
    }

    public ResultCheck getBattleField() {
        return processor.checkBF(battleField, pointsToWin);
    }

    public void setPlayersGameStatusToFalse() {
        players.forEach(Player::setInGameFalse);
    }


    private boolean isRightPlayerGetTurn(Player currentPlayer) {
        return players.getFirst().equals(currentPlayer);
    }

    private PlayerSymbol getPlayerSymbol() {
        return players.getFirst().getPlayerTurn().getPlayerSymbol();
    }

    private void moveTurn() {
        turnsCount++;
        Player player = players.pollFirst();
        players.addLast(player);
    }
}
