package com.game.trial.base.gameDetails.compute;

import com.game.trial.base.gameDetails.Battlefield;
import com.game.trial.base.gameDetails.turn.PlayerSymbol;
import org.springframework.stereotype.Component;

@Component
public class TopLeftToBelowRight implements Direction {

    @Override
    public PlayerSymbol hasWinner(Battlefield game) {
        PlayerSymbol[][] bf = game.getBattleField();
        int xPoints = 0;
        int oPoints = 0;
        int startXPosition = bf.length -1;
        int startYPosition = 1;

        for (int x = startXPosition; x > 1 ; ) {
            for (int y = 0; y < bf.length; y++) {
                PlayerSymbol symbol = bf[x][y];
                if (symbol == null) continue;

                if (symbol == PlayerSymbol.X) {
                    oPoints = 0;
                    xPoints++;
                }
                if (symbol == PlayerSymbol.O) {
                    xPoints = 0;
                    oPoints++;
                }
                if (xPoints == game.getPointsToWin()) return PlayerSymbol.X;
                if (oPoints == game.getPointsToWin()) return PlayerSymbol.O;
                x--;
            }
            xPoints = 0;
            oPoints = 0;
            x = --startXPosition;
        }
        for (int y = startYPosition; y < bf.length - 2 ; ) {
            for (int x = bf.length - 1; x >= 0; x--) {
                PlayerSymbol symbol = bf[x][y];
                if (symbol == null) continue;

                if (symbol == PlayerSymbol.X) {
                    oPoints = 0;
                    xPoints++;
                }
                if (symbol == PlayerSymbol.O) {
                    xPoints = 0;
                    oPoints++;
                }
                if (oPoints == game.getPointsToWin()) return PlayerSymbol.O;
                y++;
            }
            xPoints = 0;
            oPoints = 0;
            y = ++startYPosition;
        }
        return null;
    }
}
