package com.crickdata.upload.s3;
/*
 * Copyright 2010-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.cricdata.model.Statistics;
import com.cricdata.userinterface.MyUI;


public class UploadLiveData {

    public static void main(String[] args) throws IOException {
    	UploadLiveData upload = new UploadLiveData();
    	upload.uploadToS3("2015_12_10_22_21_21",true);
    	
    }
    
    

    /**
     * Creates a temporary file with text data to demonstrate uploading a file
     * to Amazon S3
     *
     * @return A newly created temporary file with text data.
     *
     * @throws IOException
     */
    
    private static File readMatchFile(String fileName)  {
		File f = new File(fileName);
		return f;
    }

    /**
     * Displays the contents of the specified input stream as text.
     *
     * @param input
     *            The input stream to display as text.
     *
     * @throws IOException
     */
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
    
    public Map<String,Date> uploadToS3(String fileName,boolean type) throws IOException{

        Statistics statistics = new Statistics();
    	Map<String, Date> perfMap= new HashMap<String, Date>();
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

        AmazonS3 s3 = new AmazonS3Client(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(usWest2);   
        String bucketName;
        if(!type) bucketName = "cricmatchinfo";    
        else bucketName = "cricmatchinfoseries";   
        String key = fileName.replace(".json", "").trim();
        try {	
            perfMap.put("S3INSERTREQ", new Date());
            statistics.setS3Req(new Date());
            File f = readMatchFile(fileName);
            
            double bytes = f.length();
        	double kilobytes = (bytes / 1024);
			System.out.println("Details :"+kilobytes);
            s3.putObject(new PutObjectRequest(bucketName, key, f));
            statistics.setSize(String.valueOf(kilobytes));
            
            S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
            perfMap.put("S3SAVERES", object.getObjectMetadata().getLastModified());
            statistics.setKey(key);
            statistics.setS3Res(object.getObjectMetadata().getLastModified());
            MyUI.stats.add(statistics);
           
            displayTextInputStream(object.getObjectContent());

            ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix("My"));
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
            }
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } 
        return perfMap;
    }
}
