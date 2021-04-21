package com.game.trial.base.gameDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.game.trial.base.IResponse;
import com.game.trial.base.gameDetails.turn.PlayerTurn;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayDeque;

public class GameWaitingStart implements IResponse {

    private String id;
    private int sideSize;
    private int pointsToWin;
    @JsonIgnore
    private LocalDateTime expired;
    @JsonIgnore
    private ArrayDeque<Player> players;

    public GameWaitingStart(String id, int sideSize, int pointsToWin, Player player, LocalDateTime expired) {
        this.id = id;
        this.expired = expired;
        this.sideSize = sideSize;
        this.pointsToWin = pointsToWin;
        this.players = new ArrayDeque<>();
        players.add(player);
        player.setPlayerTurn(PlayerTurn.PLAYER_FIRST);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getSideSize() {
        return sideSize;
    }
    public void setSideSize(int sideSize) {
        this.sideSize = sideSize;
    }
    public int getPointsToWin() {
        return pointsToWin;
    }
    public void setPointsToWin(int pointsToWin) {
        this.pointsToWin = pointsToWin;
    }
    public ArrayDeque<Player> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayDeque<Player> players) {
        this.players = players;
    }
    public boolean isExpired() {
        return expired.isBefore(LocalDateTime.now());
    }
    public int waitingTimeSeconds() {
         return (int)Duration.between(expired, LocalDateTime.now()).getSeconds();
    }
    public void addPlayer(Player p) {
        p.setPlayerTurn(PlayerTurn.PLAYER_SECOND);
        players.add(p);
    }
    public void setPlayerGameStatusFalse() {
        players.forEach(Player::setInGameFalse);
    }

    /*
        Сделать чтобы этот класс создавал и возвращал Объект ValidationStatus вместо GameValidatorService

    */
}
