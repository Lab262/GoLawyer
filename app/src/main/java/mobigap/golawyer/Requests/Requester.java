package mobigap.golawyer.Requests;

import android.graphics.Bitmap;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by luisresende on 31/10/16.
 */

public class Requester {
    public static String baseUrl = "http://www.golawyer.online/";
    public static String token = "/8a6c2d37c22220206a865861cab4202c";

    public static String keyResponse = "retorno";
    public static String keyMessage = "msg";
    public static String responseSuccess = "true";

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void postRequest(String endPoint, RequestParams requestParams, JsonHttpResponseHandler jsonHttpResponseHandler){
        String url = baseUrl + endPoint + token;
        client.post(url,requestParams,jsonHttpResponseHandler);
    }

    public static void getRequest(String url, AsyncHttpResponseHandler asyncHttpResponseHandler){
        client.get(url,null, asyncHttpResponseHandler);
    }

    public static Boolean haveSuccess(JSONObject jsonObject){
        try {
            String response = jsonObject.getString(keyResponse);
            if (response.equals(responseSuccess)){
                return true;
            }else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static RequestParams getRequestParams(Map<String,String> params){
        RequestParams requestParams = new RequestParams();

        for (Map.Entry<String, String> entry : params.entrySet()){
            requestParams.put(entry.getKey(),entry.getValue());
        }

        return requestParams;
    }

    public static JSONArray getJsonArray(JSONObject response, String keyArray){
        JSONArray jsonArray = null;
        try {
            jsonArray = response.getJSONArray(keyArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONObject getJsonObject(JSONArray arrayResponse, int position){
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) arrayResponse.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
