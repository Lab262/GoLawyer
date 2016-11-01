package mobigap.golawyer.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luisresende on 01/11/16.
 */

public class EvaluationModel {

    private int total;
    private int totalFive;
    private int totalFour;
    private int totalThree;
    private int totalTwo;
    private int totalOne;

    private static String keyTotal = "total_estrelas";
    private static String keyTotalFive = "estrelas_5";
    private static String keyTotalFour = "estrelas_4";
    private static String keyTotalThree = "estrelas_3";
    private static String keyTotalTwo = "estrelas_2";
    private static String keyTotalOne = "estrelas_1";

    public EvaluationModel(JSONObject jsonObject){
        try {
            this.total = Integer.parseInt(jsonObject.getString(keyTotal));
            this.totalFive = Integer.parseInt(jsonObject.getString(keyTotalFive));
            this.totalFour = Integer.parseInt(jsonObject.getString(keyTotalFour));
            this.totalThree = Integer.parseInt(jsonObject.getString(keyTotalThree));
            this.totalTwo = Integer.parseInt(jsonObject.getString(keyTotalTwo));
            this.totalOne = Integer.parseInt(jsonObject.getString(keyTotalOne));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
