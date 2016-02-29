package example;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;

import org.apache.commons.io.IOUtils;



import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityResult;
import com.amazonaws.services.securitytoken.model.Credentials;



public class Hello implements
        RequestHandler<S3Event, String> {
	public String handleRequest(S3Event s3event, Context context) {
        try {
            LambdaLogger logger=context.getLogger();
        	S3EventNotificationRecord record = s3event.getRecords().get(0);

            String srcBucket = record.getS3().getBucket().getName();
            
            String srcKey = record.getS3().getObject().getKey()
                    .replace('+', ' ');
            srcKey = URLDecoder.decode(srcKey, "UTF-8");
			
			// Download the image from S3 into a stream
            AmazonS3 s3Client = new AmazonS3Client();
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(
                    srcBucket, srcKey));
            InputStream objectData = s3Object.getObjectContent();
			StringWriter writer = new StringWriter();
			IOUtils.copy(objectData, writer, null);
			String theString = writer.toString();int number_process=0;
			logger.log("JSON id:"+theString);
	        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
	        Body user1 = mapper.readValue(theString.toString(), Body.class);
	       // logger.log("received : " + 12);        
	        try{
	        DynamoDB dynamodb = new DynamoDB(
	        	    Region.getRegion(Regions.US_WEST_2).createClient(
	        	        AmazonDynamoDBClient.class,
	        	        new EnvironmentVariableCredentialsProvider(),
	        	        new ClientConfiguration()));
	        TableCollection<ListTablesResult> tables = dynamodb.listTables();
	        
	        Iterator<Table> iterator = tables.iterator();

	        while (iterator.hasNext()) { // this is where the error was thrown
	            Table table = iterator.next();
	            logger.log(table.getTableName());
	        }     
	        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS a");
	        Table cricTable = dynamodb.getTable("Matches");
	        Table cricTable_pref = dynamodb.getTable("Performance");
	        Item pref;
	        Thread.sleep(750);
	        Date date1 = new Date();
            if(user1.getStatus()==200){            	
            	MatchList ms=user1.getMatchList();int i=0;
            	while(i!=ms.getMatches().size())
            	{
            		Date date = new Date();
            		Match mat=ms.getMatches().get(i);i++;
            		if(mat==null) {
            			logger.log("null object at index "+i);
            			continue;
            			}
            		if((!mat.isWomensMatch())&&((mat.getCmsMatchAssociatedType().equals("Test"))||(mat.getCmsMatchAssociatedType().equals("T20 International"))||(mat.getCmsMatchAssociatedType().equals("One-Day International"))))
            		{
            			Item items;
            				if(mat.getScores()==null){
            					logger.log("null at scores"); number_process++;
            					items = new Item().withPrimaryKey("match_id",mat.getId())
                    	        		.withString("name", validate(mat.getSeries().getShortName()))
                    	        		.withString("currentMatchState", validate(mat.getCurrentMatchState()))
                    	        		.withString("homeTeam", validate(mat.getHomeTeam().getName()))
                    					.withString("awayTeam", validate(mat.getAwayTeam().getName()))                    					
                    	        		.withString("status", validate(mat.getStatus()))
                    	        		.withString("match_type", validate(mat.getCmsMatchAssociatedType()))
                    	        		.withString("isWomen", validate(String.valueOf(mat.isWomensMatch())))
                    	        		.withString("startDate", validate(String.valueOf(mat.getStartDateTime())))
                    	        		.withString("endDate", validate(String.valueOf(mat.getEndDateTime())))
                    	        		.withString("adminDateTime", validate(sdf.format(date) ))
                    	        		.withString("venue", validate(mat.getVenue().getName()))
                    					.withString("series", validate(mat.getSeries().getShortName()));            				
            			}
						else{
            				number_process++;
							items = new Item().withPrimaryKey("match_id",mat.getId())
            	        		.withString("name", validate(mat.getSeries().getShortName()))
            	        		.withString("currentMatchState", validate(mat.getCurrentMatchState()))
            	        		.withString("homeTeam", validate(mat.getHomeTeam().getName()))
            					.withString("awayTeam", validate(mat.getAwayTeam().getName()))
            					.withString("homeScore", validate(mat.getScores().getHomeScore()))
            					.withString("awayScore", validate(mat.getScores().getAwayScore()))
            					.withString("homeOvers", validate(mat.getScores().getHomeOvers()))
            					.withString("awayOvers", validate(mat.getScores().getAwayOvers()))
            	        		.withString("status", validate(mat.getStatus()))
            	        		.withString("match_type", validate(mat.getCmsMatchAssociatedType()))
            	        		.withString("isWomen", validate(String.valueOf(mat.isWomensMatch())))
            	        		.withString("startDate", validate(String.valueOf(mat.getStartDateTime())))
            	        		.withString("endDate", validate(String.valueOf(mat.getEndDateTime())))
            	        		.withString("adminDateTime", validate(sdf.format(date) ))
            	        		.withString("venue", validate(mat.getVenue().getName()))
            					.withString("series", validate(mat.getSeries().getShortName()));            			
            			}            			
            			if(mat.getWinningTeamId()!=null){            				
                			String team=getWinning_team( mat,mat.getWinningTeamId());
                			items.withString("won", validate(team));
                		}            			
            			cricTable.putItem(items);            			
            		}            		
            	}
            }else
            {
            	logger.log("not 200");
            }
            Date date3 = new Date();
            pref = new Item().withPrimaryKey("perf_id",srcKey)
	        		.withString("lambdaStart", sdf.format(date1))
	        		.withString("dynamoStop", sdf.format(date3))
                    .withNumber("number_req", number_process);
            cricTable_pref.putItem(pref);
	        }
	        catch(Exception e)
	        {
	        	logger.log("Exception caught"+e.toString());
	        }	   
			return "true".toString();
		}
		catch (IOException e) {
            throw new RuntimeException(e);
		}
	}
    public String validate(String input)
    {
    	if(input!=null){
    	if(input.length()>0)
    		return input;
    	else
    		return "$";
    	}
    	return "$$";
    }
    public String getWinning_team(Match match,String id)
    {
    	String team="";
    	if(match.getHomeTeam().getId().equals(id))
    		team=match.getHomeTeam().getName();
    	else
    		team=match.getAwayTeam().getName();
    	return team;
    }
}

