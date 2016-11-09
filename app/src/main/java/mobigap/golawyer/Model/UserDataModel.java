package mobigap.golawyer.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luisresende on 07/11/16.
 */

public class UserDataModel {

    private String dataTitle;
    private String dataName;
    private String dataValue;
    private String type;
    private Boolean isVisible;

    private static String keyDataTitle = "data_title";
    private static String keyDataName = "data_name";
    private static String keyDataValue = "data_value";
    private static String keyType = "type";
    private static String keyIsView = "view";
    public static String keyItensDataModel = "itens";

    private static String viewVisible = "true";
    private static String viewInvisible = "false";

    public UserDataModel(JSONObject jsonObject){
        try {
            this.dataTitle = jsonObject.getString(keyDataTitle);
            this.dataName = jsonObject.getString(keyDataName);
            this.dataValue = jsonObject.getString(keyDataValue);
            this.type = jsonObject.getString(keyType);
            setVisible(jsonObject.getString(keyIsView));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setVisible(String visible) {
        if (visible.compareTo(viewVisible)==0){
            this.isVisible = true;
        } else if (visible.compareTo(viewInvisible)==0){
            this.isVisible = false;
        }
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
