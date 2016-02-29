package com.cricdata.cric.livescore.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
	String startDateTime;

	String cmsMatchEndDate;
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getCmsMatchEndDate() {
		return cmsMatchEndDate;
	}
	public void setCmsMatchEndDate(String cmsMatchEndDate) {
		this.cmsMatchEndDate = cmsMatchEndDate;
	}
	
	@Override
	public String toString() {
		return "Match [startDateTime=" + startDateTime + ", cmsMatchEndDate="
				+ cmsMatchEndDate + ", getStartDateTime()="
				+ getStartDateTime() + ", getCmsMatchEndDate()="
				+ getCmsMatchEndDate() + "]";
	}
}
