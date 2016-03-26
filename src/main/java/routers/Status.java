package routers;

import verily.lang.*;

public class Status{

     public static final Content isOk(boolean status){
	 if(status){
	     return new TextContent("OK");
	 }

	 return new TextContent("FAIL");
     }
}
