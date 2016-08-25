package mobigap.golawyer.Model;

/**
 * Created by luisresende on 24/08/16.
 */
public class CommentModel {

    public String profileImageUrl;
    public String name;
    public String comment;
    public int evaluation;

    public CommentModel(String profileImageUrl, String name, String comment, int evaluation) {
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.comment = comment;
        this.evaluation = evaluation;
    }
}
