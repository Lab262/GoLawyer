package mobigap.golawyer.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luisresende on 01/11/16.
 */

public class UserModel {

    private String id;
    private String name;
    private String typeAccount;
    private String email;
    private String cpf;
    private String oab;
    private String curriculum;
    private String photo;
    private String cep;
    private String neighborhood;
    private String city;
    private int totalOrders;
    private int totalConcludedOrders;
    private ArrayList<CommentModel> comments;
    private ArrayList<EvaluationModel> evaluations;

    private static String keyID = "id_user";
    private static String keyName = "nome";
    private static String keyTypeAccount = "tipo_conta";
    private static String keyEmail = "email";
    private static String keyCpf = "cpf";
    private static String keyOab = "oab";
    private static String keyCurriculum = "curriculo";
    private static String keyPhoto = "imagem";
    private static String keyCep = "cep";
    private static String keyNeighborhood = "bairro";
    private static String keyCity = "cidade";
    private static String keyTotalOrders = "qtd_atendimentos";
    private static String keyTotalConcludedOrders = "qtd_atendimentos_concluidos";
    private static String keyComments = "comentarios";
    private static String keyEvaluations = "avaliacao";

    public UserModel(JSONObject jsonObject){
        try {
            this.id = jsonObject.getString(keyID);
            this.name = jsonObject.getString(keyName);
            this.typeAccount = jsonObject.getString(keyTypeAccount);
            this.email = jsonObject.getString(keyEmail);
            this.cpf = jsonObject.getString(keyCpf);
            this.oab = jsonObject.getString(keyOab);
            this.curriculum = jsonObject.getString(keyCurriculum);
            this.photo = jsonObject.getString(keyPhoto);
            this.cep = jsonObject.getString(keyCep);
            this.neighborhood = jsonObject.getString(keyNeighborhood);
            this.city = jsonObject.getString(keyCity);
            this.totalOrders = Integer.parseInt(jsonObject.getString(keyTotalOrders));
            this.totalConcludedOrders = Integer.parseInt(jsonObject.getString(keyTotalConcludedOrders));
            this.comments = parseComments(jsonObject.getJSONArray(keyComments));
            this.evaluations = parseEvaluations(jsonObject.getJSONArray(keyEvaluations));
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

    private ArrayList<EvaluationModel> parseEvaluations(JSONArray arrayEvaluations){

        ArrayList<EvaluationModel> evaluations = new ArrayList<>();

        for (int i=0; i<arrayEvaluations.length(); i++){
            try {
                EvaluationModel evaluation = new EvaluationModel(arrayEvaluations.getJSONObject(i));
                evaluations.add(evaluation);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return evaluations;
    }

}
