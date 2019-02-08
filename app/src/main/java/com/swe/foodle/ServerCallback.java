package com.swe.foodle;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The successful return from a server call.
 */
public interface ServerCallback{
    /**
     * From a successful server request a JSONObject should be returned.
     * @param result the result of the api call
     * @return the successful result of the api call
     */
    public JSONObject onSuccess(JSONObject result);

}

