package mobigap.golawyer.Persistence;

import java.util.ArrayList;

import mobigap.golawyer.Model.UserDataModel;
import mobigap.golawyer.Model.UserModel;

/**
 * Created by luisresende on 01/11/16.
 */

public class ApplicationState {

    public UserModel currentUser;
    public ArrayList<UserDataModel> userDataModels;

    private static ApplicationState ourInstance = new ApplicationState();

    public static ApplicationState sharedState() {
        return ourInstance;
    }

    private ApplicationState(){
    }

}
