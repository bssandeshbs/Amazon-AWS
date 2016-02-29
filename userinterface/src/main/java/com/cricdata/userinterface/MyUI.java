package com.cricdata.userinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.cricdata.cric.livescore.api.Client;
import com.cricdata.model.MatchBean;
import com.cricdata.model.SeriesBean;
import com.cricdata.model.Statistics;
import com.crickdata.upload.s3.UploadLiveData;
import com.dynamo.DynamoData;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.cricdata.userinterface.MyAppWidgetset")
public class MyUI extends UI {

    /**
	 * 
	 */
	public static List<Statistics> stats = new ArrayList<Statistics>();
	
	

	private static final long serialVersionUID = -4181025099917639552L;
	Map<String,Date> resultMap;
	StatisticsLayout statLayout;
	SeriesLayout sLayout;
	 MatchLayout mLayout;
	 Label l1;
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        Button button = new Button("Download Latest Scores");
        button.addClickListener(new Button.ClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 8177499019184504717L;

			@Override
            public void buttonClick(ClickEvent event) {
				l1.setValue("Last Sync at - " +new Date().toString());
				 Notification.show("",
		                  "Download has been Initiated succesfully",
		                  Notification.Type.HUMANIZED_MESSAGE);
                Client client = new Client();
        		String fileName = client.download_cric_data();
        	
        		UploadLiveData upload = new UploadLiveData();
            	try {
            		resultMap = upload.uploadToS3(fileName,false);
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            }
        });
        Button seriesButton = new Button("Download Latest Series");
        seriesButton.addClickListener(new Button.ClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 8177499019184504717L;

			@Override
            public void buttonClick(ClickEvent event) {
             //   layout.addComponent(new Label("Download has been Initiated succesfully"));
                Notification.show("","Download has been Initiated succesfully",
		                  Notification.Type.HUMANIZED_MESSAGE);
                Client client = new Client();
        		String fileName = client.download_cric_series_data();
        		UploadLiveData upload = new UploadLiveData();
        		l1.setValue("Last Sync at - " +new Date().toString());
            	try {
        			upload.uploadToS3(fileName,true);
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            }
        });
        
        HorizontalLayout hLayout = new HorizontalLayout();
       
        Label label = new Label("Welcome to Cric Data Application !");
        label.setStyleName("boldLabel");
        hLayout.addComponent(label);
        layout.addComponent(hLayout);
        
        TabSheet tabsheet = new TabSheet();
        mLayout = new MatchLayout();
        tabsheet.addTab(mLayout,"Match Statistics");
        
        sLayout = new SeriesLayout();
        tabsheet.addTab(sLayout,"Series Statistics");
        
        statLayout = new StatisticsLayout();
        tabsheet.addTab(statLayout,"Statistics");
  
        l1 = new Label("",ContentMode.HTML);
        
        layout.addComponent(l1);
        
        Button refreshTable = new Button("Refresh Tables");
        refreshTable.addClickListener(new Button.ClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 8177499019184504717L;

			@Override
            public void buttonClick(ClickEvent event) {
				getStats();
				 Notification.show("",
		                  "Refresh has been Initiated succesfully",
		                  Notification.Type.HUMANIZED_MESSAGE);
				statLayout.beans.removeAllItems();
				statLayout.averageStat.removeAllItems();
				statLayout.averageStat.addAll(statLayout.getAverage(getStats()));
				
				statLayout.beans.addAll(getStats());
				DynamoData data = new DynamoData();
				List<SeriesBean> beanList = data.getSeriesData();
				sLayout.beans.removeAllItems();
				sLayout.beans.addAll(beanList);
				List<MatchBean> matchList = data.getData();
				mLayout.beans.addAll(matchList);
				
            }
        });
        

        HorizontalLayout hButtonLayout = new HorizontalLayout();
        hButtonLayout.setSpacing(true);
        hButtonLayout.addComponent(button);
        hButtonLayout.addComponent(seriesButton);
        hButtonLayout.addComponent(refreshTable);
 
        layout.addComponent(hButtonLayout);
        layout.addComponent(tabsheet);
      

    }
	
	public static List<Statistics> getStats() {
		DynamoData data = new DynamoData();
		return data.getPerfData();
	}

	public static void setStats(List<Statistics> stats) {
		MyUI.stats = stats;
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5299866373310023418L;
    }
}
