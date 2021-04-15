package com.game.trial.base.gameDetails;

import com.game.trial.base.gameDetails.compute.Processor;
import com.game.trial.request.base.Step;
import java.util.ArrayDeque;

public class XOGame {

    private String gameId;
    private int sideSize;
    private int pointsToWin;
    private Battlefield battlefield;

    // TODO: 14.04.2021 probably we can store turns instead players in blocking queue while move logic will wait in loop
    private ArrayDeque<Player> players;



    public XOGame(String gameId, int sideSize, int pointsToWin, ArrayDeque<Player> players) {
        this.gameId = gameId;
        this.sideSize = sideSize;
        this.pointsToWin = pointsToWin;
        this.players = players;
        battlefield = new Battlefield(sideSize, pointsToWin);

    }
    
    private Player getPlayerTurn() {
        Player player = players.pollFirst();
        if (battlefield.haveSomeTurns()) {
            players.addLast(player);
        }
        return player;
    }

    public boolean makeStep(Step step, Player sessionPlayer, Processor processor) {
        Player player = getPlayerTurn();
//        if (sessionPlayer.equals(player)) {
            return battlefield.makeTurn(step, player.getPlayerTurn().getPlayerSymbol(), processor);
//        }
//        return false;
    }


}
