package mobigap.golawyer.Extensions;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;

/**
 * Created by luisresende on 01/11/16.
 */

public class FeedbackManager {

    static public void createToast(Context context, String message){

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    static public void createToast(Context context, JSONObject response){
        try {
            createToast(context,response.getString(Requester.keyMessage));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static public ProgressDialog createProgressDialog (Context context, String message){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.show();
        return dialog;
    }

    static public void feedbackErrorResponse(Context context, ProgressDialog progressDialog){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
        FeedbackManager.createToast(context,context.getString(R.string.placeholder_error_connection));
    }
}
