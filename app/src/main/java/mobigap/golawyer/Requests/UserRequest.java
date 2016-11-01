package mobigap.golawyer.Requests;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luisresende on 01/11/16.
 */

public class UserRequest {

    private static String urlLogin = "GetLogin";
    private static String keyLoginEmail = "login";
    private static String keyLoginPassword = "senha";

    public static void login(String email, String password, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyLoginEmail,email);
        params.put(keyLoginPassword,password);
        Requester.postRequest(urlLogin,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

}
