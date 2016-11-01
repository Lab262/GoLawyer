package mobigap.golawyer.Requests;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by luisresende on 31/10/16.
 */

public class Requester {
    public static String baseUrl = "http://www.golawyer.online/";
    public static String token = "/8a6c2d37c22220206a865861cab4202c";

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void postRequest(String url, RequestParams requestParams, ResponseHandlerInterface responseHandlerInterface){
        client.post(url,requestParams,responseHandlerInterface);
    }
}
