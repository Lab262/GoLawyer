package mobigap.golawyer.Requests;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static void postRequest(String endPoint, RequestParams requestParams, ResponseHandlerInterface responseHandlerInterface){
        String url = baseUrl + endPoint + token;
        client.post(url,requestParams,responseHandlerInterface);
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
}
