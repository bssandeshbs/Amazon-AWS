package example;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
public class MatchList {
	private List<Match> matches;
	

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	
}
