package com.cricdata.userinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.cricdata.cric.livescore.api.Client;
import com.cricdata.model.AverageStatistics;
import com.cricdata.model.MatchBean;
import com.cricdata.model.PerfDetail;
import com.cricdata.model.SeriesBean;
import com.cricdata.model.Statistics;
import com.cricdata.userinterface.MySub.RandomString;
import com.crickdata.upload.s3.UploadLiveData;
import com.dynamo.DynamoData;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class StatisticsLayout extends VerticalLayout{

	private static final long serialVersionUID = 4810585890019661600L;
	public BeanContainer<String, Statistics> beans;
	public BeanContainer<String, AverageStatistics> averageStat;
	
	Table table ;
	Table avgTable;
	public StatisticsLayout(){ 	
		DynamoData data = new DynamoData();
		System.out.println("Constructor Called");
	try {
		data.init();
	} catch(Exception e) {
		e.printStackTrace();
	}
	List<Statistics> beanList = MyUI.stats;
	System.out.println("Main Called");
	beans = new BeanContainer<String, Statistics>(Statistics.class);
	averageStat = new BeanContainer<String, AverageStatistics>(AverageStatistics.class);

	// Use the name property as the item ID of the bean
	beans.setBeanIdProperty("key");
	averageStat.setBeanIdProperty("id");

	// Add some beans to it
	beans.addAll(beanList);
	averageStat.addAll(getAverage(beanList));
	
	// Bind a table to it
	table = new Table("", beans);
	table.setSelectable(true);

	table.setVisibleColumns(new Object[]{"key","size","totalRecords","s3Req","s3Res","lambdaStart","dynamoComplete"});
	table.setColumnHeaders(new String[]{"S3 Object Key","File Size","Total Records","S3 Req Time","S3 Save Time","Lambda Start","Dynamo Complete"});	
	
	table.setStyleName("matchTable");
	
	table.setWidth(30, Unit.CM);
	table.setHeight(7,Unit.CM);
	
	 Button refreshTable = new Button("View Statistics");
     refreshTable.addClickListener(new Button.ClickListener() {
         /**
			 * 
			 */
			private static final long serialVersionUID = 8177499019184504717L;

			@Override
         public void buttonClick(ClickEvent event) {
				System.out.println("Button Clicked");
			
				Object item = table.getValue();
				if(item != null){
					Statistics group = beans.getItem(item).getBean();
					System.out.println("Group "+group);
					 MySub sub = new MySub(group);
					 UI.getCurrent().addWindow(sub);
				}else{
					Notification.show("",
			                  "Please select a row and click on statistics",
			                  Notification.Type.WARNING_MESSAGE);
				}			       
         }
     });
     
    this.setSpacing(true);
	
	this.addComponent(table);
	this.addComponent(refreshTable);
	
	Label label = new Label("Average Performance Results");
	this.addComponent(label);
	
	avgTable = new Table("", averageStat);
	avgTable.setSelectable(true);
	avgTable.setStyleName("matchTable");
	avgTable.setHeight(8,Unit.CM);
	avgTable.setVisibleColumns(new Object[] { "name", "value"});
	avgTable.setColumnHeaders(new String[] { "Name", "Value"});
	avgTable.setWidth(30, Unit.CM);
	
	this.addComponent(avgTable);
	
	}
	
	public List<AverageStatistics> getAverage(List<Statistics> stats) {
		List<AverageStatistics> detail = new ArrayList<AverageStatistics>();
		Map<String,Long> sumStats = new HashMap<String,Long>(); 
		int counter=0;
		for (Statistics stat : stats) {	
			if(stat.getTotalRecords() !=null) {
				counter++;				 
				  if(stat.getS3Res() !=null && stat.getS3Res() !=null) {
					  long res =  getDateDiff(stat.getS3Req(),stat.getS3Res(),TimeUnit.MILLISECONDS);
					  Long s3 = sumStats.get("s3ins"); 
					  if(s3 == null) sumStats.put("s3ins", res);
					  else sumStats.put("s3ins", s3+res);					
				  }			
				  
				  Long fSize = sumStats.get("fileSize"); 
				  double d = Double.parseDouble(stat.getSize());
				  long x = (long) d; // x = 1234
				  if(fSize == null) sumStats.put("fileSize", x);
				  else sumStats.put("fileSize", fSize+x);
				  
				  Long dRecords = sumStats.get("dRecords"); 
				  System.out.println("Total Records :"+stat.getTotalRecords());
				  System.out.println("Long value :"+stat.getTotalRecords());
				  if(dRecords == null) sumStats.put("dRecords", Long.parseLong(stat.getTotalRecords()));
				  else sumStats.put("dRecords", dRecords+Long.parseLong(stat.getTotalRecords()));
				  
				  if(stat.getS3Res() !=null && stat.getLambdaStart() !=null) {
					  long res =  getDateDiff(stat.getS3Res(),stat.getLambdaStart(),TimeUnit.MILLISECONDS);					  
					  Long processLambda = sumStats.get("lambdaInsert"); 
					  if(processLambda == null) sumStats.put("lambdaInsert", res);
					  else sumStats.put("lambdaInsert", processLambda+res);
				  }
				
				  if(stat.getDynamoComplete() !=null && stat.getLambdaStart() !=null) {
					  long res =  getDateDiff(stat.getLambdaStart(),stat.getDynamoComplete(),TimeUnit.MILLISECONDS);
					  Long dynamoInsert = sumStats.get("dynamoInsert"); 
					  if(dynamoInsert == null) sumStats.put("dynamoInsert", res);
					  else sumStats.put("dynamoInsert", dynamoInsert+res);
				  }
				  

				  if(stat.getDynamoComplete() !=null && stat.getS3Req() !=null) {
					  long res =  getDateDiff(stat.getS3Req(),stat.getDynamoComplete(),TimeUnit.MILLISECONDS);
					  Long overallAvg = sumStats.get("overallAvg"); 
					  if(overallAvg == null) sumStats.put("overallAvg", res);
					  else sumStats.put("overallAvg", overallAvg+res);
				  }							
			}	
			
			 if(sumStats.size()>5) {
				 detail.clear();
				 
				 AverageStatistics detail1 = new AverageStatistics();
				 detail1.setName("Average Time taken to insert into S3");
				 RandomString ran = new RandomString(10); 
				 detail1.setId(ran.nextString());
				 detail1.setValue(String.valueOf((sumStats.get("s3ins")/counter)));
				 detail.add(detail1);
				 
				 AverageStatistics detail2 = new AverageStatistics();
				 detail2.setName("Average Size of the file inserted into S3 (KiloBytes)");
				 detail2.setId(ran.nextString());
				 detail2.setValue(String.valueOf((sumStats.get("fileSize")/counter)));
				 detail.add(detail2);
				 
				 AverageStatistics detail3 = new AverageStatistics();
				 detail3.setName("Average number of records inserted into Dyanamo");
				 detail3.setId(ran.nextString());
				 detail3.setValue(String.valueOf((sumStats.get("dRecords")/counter)));
				 
				 
				 AverageStatistics detail4 = new AverageStatistics();
				 detail4.setName("Average Time taken to Start Lambda Processing on S3 insert");
				 detail4.setId(ran.nextString());
				 detail4.setValue(String.valueOf((sumStats.get("lambdaInsert")/counter)));
				 detail.add(detail4);
				 
				 AverageStatistics detail5 = new AverageStatistics();
				 detail5.setName("Average Time Taken for the insertion into Dyanamo DB");
				 detail5.setId(ran.nextString());
				 detail5.setValue(String.valueOf((sumStats.get("dynamoInsert")/counter)));
				 detail.add(detail5);
				 detail.add(detail3);
				 
				 AverageStatistics detail6 = new AverageStatistics();
				 detail6.setName("Average Overall Timetaken to insert download and insert into Dynamo DB");
				 detail6.setId(ran.nextString());
				 detail6.setValue(String.valueOf((sumStats.get("overallAvg")/counter)));
				 detail.add(detail6);
			 }
			
		}
		return detail;
		
	}
	
	  public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		    long diffInMillies = date2.getTime() - date1.getTime();
		    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
		} 
	
	public static class RandomString { 
		  
		  private static final char[] symbols;
		 
		  static { 
		    StringBuilder tmp = new StringBuilder();
		    for (char ch = '0'; ch <= '9'; ++ch)
		      tmp.append(ch);
		    for (char ch = 'a'; ch <= 'z'; ++ch)
		      tmp.append(ch);
		    symbols = tmp.toString().toCharArray();
		  }    
		 
		  private final Random random = new Random();
		 
		  private final char[] buf;
		 
		  public RandomString(int length) {
		    if (length < 1)
		      throw new IllegalArgumentException("length < 1: " + length);
		    buf = new char[length];
		  } 
		 
		  public String nextString() {
		    for (int idx = 0; idx < buf.length; ++idx) 
		      buf[idx] = symbols[random.nextInt(symbols.length)];
		    return new String(buf);
		  } 
		} 
}
