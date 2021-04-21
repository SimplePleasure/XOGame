package com.game.trial.base.gameDetails.compute;

import com.game.trial.base.gameDetails.turn.PlayerSymbol;
import org.springframework.stereotype.Component;

@Component
public class XDirection implements Direction{

    @Override
    public ResultCheck hasWinner(ResultCheck check) {
        PlayerSymbol[][] bf = check.getBattlefield();
        int pointCountToWin = check.getScoresToWin();
        int xPoints = 0;
        int oPoints = 0;

        for (int x = 0; x < bf.length; x++) {
            for (int y = 0; y < bf[x].length; y++) {
                PlayerSymbol current = bf[x][y];
                if (current == PlayerSymbol.O) {
                    oPoints++;
                    xPoints = 0;
                } else if (current == PlayerSymbol.X) {
                    xPoints++;
                    oPoints = 0;
                }
                if (xPoints == pointCountToWin) return check.setWinnerSymbol(PlayerSymbol.X);
                if (oPoints == pointCountToWin) return check.setWinnerSymbol(PlayerSymbol.O);
            }
            xPoints = 0;
            oPoints = 0;
        }
        return check;
    }
}
