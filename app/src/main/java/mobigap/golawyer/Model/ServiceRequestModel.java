package mobigap.golawyer.Model;

import org.json.JSONException;
import org.json.JSONObject;

import mobigap.golawyer.Requests.Requester;

/**
 * Created by thiagoMB on 7/29/16.
 */
public class ServiceRequestModel {

    private String idOrder;
    private String idLawyer;
    private String status;
    private String demand;
    private String value;
    private int step;
    private Boolean isWarning;
    private String profileImageUrl;
    private String nameLawyer;
    private byte [] imageBytes;

    public static String keyIdOrder = "id_pedido";
    public static String keyIdLawyer = "id_advogado";
    public static String keyStatus = "status";
    private static String keyDemand = "demanda";
    private static String keyValue = "valor";
    private static String keyStep = "passo";
    private static String keyIsWarning = "flag_notificacao";
    private static String keyPhoto = "foto";
    private static String keyName = "nome";
    public static String keyItens = "itens";

    public ServiceRequestModel(JSONObject jsonObject) {
        try {
            this.idOrder = jsonObject.getString(keyIdOrder);
            this.idLawyer = jsonObject.getString(keyIdLawyer);
            this.status = jsonObject.getString(keyStatus);
            this.demand = jsonObject.getString(keyDemand);
            this.value = jsonObject.getString(keyValue);
            this.step = Integer.parseInt(jsonObject.getString(keyStep));
            this.isWarning = parseStringBoolean(jsonObject.getString(keyIsWarning));
            this.profileImageUrl = jsonObject.getString(keyPhoto);
            this.nameLawyer = jsonObject.getString(keyName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Boolean parseStringBoolean(String response){
        if (response.equals(Requester.responseSuccess)){
            return true;
        }else {
            return false;
        }
    }

    public Boolean getWarning() {
        return isWarning;
    }

    public String getStatus() {
        return status;
    }

    public String getNameLawyer() {
        return nameLawyer;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdLawyer() {
        return idLawyer;
    }
}
