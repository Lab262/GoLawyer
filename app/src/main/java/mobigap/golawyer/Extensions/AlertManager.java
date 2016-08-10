package mobigap.golawyer.Extensions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;

/**
 * Created by luisresende on 23/07/16.
 */
public class AlertManager {
    public static void showAlert(FragmentActivity activity, String message, String positive, String negative,
                          DialogInterface.OnClickListener onClickListener){
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(positive, onClickListener)
                .setNegativeButton(negative, null)
                .create()
                .show();
    }
}
