package routers;

import util.APIResult;
import verily.lang.*;

public class Package{

     public static final Content create(String project, String username, APIResult result){
         JSONContent content = new JSONContent();
          content.setContent(result.toJSON());
          return content;
     }
}
