package com.game.trial.base.gameDetails.compute;

import com.game.trial.base.gameDetails.turn.PlayerSymbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Processor {

    private final List<Direction> directions;

    public Processor(@Autowired XDirection x, @Autowired YDirection y,
                     @Autowired TopLeftToBelowRight topLeftToBelowRight,
                     @Autowired TopRightToBelowLeft topRightToBelowLeft) {
        directions = new ArrayList<>();
        directions.add(x);
        directions.add(y);
        directions.add(topLeftToBelowRight);
        directions.add(topRightToBelowLeft);
    }

    public ResultCheck checkBF(PlayerSymbol[][] bf, int pointsCountToWin, int availableTurns) {
        ResultCheck check = new ResultCheck(bf, pointsCountToWin, availableTurns);
        for (Direction direction : directions) {
            direction.hasWinner(check);
        }
        return check;
    }
}
