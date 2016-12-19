package mobigap.golawyer.Requests;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luisresende on 22/11/16.
 */

public class LawyerRequest {

    private static String urlGetLawyers = "GetAdvogados";
    private static String urlGetLawyer = "GetAdvogado";

    private static String keyType = "tipo"; //Gps
    private static String keyLatitude = "latitude";
    private static String keyLogitude = "longitude";

    private static String nameDefaultTypeGps = "gps";

    public static void getLawyers(String latitude, String longitude, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyType,nameDefaultTypeGps);
        params.put(keyLatitude,latitude);
        params.put(keyLogitude,longitude);
        Requester.postRequest(urlGetLawyers,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

    public static void getLawyer(String idLawyer, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(UserRequest.keyIdLawyer,idLawyer);
        Requester.postRequest(urlGetLawyer,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

}

