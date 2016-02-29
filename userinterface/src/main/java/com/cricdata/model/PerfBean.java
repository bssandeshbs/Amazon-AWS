package com.cricdata.model;

public class PerfBean {
	private String perf_id;
	private String lambdaStart;
	private String dynamoStop;
	private String records;
	

	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	public String getPerf_id() {
		return perf_id;
	}
	public void setPerf_id(String perf_id) {
		this.perf_id = perf_id;
	}
	public String getLambdaStart() {
		return lambdaStart;
	}
	public void setLambdaStart(String lambdaStart) {
		this.lambdaStart = lambdaStart;
	}
	public String getDynamoStop() {
		return dynamoStop;
	}
	public void setDynamoStop(String dynamoStop) {
		this.dynamoStop = dynamoStop;
	}

}
