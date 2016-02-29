package com.cricdata.userinterface;

import java.util.List;

import com.cricdata.model.DynamoBean;
import com.cricdata.model.MatchBean;
import com.dynamo.DynamoData;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class MatchLayout extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4810585890019661600L;
	BeanContainer<String, MatchBean> beans;

	public MatchLayout() {
		DynamoData data = new DynamoData();
		try {
			data.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<MatchBean> beanList = data.getData();
		System.out.println(beanList.size());

		beans = new BeanContainer<String, MatchBean>(
				MatchBean.class);

		// Use the name property as the item ID of the bean
		beans.setBeanIdProperty("matchId");

		// Add some beans to it
		beans.addAll(beanList);

		// Bind a table to it
		Table table = new Table("", beans);
		table.setSelectable(true);

		table.setVisibleColumns(new Object[] { "series","homeTeam", "awayTeam", "status","currentMatchesState", "matchType"
				, "won", 
				"homeOvers", "homeScore", "awayOvers" });
		table.setColumnHeaders(new String[] { "Series","Home Team","Away Team" , "Status","Current Match State", "Match Type"
				, "Result", 
				"Home Overs", "Home Score", "Away Overs" });

		table.setStyleName("matchTable");

		table.setWidth(32, Unit.CM);
		this.addComponent(table);

	}
}
