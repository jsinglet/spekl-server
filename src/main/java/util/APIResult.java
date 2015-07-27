package util;

import com.google.gson.JsonObject;

/**
 * Created by jls on 7/22/2015.
 */
public class APIResult {

    private static final String SPM_VERSION = "0.0.1";
    private boolean failed;
    private String reason;
    private String message;

    public APIResult(boolean failed, String reason){
        this.failed = failed;
        this.reason = reason;
    }

    public APIResult(String message){
        this.failed = false;
        this.message = message;
    }

    public boolean isFailed(){
        return failed;
    }

    public String getReason(){
        return reason;
    }


    public String toJSON(){
        JsonObject obj = new JsonObject();

        obj.addProperty("failed", isFailed());
        if(failed) {
            obj.addProperty("reason", getReason());
        }else{
            obj.addProperty("message", getMessage());
        }

        obj.addProperty("version", SPM_VERSION);

        return obj.toString();
    }

    public String getMessage() {
        return message;
    }
}
