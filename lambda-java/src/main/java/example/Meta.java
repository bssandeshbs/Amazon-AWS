package example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
	public String getUpcomingMatchCount() {
		return upcomingMatchCount;
	}
	public void setUpcomingMatchCount(String upcomingMatchCount) {
		this.upcomingMatchCount = upcomingMatchCount;
	}
	public String getInProgressMatchCount() {
		return inProgressMatchCount;
	}
	public void setInProgressMatchCount(String inProgressMatchCount) {
		this.inProgressMatchCount = inProgressMatchCount;
	}
	public String getCompletedMatchCount() {
		return completedMatchCount;
	}
	public void setCompletedMatchCount(String completedMatchCount) {
		this.completedMatchCount = completedMatchCount;
	}
	String upcomingMatchCount;
	String inProgressMatchCount;
	String completedMatchCount;

}
