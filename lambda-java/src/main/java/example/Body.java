package example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
public class Body {
	private MatchList matchList;
	private Meta meta;
	int status;
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	
	public Meta getMeta() {		return meta;	}

	public void setMeta(Meta meta) {this.meta = meta;	}

	public MatchList getMatchList() {		return matchList;	}

	public void setMatchList(MatchList matchList) {		this.matchList = matchList;	}
}
