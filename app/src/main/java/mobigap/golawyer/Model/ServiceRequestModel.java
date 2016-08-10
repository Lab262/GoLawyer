package mobigap.golawyer.Model;

import android.media.Image;

/**
 * Created by thiagoMB on 7/29/16.
 */
public class ServiceRequestModel {

    public String profileImageUrl;
    public String title;
    public String description;
    public Boolean isWarning;

    public ServiceRequestModel(String profileImageUrl, String title, String description, Boolean isWarning) {
        this.profileImageUrl = profileImageUrl;
        this.title = title;
        this.description = description;
        this.isWarning = isWarning;
    }

}
