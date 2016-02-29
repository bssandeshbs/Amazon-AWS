package example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
public class Scores {
    String homeScore;
    String homeOvers;
    String awayScore;
    String awayOvers;
    public String getHomeOvers() {
        return homeOvers;
    }

    public void setHomeOvers(String homeOvers) {
        this.homeOvers = homeOvers;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }

    public String getAwayOvers() {
        return awayOvers;
    }

    public void setAwayOvers(String awayOvers) {
        this.awayOvers = awayOvers;
    }


    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }
    

}
