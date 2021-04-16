package com.game.trial.base.gameDetails.compute;

import com.game.trial.base.gameDetails.Battlefield;
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

    public PlayerSymbol checkBF(Battlefield bf) {
        for (Direction d : directions) {
            PlayerSymbol ps = d.hasWinner(bf);
            if (ps != null) {
                System.err.println("WINNER!:))");
                return ps;
            }
        }
        return null;
    }
}
