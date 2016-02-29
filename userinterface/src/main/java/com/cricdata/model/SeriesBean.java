package com.cricdata.model;

public class SeriesBean {
	
	private String seriesId;
	private String endDatetime;
	private String name;
	private String startDateTime;
	private String status;
	
	public SeriesBean() {
		
	}

	public String getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}

	public String getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(String endDatetime) {
		this.endDatetime = endDatetime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SeriesBean [seriesId=" + seriesId + ", endDatetime="
				+ endDatetime + ", name=" + name + ", startDateTime="
				+ startDateTime + ", status=" + status + "]";
	}
}
