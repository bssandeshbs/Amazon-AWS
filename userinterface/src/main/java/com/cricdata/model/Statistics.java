package com.cricdata.model;

import java.util.Date;

public class Statistics {
	private String key;
	private Date s3Req;
	private Date s3Res;
	private Date lambdaStart;
	private Date dynamoComplete;
	private String size;
	private String totalRecords;
	
	public String getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Date getS3Req() {
		return s3Req;
	}
	public void setS3Req(Date s3Req) {
		this.s3Req = s3Req;
	}
	public Date getS3Res() {
		return s3Res;
	}
	public void setS3Res(Date s3Res) {
		this.s3Res = s3Res;
	}
	public Date getLambdaStart() {
		return lambdaStart;
	}
	public void setLambdaStart(Date lambdaStart) {
		this.lambdaStart = lambdaStart;
	}
	public Date getDynamoComplete() {
		return dynamoComplete;
	}
	public void setDynamoComplete(Date dynamoComplete) {
		this.dynamoComplete = dynamoComplete;
	}
	
	@Override
	public String toString() {
		return "Statistics [key=" + key + ", s3Req=" + s3Req + ", s3Res="
				+ s3Res + ", lambdaStart=" + lambdaStart + ", dynamoComplete="
				+ dynamoComplete + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistics other = (Statistics) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
}
