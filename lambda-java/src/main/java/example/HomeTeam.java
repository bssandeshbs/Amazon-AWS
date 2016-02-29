package example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
public class HomeTeam {
    boolean isBatting;
    String id;
    String name;
    String shortName;
    public boolean getIsBatting() {
       return isBatting;
   }
   public void setIsBatting(boolean isBatting) {
       this.isBatting = isBatting;
   }
   public String getId() {
       return id;
   }
   public void setId(String id) {
       this.id = id;
   }
   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
   public String getShortName() {
       return shortName;
   }
   public void setShortName(String shortName) {
       this.shortName = shortName;
   }

}
