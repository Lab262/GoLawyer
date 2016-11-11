package mobigap.golawyer.Persistence;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import mobigap.golawyer.Model.UserDataModel;
import mobigap.golawyer.Model.UserInformationModel;
import mobigap.golawyer.Model.UserModel;

/**
 * Created by luisresende on 01/11/16.
 */

public class ApplicationState {

    private UserModel currentUser = null;
    public UserInformationModel currentUserInformationModel = null;
    private ArrayList<UserDataModel> currentUserDataModels = null;

    private static ApplicationState ourInstance = new ApplicationState();
    private static String nameSharedPreferences = "currentUserStorage";

    public static ApplicationState sharedState() {
        return ourInstance;
    }

    private ApplicationState(){
    }

    public UserModel getCurrentUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(nameSharedPreferences, 0); // 0 - for private mode
        String id = preferences.getString(UserModel.keyID,null);
        if (currentUser==null){
            if (id!=null){
                currentUser = new UserModel(preferences);
                return currentUser;
            }else {
                return null;
            }
        }else {
            return currentUser;
        }
    }

    public void setCurrentUser(UserModel currentUser, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(nameSharedPreferences, 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        this.currentUser = currentUser;
        this.currentUser.savePropertyUser(editor);
    }

    public void clearCurrentUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(nameSharedPreferences, 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public ArrayList<UserDataModel> getCurrentUserDataModels() {
        return currentUserDataModels;
    }

    public void setCurrentUserDataModels(ArrayList<UserDataModel> currentUserDataModels) {
        this.currentUserDataModels = currentUserDataModels;
    }
}
