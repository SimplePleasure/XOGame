package com.game.trial.base.gameDetails;

import com.game.trial.base.gameDetails.turn.PlayerTurn;

import java.time.LocalDateTime;
import java.util.ArrayDeque;


public class GameWaitingStart {
    private String id;
    private int sideSize;
    private int pointsToWin;
    private LocalDateTime burningTime;
    private ArrayDeque<Player> players;

    public GameWaitingStart(String id, int sideSize, int pointsToWin, Player player) {
        this.id = id;
        this.players = new ArrayDeque<>();
        this.sideSize = sideSize;
        this.pointsToWin = pointsToWin;
        players.add(player);
        player.setPlayerTurn(PlayerTurn.PLAYER_FIRST);
        burningTime = LocalDateTime.now().plusMinutes(5);
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
        return burningTime.isBefore(LocalDateTime.now());
    }

    public void addPlayer(Player p) {
        p.setPlayerTurn(PlayerTurn.PLAYER_SECOND);
        players.add(p);
    }

}
