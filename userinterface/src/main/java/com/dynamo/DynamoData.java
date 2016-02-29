package com.dynamo;
/*
 * Copyright 2012-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;
import com.cricdata.model.DynamoBean;
import com.cricdata.model.MatchBean;
import com.cricdata.model.PerfBean;
import com.cricdata.model.SeriesBean;
import com.cricdata.model.Statistics;
import com.cricdata.userinterface.MyUI;
import com.vaadin.sass.internal.util.StringUtil;

/**
 * This sample demonstrates how to perform a few simple operations with the
 * Amazon DynamoDB service.
 */
public class DynamoData {

    static AmazonDynamoDBClient dynamoDB;
    public void init() throws Exception {
       
        AWSCredentials credentials = null;
        try {
        	credentials = new BasicAWSCredentials("AKIAI6QKTRAQE7MXQOIQ","wIG6u1yI5ZaseeJbvYSUmD98qelIJNSCVBzt5k2q");        	
           } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\bssan_000\\.aws\\credentials), and is in valid format.",
                    e);
        }
        dynamoDB = new AmazonDynamoDBClient(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        dynamoDB.setRegion(usWest2);
    }

    public static void main(String[] args) throws Exception {
    	DynamoData data = new DynamoData();
    	data.init();
        List<MatchBean> bean=  data.getData();
        System.out.println(bean.size());
    }

    public List<MatchBean> getData() {

        try {
        	List<MatchBean> dataList = new ArrayList<MatchBean>(); 
        	String tableName = "Matches";

            ScanRequest scanRequest = new ScanRequest(tableName);
            ScanResult scanResult = dynamoDB.scan(scanRequest);
            
            List<Map<String, AttributeValue>> list = new ArrayList<Map<String, AttributeValue>>();
            list = scanResult.getItems();
            for (Map<String, AttributeValue> map : list) {
				String match_id = map.get("match_id").getN();
				String awayTeam = map.get("awayTeam").getS();
				String currentMatchState = map.get("currentMatchState").getS();
				if(currentMatchState.equals("$$")) {
					currentMatchState="";
				}
				String homeTeam = map.get("homeTeam").getS();
				String isWom = map.get("isWomen").getS();
				String match_Type = map.get("match_type").getS();
				String name = map.get("name").getS();
				String series = map.get("series").getS();
				String status = map.get("status").getS();
				String awayOvers;
				String homeOvers;
				String homeScore;
				if(map.get("awayOvers") !=null) {
					awayOvers = map.get("awayOvers").getS();
				} else  {
					awayOvers = "";
				}
				
				if(map.get("homeOvers") !=null) {
					homeOvers = map.get("homeOvers").getS();
				} else {
					homeOvers = "";
				}
				if(map.get("homeScore") !=null) {
					homeScore = map.get("homeScore").getS();
				} else {
					homeScore = "";
				}
				
				String won;
				if(map.get("won") !=null) {
					won = map.get("won").getS();
				} else {
					won = "";
				}
				
				
				MatchBean data = new MatchBean();
				data.setMatchId(match_id);
				data.setAwayTeam(awayTeam);
				data.setCurrentMatchesState(currentMatchState);
				data.setHomeTeam(homeTeam);
				data.setIsWom(isWom);
				data.setName(name);
				data.setSeries(series);
				data.setAwayOvers(awayOvers);
				data.setHomeOvers(homeOvers);
				data.setHomeScore(homeScore);
				data.setMatchType(match_Type);
				data.setWon(won);
				data.setStatus(status);
				dataList.add(data);			
				System.out.println("Match :"+data.toString());
			}
            System.out.println("Result: " + scanResult);
            return dataList;
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return null;
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return null;
        }
    }
    
    public List<SeriesBean> getSeriesData() {

        try {
        	List<SeriesBean> dataList = new ArrayList<SeriesBean>(); 
        	String tableName = "series";

            ScanRequest scanRequest = new ScanRequest(tableName);
            ScanResult scanResult = dynamoDB.scan(scanRequest);
            dynamoDB.describeTable(tableName).getTable();
            List<Map<String, AttributeValue>> list = new ArrayList<Map<String, AttributeValue>>();
            list = scanResult.getItems();
            
            for (Map<String, AttributeValue> map : list) {
				String series_id = map.get("series_id").getN();
				String endDateTime = map.get("endDateTime").getS();
				String seriesName = map.get("name").getS();
				
				String startDateTime = map.get("startDateTime").getS();
				String status = map.get("status").getS();
				
				SeriesBean data = new SeriesBean();
				data.setSeriesId(series_id);
				data.setName(seriesName);
				data.setEndDatetime(endDateTime);
				data.setStartDateTime(startDateTime);
				data.setStatus(status);
				dataList.add(data);			
				System.out.println(data.toString());
			}
            System.out.println("Result: " + scanResult);
            return dataList;
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return null;
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return null;
        }
    }
    
    public List<Statistics> getPerfData() {

        try {
        	List<PerfBean> dataList = new ArrayList<PerfBean>(); 
        	String tableName = "Performance";

            ScanRequest scanRequest = new ScanRequest(tableName);
            ScanResult scanResult = dynamoDB.scan(scanRequest);
            dynamoDB.describeTable(tableName).getTable();
            List<Map<String, AttributeValue>> list = new ArrayList<Map<String, AttributeValue>>();
            list = scanResult.getItems();
            String perf_id;
            for (Map<String, AttributeValue> map : list) {
            	perf_id = map.get("perf_id").getS();
				String lambdaStart = map.get("lambdaStart").getS();
				String dynamoSave = map.get("dynamoStop").getS();
				String records  = map.get("number_req").getN();
				System.out.println("records :********"+records);
				PerfBean data = new PerfBean();
				data.setDynamoStop(dynamoSave);
				data.setPerf_id(perf_id);
				data.setLambdaStart(lambdaStart);
				data.setRecords(records);
				
				dataList.add(data);			
				System.out.println(data.toString());
			}
            List<Statistics> statistics =   MyUI.stats;
            
       
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS a");
          //  Date d = new Date(formatter);
            
            for (Statistics statistics2 : statistics) {
				for (PerfBean dataBean : dataList) {
					if(dataBean.getPerf_id().equals(statistics2.getKey())){						
						Date lambdaStart;
						Date dynamoComplete;
						try {
							dynamoComplete = formatter.parse(dataBean.getDynamoStop());
							lambdaStart = formatter.parse(dataBean.getLambdaStart());
							statistics2.setLambdaStart(lambdaStart);
							statistics2.setDynamoComplete(dynamoComplete);
							statistics2.setTotalRecords(dataBean.getRecords());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}
				}
			}
            System.out.println("Result: " + scanResult);
            return statistics;
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return null;
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return null;
        }
    }
}