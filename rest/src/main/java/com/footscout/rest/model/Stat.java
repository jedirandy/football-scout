package com.footscout.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stat {
	private int playerId;
	private int gameId;
	private int numPass;
	private int numCross;
	private int numBallTouched;
	private int numGoal;
	private int numHeadgoal;
	private int numFreekickGoal;
	private int numNormalGoal;
	private int numShot;
	private int numShotFreekick;
	private int numTackle;
	private int numInterception;
	private int numFoul;
	private int numOffside;
	private int numAssist;
	private int numYellowcard;
	private int numRedcard;
	private int numInjury;
	private int coplayer1Id;
	private int coplayer2Id;
	private int coplayer3Id;
	private int jerseyNumber;
	private int position;
	private double passPrecision;
	private double shotPrecision;
	private double aerialDuelWon;
	private int timePlayed;
	private double zoneX;
	private double zoneY;
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getNumPass() {
		return numPass;
	}
	public void setNumPass(int numPass) {
		this.numPass = numPass;
	}
	public int getNumCross() {
		return numCross;
	}
	public void setNumCross(int numCross) {
		this.numCross = numCross;
	}
	public int getNumBallTouched() {
		return numBallTouched;
	}
	public void setNumBallTouched(int numBallTouched) {
		this.numBallTouched = numBallTouched;
	}
	public int getNumGoal() {
		return numGoal;
	}
	public void setNumGoal(int numGoal) {
		this.numGoal = numGoal;
	}
	public int getNumHeadgoal() {
		return numHeadgoal;
	}
	public void setNumHeadgoal(int numHeadgoal) {
		this.numHeadgoal = numHeadgoal;
	}
	public int getNumFreekickGoal() {
		return numFreekickGoal;
	}
	public void setNumFreekickGoal(int numFreekickGoal) {
		this.numFreekickGoal = numFreekickGoal;
	}
	public int getNumNormalGoal() {
		return numNormalGoal;
	}
	public void setNumNormalGoal(int numNormalGoal) {
		this.numNormalGoal = numNormalGoal;
	}
	public int getNumShot() {
		return numShot;
	}
	public void setNumShot(int numShot) {
		this.numShot = numShot;
	}
	public int getNumShotFreekick() {
		return numShotFreekick;
	}
	public void setNumShotFreekick(int numShotFreekick) {
		this.numShotFreekick = numShotFreekick;
	}
	public int getNumTackle() {
		return numTackle;
	}
	public void setNumTackle(int numTackle) {
		this.numTackle = numTackle;
	}
	public int getNumInterception() {
		return numInterception;
	}
	public void setNumInterception(int numInterception) {
		this.numInterception = numInterception;
	}
	public int getNumFoul() {
		return numFoul;
	}
	public void setNumFoul(int numFoul) {
		this.numFoul = numFoul;
	}
	public int getNumOffside() {
		return numOffside;
	}
	public void setNumOffside(int numOffside) {
		this.numOffside = numOffside;
	}
	public int getNumAssist() {
		return numAssist;
	}
	public void setNumAssist(int numAssist) {
		this.numAssist = numAssist;
	}
	public int getNumYellowcard() {
		return numYellowcard;
	}
	public void setNumYellowcard(int numYellowcard) {
		this.numYellowcard = numYellowcard;
	}
	public int getNumRedcard() {
		return numRedcard;
	}
	public void setNumRedcard(int numRedcard) {
		this.numRedcard = numRedcard;
	}
	public int getNumInjury() {
		return numInjury;
	}
	public void setNumInjury(int numInjury) {
		this.numInjury = numInjury;
	}
	public int getCoplayer1Id() {
		return coplayer1Id;
	}
	public void setCoplayer1Id(int coplayer1Id) {
		this.coplayer1Id = coplayer1Id;
	}
	public int getCoplayer2Id() {
		return coplayer2Id;
	}
	public void setCoplayer2Id(int coplayer2Id) {
		this.coplayer2Id = coplayer2Id;
	}
	public int getCoplayer3Id() {
		return coplayer3Id;
	}
	public void setCoplayer3Id(int coplayer3Id) {
		this.coplayer3Id = coplayer3Id;
	}
	public int getJerseyNumber() {
		return jerseyNumber;
	}
	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public double getPassPrecision() {
		return passPrecision;
	}
	public void setPassPrecision(double passPrecision) {
		this.passPrecision = passPrecision;
	}
	public double getShotPrecision() {
		return shotPrecision;
	}
	public void setShotPrecision(double shotPrecision) {
		this.shotPrecision = shotPrecision;
	}
	public double getAerialDuelWon() {
		return aerialDuelWon;
	}
	public void setAerialDuelWon(double aerialDuelWon) {
		this.aerialDuelWon = aerialDuelWon;
	}
	public int getTimePlayed() {
		return timePlayed;
	}
	public void setTimePlayed(int timePlayed) {
		this.timePlayed = timePlayed;
	}
	public double getZoneX() {
		return zoneX;
	}
	public void setZoneX(double zoneX) {
		this.zoneX = zoneX;
	}
	public double getZoneY() {
		return zoneY;
	}
	public void setZoneY(double zoneY) {
		this.zoneY = zoneY;
	}
}
