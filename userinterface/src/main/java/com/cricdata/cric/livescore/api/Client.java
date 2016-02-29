package com.cricdata.cric.livescore.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.crickdata.upload.s3.UploadLiveData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Client to download cricket data");
		
		Client client = new Client();
		String fileName = client.download_cric_series_data();
		System.out.println("Upload file :"+fileName);
		
		UploadLiveData upload = new UploadLiveData();
    	try {
			upload.uploadToS3(fileName,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String download_cric_data() {

		try {
			Unirest.setObjectMapper(new ObjectMapper() {
				private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
				= new com.fasterxml.jackson.databind.ObjectMapper();

				public <T> T readValue(String value, Class<T> valueType) {
					try {
						return jacksonObjectMapper.readValue(value, valueType);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}

				public String writeValue(Object value) {
					try {
						return jacksonObjectMapper.writeValueAsString(value);
					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}
				}
			});

			HttpResponse<JsonNode> jsonResponse = Unirest.post("https://dev132-cricket-live-scores-v1.p.mashape.com/matches.php")
					.header("accept", "application/json")
					.header("X-Mashape-Key", "IopKo8Oeqgmsh1ECQ50MWIpcPCLZp1eY0uUjsnfAM0kbOaLS2L")
					.asJson();

			JsonNode result = jsonResponse.getBody();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		
			Date date = new Date(); 
			String fileName = dateFormat.format(date).toString()+".json";
			System.out.println(fileName);
			File file = new File(fileName);
			
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(result.toString());
			bw.close();

			return fileName;
			/*// Response to Object
			HttpResponse<Body> bookResponse = Unirest.post("https://dev132-cricket-live-scores-v1.p.mashape.com/matches.php")
					.header("accept", "application/json")
					.header("X-Mashape-Key", "IopKo8Oeqgmsh1ECQ50MWIpcPCLZp1eY0uUjsnfAM0kbOaLS2L")
					.asObject(Body.class);
			Body bookObject = bookResponse.getBody();

			if(bookObject !=null) {
				System.out.println(jsonResponse.getBody());
			}*/
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String download_cric_series_data() {

		try {
			Unirest.setObjectMapper(new ObjectMapper() {
				private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
				= new com.fasterxml.jackson.databind.ObjectMapper();

				public <T> T readValue(String value, Class<T> valueType) {
					try {
						return jacksonObjectMapper.readValue(value, valueType);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}

				public String writeValue(Object value) {
					try {
						return jacksonObjectMapper.writeValueAsString(value);
					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}
				}
			});

			HttpResponse<JsonNode> jsonResponse =Unirest.get("https://dev132-cricket-live-scores-v1.p.mashape.com/series.php")
                    .header("X-Mashape-Key", "IopKo8Oeqgmsh1ECQ50MWIpcPCLZp1eY0uUjsnfAM0kbOaLS2L")
                    .header("Accept", "application/json").asJson();

			JsonNode result = jsonResponse.getBody();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		
			Date date = new Date(); 
			String fileName = dateFormat.format(date).toString()+".json";
			System.out.println(fileName);
			File file = new File(fileName);
			
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(result.toString());
			bw.close();

			return fileName;
			/*// Response to Object
			HttpResponse<Body> bookResponse = Unirest.post("https://dev132-cricket-live-scores-v1.p.mashape.com/matches.php")
					.header("accept", "application/json")
					.header("X-Mashape-Key", "IopKo8Oeqgmsh1ECQ50MWIpcPCLZp1eY0uUjsnfAM0kbOaLS2L")
					.asObject(Body.class);
			Body bookObject = bookResponse.getBody();

			if(bookObject !=null) {
				System.out.println(jsonResponse.getBody());
			}*/
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
