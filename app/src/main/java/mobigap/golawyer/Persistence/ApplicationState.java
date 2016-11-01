package mobigap.golawyer.Persistence;

import mobigap.golawyer.Model.UserModel;

/**
 * Created by luisresende on 01/11/16.
 */

public class ApplicationState {

    public UserModel currentUser;

    private static ApplicationState ourInstance = new ApplicationState();

    public static ApplicationState sharedState() {
        return ourInstance;
    }

    private ApplicationState(){

    }

}
