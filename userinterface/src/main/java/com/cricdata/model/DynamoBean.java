package com.cricdata.model;

public class DynamoBean {
	String name;
	String ratings;
	String year;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRatings() {
		return ratings;
	}
	public void setRatings(String ratings) {
		this.ratings = ratings;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "DynamoBean [name=" + name + ", ratings=" + ratings + ", year="
				+ year + "]";
	}
}
