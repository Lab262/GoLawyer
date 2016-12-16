package mobigap.golawyer.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thiagoMB on 7/29/16.
 */
public class ServiceStatusChatModel {

    public String title;
    public String description;
    public String content;

    private static String keyName = "nome";
    private static String keyMessage = "mensagem";
    private static String keyDate = "data_hora";

    public ServiceStatusChatModel(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public ServiceStatusChatModel(JSONObject jsonObject) {
        try {
            this.title = jsonObject.getString(keyName);
            this.description = jsonObject.getString(keyDate);
            this.content = jsonObject.getString(keyMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
