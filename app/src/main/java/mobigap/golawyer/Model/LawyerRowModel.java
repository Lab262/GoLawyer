package mobigap.golawyer.Model;

/**
 * Created by luisresende on 12/08/16.
 */
public class LawyerRowModel {

    public String profileImageUrl;
    public String title;
    public String description;

    public LawyerRowModel(String profileImageUrl, String title, String description) {
        this.profileImageUrl = profileImageUrl;
        this.title = title;
        this.description = description;
    }
}
