package com.footscout.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="player")
@XmlType(propOrder={
		"playerId",
		"playerName",
		"teamId",
		"teamName",
		"timePlayed",
		"goal",
		"assist",
		"yellowcard",
		"redcard",
		"injury",
		"ballTouched",
		"appearance",
		"position",
		"jerseyNumber",
		"passPrecision",
		"shotPrecision",
		"aerialDuelWon",
		"zoneX",
		"zoneY",
		"attack",
		"defense",
		"skill",
		"physique",
		"teamwork"
})
public class Player {
	private int playerId;
	private int teamId;
	private String playerName;
	private String teamName;
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	private int timePlayed;
	private int goal;
	private int assist;
	private int yellowcard;
	private int redcard;
	private int injury;
	private double ballTouched;
	private int appearance;
	private int position;
	private int jerseyNumber;
	public int getJerseyNumber() {
		return jerseyNumber;
	}
	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}
	private double passPrecision;
	private double shotPrecision;
	private double aerialDuelWon;
	public double getAerialDuelWon() {
		return aerialDuelWon;
	}
	public void setAerialDuelWon(double aerialDuelWon) {
		this.aerialDuelWon = aerialDuelWon;
	}
	private double zoneX;
	private double zoneY;
	// KPIs
	private double attack;
	private double defense;
	private double skill;
	private double physique;
	private double teamwork;
	public double getAttack() {
		return attack;
	}
	public void setAttack(double attack) {
		this.attack = attack;
	}
	public double getDefense() {
		return defense;
	}
	public void setDefense(double defense) {
		this.defense = defense;
	}
	public double getSkill() {
		return skill;
	}
	public void setSkill(double skill) {
		this.skill = skill;
	}
	public double getPhysique() {
		return physique;
	}
	public void setPhysique(double physique) {
		this.physique = physique;
	}
	public double getTeamwork() {
		return teamwork;
	}
	public void setTeamwork(double teamwork) {
		this.teamwork = teamwork;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getTimePlayed() {
		return timePlayed;
	}
	public void setTimePlayed(int timePlayed) {
		this.timePlayed = timePlayed;
	}
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public int getAssist() {
		return assist;
	}
	public void setAssist(int assist) {
		this.assist = assist;
	}
	public int getYellowcard() {
		return yellowcard;
	}
	public void setYellowcard(int yellowcard) {
		this.yellowcard = yellowcard;
	}
	public int getRedcard() {
		return redcard;
	}
	public void setRedcard(int redcard) {
		this.redcard = redcard;
	}
	public int getInjury() {
		return injury;
	}
	public void setInjury(int injuery) {
		this.injury = injuery;
	}
	public double getBallTouched() {
		return ballTouched;
	}
	public void setBallTouched(double ballTouched) {
		this.ballTouched = ballTouched;
	}
	public int getAppearance() {
		return appearance;
	}
	public void setAppearance(int appearance) {
		this.appearance = appearance;
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
