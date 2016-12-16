package mobigap.golawyer.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luisresende on 22/11/16.
 */

public class LawyerModel {

    private String idLawyer;
    private String name;
    private String oab;
    private String miniCurriculum;
    private EvaluationModel evaluation;
    private ArrayList<CommentModel> comments;
    private int totalOrders;
    private int totalConcludedOrders;
    private String status;
    private String photo;
    private Double latitude;
    private Double longitude;
    private byte [] imageBytes;


    public static String keyIdLawyer = "id_advogado";
    private static String keyName = "nome";
    private static String keyOab = "oab";
    private static String keyMiniCurriculum = "mini_curriculo";
    private static String keyEvaluations = "avaliacao";
    private static String keyComments = "comentarios";
    private static String keyTotalOrders = "qtd_atendimentos";
    private static String keyTotalConcludedOrders = "qtd_atendimentos_concluidos";
    private static String keyStatus = "status_advogado";
    public static String keyPhoto = "foto";
    private static String keyLatitude= "latitude";
    private static String keyLongitude= "longitude";
    public static String keyItensDataModel = "itens";

    public LawyerModel(ServiceRequestModel serviceRequestModel){
        this.name = serviceRequestModel.getNameLawyer();
        this.photo = serviceRequestModel.getProfileImageUrl();
        this.status = serviceRequestModel.getStatus();
    }

    public LawyerModel(JSONObject jsonObject){
        try {
            this.idLawyer = jsonObject.getString(keyIdLawyer);
            this.name = jsonObject.getString(keyName);
            this.oab = jsonObject.getString(keyOab);
            this.miniCurriculum = jsonObject.getString(keyMiniCurriculum);
            this.evaluation = parseEvaluations(jsonObject.getJSONObject(keyEvaluations));
            this.comments = parseComments(jsonObject.getJSONArray(keyComments));
            this.totalOrders = Integer.parseInt(jsonObject.getString(keyTotalOrders));
            this.totalConcludedOrders = Integer.parseInt(jsonObject.getString(keyTotalConcludedOrders));
            this.status = jsonObject.getString(keyStatus);
            this.photo = jsonObject.getString(keyPhoto);
            this.latitude = Double.parseDouble(jsonObject.getString(keyLatitude));
            this.longitude = Double.parseDouble(jsonObject.getString(keyLongitude));
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

    private EvaluationModel parseEvaluations(JSONObject evaluation){
        EvaluationModel evaluationModel = new EvaluationModel(evaluation);
        return evaluationModel;
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

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public String getPhoto() {
        return photo;
    }

    public String getMiniCurriculum() {
        return miniCurriculum;
    }

    public String getOab() {
        return oab;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getIdLawyer() {
        return idLawyer;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
