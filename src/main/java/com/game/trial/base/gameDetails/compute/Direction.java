package com.game.trial.base.gameDetails.compute;

import com.game.trial.base.gameDetails.Battlefield;
import com.game.trial.base.gameDetails.turn.PlayerSymbol;

public interface Direction {

    PlayerSymbol hasWinner(Battlefield game);
}
