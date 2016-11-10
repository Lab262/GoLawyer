package mobigap.golawyer.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luisresende on 24/08/16.
 */
public class CommentModel {

    private String profileImageUrl;
    private String name;
    private String comment;
    private int evaluation;
    private String id;
    private byte [] imageBytes;

    private static String keyID = "id_comentario";
    private static String keyName = "nome";
    private static String keyComment = "comentario";
    private static String keyEvaluation = "qtd_estrelas";
    private static String keyPhoto = "foto";

    public CommentModel(JSONObject jsonObject){
        try {
            this.id = jsonObject.getString(keyID);
            this.name = jsonObject.getString(keyName);
            this.comment = jsonObject.getString(keyComment);
            this.evaluation = Integer.parseInt(jsonObject.getString(keyEvaluation));;
            this.profileImageUrl = jsonObject.getString(keyPhoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public String getId() {
        return id;
    }

    public static String getKeyID() {
        return keyID;
    }

    public static String getKeyName() {
        return keyName;
    }

    public static String getKeyComment() {
        return keyComment;
    }

    public static String getKeyEvaluation() {
        return keyEvaluation;
    }

    public static String getKeyPhoto() {
        return keyPhoto;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
