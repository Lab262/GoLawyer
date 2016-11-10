package mobigap.golawyer.Extensions;

import android.app.Activity;
import android.view.View;
import android.view.ViewStub;

/**
 * Created by thiagobernardes on 19/07/16.
 */
public class LayoutManagerExtension {


    public static void addLayout(Activity sourceActivity,int stubId, int layoutID){

        ViewStub stub = (ViewStub) sourceActivity.findViewById(stubId);
        stub.setLayoutResource(layoutID);
        stub.inflate();
    }

}
