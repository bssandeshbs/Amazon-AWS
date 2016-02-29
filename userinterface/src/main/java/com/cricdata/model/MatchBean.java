package com.cricdata.model;

import java.io.Serializable;

//Here is a JavaBean
public class MatchBean implements Serializable {
	 /**
	   * 
	 */
     private static final long serialVersionUID = 5680007378293631464L;

     String matchId;
	 String awayTeam;
	 String currentMatchesState;
	 String homeTeam;
	 String isWom;
	 String matchType;
     String name;
	 String series;
	 String status;
	 String awayOvers;
	 String homeOvers;
	 String homeScore;
	 String won; 

	 public MatchBean() {
		 
	 }

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public String getCurrentMatchesState() {
		return currentMatchesState;
	}

	public void setCurrentMatchesState(String currentMatchesState) {
		this.currentMatchesState = currentMatchesState;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getIsWom() {
		return isWom;
	}

	public void setIsWom(String isWom) {
		this.isWom = isWom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAwayOvers() {
		return awayOvers;
	}

	public void setAwayOvers(String awayOvers) {
		this.awayOvers = awayOvers;
	}

	public String getHomeOvers() {
		return homeOvers;
	}

	public void setHomeOvers(String homeOvers) {
		this.homeOvers = homeOvers;
	}

	public String getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(String homeScore) {
		this.homeScore = homeScore;
	}

	public String getWon() {
		return won;
	}

	public void setWon(String won) {
		this.won = won;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
}