package example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
public class Venue {
	 String name;
	 String location;
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {	        this.name = name;	    }
	    public String getShortName() {	        return location;	    }
	    public void setShortName(String location) {	        this.location = location;	    }

}
