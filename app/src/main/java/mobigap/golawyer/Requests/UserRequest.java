package mobigap.golawyer.Requests;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luisresende on 01/11/16.
 */

public class UserRequest {

    private static String urlLogin = "GetLogin";
    private static String urlForgotPassword = "SetRecuperarSenha";

    private static String keyEmail = "login";
    private static String keyPassword = "senha";

    public static void login(String email, String password, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyEmail,email);
        params.put(keyPassword,password);
        Requester.postRequest(urlLogin,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

    public static void forgotPassword(String email, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyEmail,email);
        Requester.postRequest(urlForgotPassword,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

}
