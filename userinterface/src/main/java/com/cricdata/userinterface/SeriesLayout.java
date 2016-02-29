package com.cricdata.userinterface;

import java.util.List;

import com.cricdata.model.DynamoBean;
import com.cricdata.model.MatchBean;
import com.cricdata.model.SeriesBean;
import com.dynamo.DynamoData;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class SeriesLayout extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8773445539705669326L;
	BeanContainer<String, SeriesBean> beans ;

	public SeriesLayout() {

		Label l = new Label("Content to be updated for Series Statistics");
		l.setStyleName("status");

		try {
			DynamoData data = new DynamoData();
			data.init();
			List<SeriesBean> beanList = data.getSeriesData();
			System.out.println(beanList.size());
			
			beans =
					new BeanContainer<String, SeriesBean>(SeriesBean.class);

			// Use the name property as the item ID of the bean
			beans.setBeanIdProperty("seriesId");

			// Add some beans to it
			beans.addAll(beanList);
			
			// Bind a table to it
			Table table = new Table("", beans);
			table.setVisibleColumns(new Object[]{"name","status","startDateTime","endDatetime"});
			table.setColumnHeaders(new String[]{"Name","Status","Start Date Time","End Date Time"});	
			
			table.setStyleName("matchTable");
			
			table.setWidth(20, Unit.CM);
			this.addComponent(table);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
