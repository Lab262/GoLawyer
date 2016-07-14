package mobigap.golawyer.Extensions;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import mobigap.golawyer.R;

/**
 * Created by luisresende on 14/07/16.
 */

public class CameraConfiguration {

    public static final int KEY_GET_PICTURE = 0;

    private static final String TITLE_ALERT_CAMERA = "Selecione uma foto";

    public static void getPicture(AppCompatActivity appCompatActivity){
        //Image by Camera
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent chooserIntent = Intent.createChooser(takePicture, TITLE_ALERT_CAMERA);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickPhoto});

        appCompatActivity.startActivityForResult(chooserIntent, KEY_GET_PICTURE);
    }
}
