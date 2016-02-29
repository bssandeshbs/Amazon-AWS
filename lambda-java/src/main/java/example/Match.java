package example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
public class Match {

	int id;
	String name;
	String status;
	String currentMatchState;
	boolean isMatchDrawn;
	boolean isMatchAbandoned;
	String winningTeamId;
	String localStartDate;
	String localStartTime;
	boolean isWomensMatch;
	String cmsMatchType;
	String cmsMatchAssociatedType;


	String startDateTime;
	String endDateTime;

	Series series;
	private Venue venue;
	private HomeTeam homeTeam;
	private AwayTeam awayTeam;
	private Scores scores;
	 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentMatchState() {
		return currentMatchState;
	}

	public void setCurrentMatchState(String currentMatchState) {
		this.currentMatchState = currentMatchState;
	}

	public boolean isMatchDrawn() {
		return isMatchDrawn;
	}

	public void setMatchDrawn(boolean isMatchDrawn) {
		this.isMatchDrawn = isMatchDrawn;
	}

	public boolean isMatchAbandoned() {
		return isMatchAbandoned;
	}

	public void setMatchAbandoned(boolean isMatchAbandoned) {
		this.isMatchAbandoned = isMatchAbandoned;
	}

	public String getWinningTeamId() {
		return winningTeamId;
	}

	public void setWinningTeamId(String winningTeamId) {
		this.winningTeamId = winningTeamId;
	}

	public String getLocalStartDate() {
		return localStartDate;
	}

	public void setLocalStartDate(String localStartDate) {
		this.localStartDate = localStartDate;
	}

	public String getLocalStartTime() {
		return localStartTime;
	}

	public void setLocalStartTime(String localStartTime) {
		this.localStartTime = localStartTime;
	}

	public boolean isWomensMatch() {
		return isWomensMatch;
	}

	public void setWomensMatch(boolean isWomensMatch) {
		this.isWomensMatch = isWomensMatch;
	}

	public String getCmsMatchType() {
		return cmsMatchType;
	}

	public void setCmsMatchType(String cmsMatchType) {
		this.cmsMatchType = cmsMatchType;
	}

	public String getCmsMatchAssociatedType() {
		return cmsMatchAssociatedType;
	}

	public void setCmsMatchAssociatedType(String cmsMatchAssociatedType) {
		this.cmsMatchAssociatedType = cmsMatchAssociatedType;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public HomeTeam getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(HomeTeam homeTeam) {
		this.homeTeam = homeTeam;
	}

	public AwayTeam getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(AwayTeam awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Scores getScores() {
		return scores;
	}

	public void setScores(Scores scores) {
		this.scores = scores;
	}
	
	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}


	/*
	 * @Override public String toString() { return "Match [startDateTime=" +
	 * startDateTime + ", cmsMatchEndDate=" + cmsMatchEndDate +
	 * ", getStartDateTime()=" + getStartDateTime() + ", getCmsMatchEndDate()="
	 * + getCmsMatchEndDate() + "]"; }
	 */
}
