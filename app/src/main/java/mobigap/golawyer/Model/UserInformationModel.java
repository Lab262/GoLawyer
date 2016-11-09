package mobigap.golawyer.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luisresende on 09/11/16.
 */

public class UserInformationModel {

    private int totalOrders;
    private int totalConcludedOrders;
    private ArrayList<CommentModel> comments;
    private EvaluationModel evaluation;

    private static String keyTotalOrders = "qtd_atendimentos";
    private static String keyTotalConcludedOrders = "qtd_atendimentos_concluidos";
    private static String keyComments = "comentarios";
    private static String keyEvaluations = "avaliacao";
    private static String keyItensUserInformationModel = "dados_de_avaliacao";

    public UserInformationModel(JSONObject jsonObject){
        try {
            JSONObject informationObject = jsonObject.getJSONObject(keyItensUserInformationModel);
            this.totalOrders = Integer.parseInt(informationObject.getString(keyTotalOrders));
            this.totalConcludedOrders = Integer.parseInt(informationObject.getString(keyTotalConcludedOrders));
            this.comments = parseComments(informationObject.getJSONArray(keyComments));
            this.evaluation = parseEvaluations(informationObject.getJSONArray(keyEvaluations));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<CommentModel> parseComments(JSONArray arrayComments){

        ArrayList<CommentModel> comments = new ArrayList<>();

        for (int i=0; i<arrayComments.length(); i++){
            try {
                CommentModel comment = new CommentModel(arrayComments.getJSONObject(i));
                comments.add(comment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return comments;
    }

    private EvaluationModel parseEvaluations(JSONArray arrayEvaluations){

        ArrayList<EvaluationModel> evaluations = new ArrayList<>();

        for (int i=0; i<arrayEvaluations.length(); i++){
            try {
                EvaluationModel evaluation = new EvaluationModel(arrayEvaluations.getJSONObject(i));
                evaluations.add(evaluation);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return evaluations.get(0);
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public int getTotalConcludedOrders() {
        return totalConcludedOrders;
    }

    public ArrayList<CommentModel> getComments() {
        return comments;
    }

    public EvaluationModel getEvaluation() {
        return evaluation;
    }
}
