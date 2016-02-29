package com.cricdata.cric.livescore.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Body {
	private MatchList matchList;

	public MatchList getMatchList() {
		return matchList;
	}

	public void setMatchList(MatchList matchList) {
		this.matchList = matchList;
	}
}
