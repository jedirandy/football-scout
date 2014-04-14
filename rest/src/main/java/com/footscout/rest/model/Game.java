package com.footscout.rest.model;

public class Game {
	private int gameId;
	private int matchday;
	private int teamHomeId;
	private int teamAwayId;
	private int scoreHome;
	private int scoreAway;
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getMatchday() {
		return matchday;
	}
	public void setMatchday(int matchday) {
		this.matchday = matchday;
	}
	public int getTeamHomeId() {
		return teamHomeId;
	}
	public void setTeamHomeId(int teamHomeId) {
		this.teamHomeId = teamHomeId;
	}
	public int getTeamAwayId() {
		return teamAwayId;
	}
	public void setTeamAwayId(int teamAwayId) {
		this.teamAwayId = teamAwayId;
	}
	public int getScoreHome() {
		return scoreHome;
	}
	public void setScoreHome(int scoreHome) {
		this.scoreHome = scoreHome;
	}
	public int getScoreAway() {
		return scoreAway;
	}
	public void setScoreAway(int scoreAway) {
		this.scoreAway = scoreAway;
	}
}
