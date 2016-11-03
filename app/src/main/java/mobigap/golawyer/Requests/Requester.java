package mobigap.golawyer.Requests;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
}
