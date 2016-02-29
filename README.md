# Amazon-AWS

CricData

The CricData web application provides useful information about cricket sport to the users. The application is developed using Amazon S3, Amazon Dynamo DB and AWS Lambda.
The application is hosted on the Amazon S3 static website. The RESTFUL web service interface provides client to store and retrieve information. The user request generated at S3
triggers a lambda that performs a pull operation on Dynamo DB.

1. The client access the static webpages or makes a restful web service call to get the resource which is hosted in the amazon S3 server.
2. On validating the request parameters in Amazon S3, the corresponding object is read and then an event is published by S3 that cause the Lambda function to be invoked.
3. Lambda Function 1 processes the incoming events from S3 and executes the code by passing the event to the handler in Lambda function. The Lambda function interacts with the dynamo db to retrieve the information
4. A search is performed in Dynamo DB to retrieve all the records that match the input request
5. The results of the query operation are processed in the Lambda function and it returns the response to the Amazon S3.
6. The results are processed and http response object is prepared in the form of XML, JSON or plain text and the response is returned to the user
