package com.game.trial.base.gameDetails;

import com.game.trial.base.gameDetails.compute.Processor;
import com.game.trial.base.gameDetails.turn.PlayerSymbol;
import com.game.trial.request.base.Step;

public class Battlefield {

    private PlayerSymbol[][] battleField;
    private final int pointsToWin;
    private final int sideSize;
    private volatile int turnsCount;
    private Processor processor;


    protected Battlefield( int sideSize, int pointsToWin) {
        this.pointsToWin = pointsToWin;
        this.sideSize = sideSize;
        turnsCount = 0;
        battleField = new PlayerSymbol[sideSize][sideSize];
    }

    public int getPointsToWin() {
        return pointsToWin;
    }
    public int getSideSize() {
        return sideSize;
    }
    public PlayerSymbol[][] getBattleField() {
        return battleField;
    }
    protected boolean haveSomeTurns() {
        return ((sideSize * sideSize - 1) > turnsCount);
    }

    protected boolean makeTurn(Step step, PlayerSymbol symbol, Processor processor) {
        if (battleField[step.getxAxis()][step.getyAxis()] == null) {
            synchronized (battleField) {
                battleField[step.getxAxis()][step.getyAxis()] = symbol;
                turnsCount++;
            }
            processor.checkBF(this);
            temporal();
            return true;
        }
        // TODO: 15.04.2021 add exception
        return false;
    }

    public void temporal() {
        Integer[][][][] test = new Integer[10][10][10][10];

        System.out.println("TurnsCount = " + turnsCount);
        for (int x = 0; x < battleField.length; x++) {
            PlayerSymbol[] line = battleField[x];
            for (int y = 0; y < line.length; y++) {
                String symbol = line[y] == null ? "." : line[y].getSymbol();
                System.out.print(" " + symbol + " ");
            }
            System.out.println();
        }
    }


}
