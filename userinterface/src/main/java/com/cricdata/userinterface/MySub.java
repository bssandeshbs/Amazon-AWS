package com.cricdata.userinterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.cricdata.model.MatchBean;
import com.cricdata.model.PerfDetail;
import com.cricdata.model.Statistics;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

//Define a sub-window by inheritance
class MySub extends Window {
	BeanContainer<String, PerfDetail> beans;
	
 public MySub(Statistics stats) {
     super("Detailed Statistics Table"); // Set window caption
     center();
     this.setWidth(800,Unit.PIXELS);
     this.setHeight(500,Unit.PIXELS);
     
     
 	 List<PerfDetail> beanList = getPeformanceDetails(stats);
	 System.out.println(beanList.size());

     
     beans = new BeanContainer<String, PerfDetail>(
    		 PerfDetail.class);

		// Use the name property as the item ID of the bean
		beans.setBeanIdProperty("id");

		// Add some beans to it
		beans.addAll(beanList);

		// Bind a table to it
		Table table = new Table("", beans);
		table.setSelectable(true);
		table.setVisibleColumns(new Object[] { "name", "value"});
		table.setColumnHeaders(new String[] { "Name", "Value"});
		table.setHeight(8,Unit.CM);
     // Some basic content for the window
     VerticalLayout content = new VerticalLayout();
     content.setSpacing(true);
     content.setMargin(true);
     setContent(content);
     
     // Disable the close button
     setClosable(false);

     // Trivial logic for closing the sub-window
     Button ok = new Button("Close Window");
     ok.addClickListener(new ClickListener() {
         public void buttonClick(ClickEvent event) {
             close(); // Close the sub-window
         }
     });
     content.addComponent(table);
     content.addComponent(ok);
 }
  List<PerfDetail> getPeformanceDetails(Statistics stat) {
	  List<PerfDetail> detail = new ArrayList<PerfDetail>();
	  PerfDetail detail1 = new PerfDetail();
	  detail1.setName("Time taken to insert into S3");
	  RandomString ran = new RandomString(10);
	  if(stat.getS3Res() !=null && stat.getS3Res() !=null) {
		  long res =  getDateDiff(stat.getS3Req(),stat.getS3Res(),TimeUnit.MILLISECONDS);
		  detail1.setValue(String.valueOf(res)+" ms");	   		 
		  detail1.setId(ran.nextString());
	  }
	  PerfDetail detail2 = new PerfDetail();
	  detail2.setName("Size of the file inserted into S3 (KiloBytes) ");
	  detail2.setId(ran.nextString());
	  detail2.setValue(stat.getSize());
	  
	  PerfDetail detail5 = new PerfDetail();
	  detail5.setName("Total Number of records processed in Dynamo DB");
	  detail5.setId(ran.nextString());
	  detail5.setValue(stat.getTotalRecords());
	  
	  if(stat.getS3Res() !=null && stat.getS3Res() !=null) {
		  long res =  getDateDiff(stat.getS3Req(),stat.getS3Res(),TimeUnit.MILLISECONDS);
		  detail1.setValue(String.valueOf(res)+" ms");	   		 
		  detail1.setId(ran.nextString());
	  }

	  detail.add(detail2);
	  detail.add(detail1);
	  
	  if(stat.getS3Res() !=null && stat.getLambdaStart() !=null) {
		  long res =  getDateDiff(stat.getS3Res(),stat.getLambdaStart(),TimeUnit.MILLISECONDS);
		 
		  PerfDetail detail3 = new PerfDetail();
		  detail3.setName("Time taken to Start Lambda Processing on S3 insert");
		  detail3.setValue(String.valueOf(res)+" ms");	   		 
		  detail3.setId(ran.nextString());
		  detail.add(detail3);		  
	  }
	
	  if(stat.getDynamoComplete() !=null && stat.getLambdaStart() !=null) {
		  long res =  getDateDiff(stat.getLambdaStart(),stat.getDynamoComplete(),TimeUnit.MILLISECONDS);
		 
		  PerfDetail detail4 = new PerfDetail();
		  detail4.setName("Time taken for insertion into Dynamo DB ");
		  detail4.setValue(String.valueOf(res)+" ms");	   		 
		  detail4.setId(ran.nextString());
		  detail.add(detail4);		  
	  }
	  detail.add(detail5);

	  if(stat.getDynamoComplete() !=null && stat.getS3Req() !=null) {
		  long res =  getDateDiff(stat.getS3Req(),stat.getDynamoComplete(),TimeUnit.MILLISECONDS);
		 
		  PerfDetail detail4 = new PerfDetail();
		  detail4.setName("Overall Timetaken to insert download and insert into Dynamo DB ");
		  detail4.setValue(String.valueOf(res)+" ms");	   		 
		  detail4.setId(ran.nextString());
		  detail.add(detail4);		  
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