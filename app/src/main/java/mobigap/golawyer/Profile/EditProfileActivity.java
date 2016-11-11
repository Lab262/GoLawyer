package mobigap.golawyer.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Extensions.CameraConfiguration;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Model.ProfileInformationModel;
import mobigap.golawyer.Model.UserDataModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Profile.Information.ProfileInformationListAdapter;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.UserRequest;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private View header;
    private ImageButton cameraButton;
    private ImageView backgroundImageViewProfile;
    private CircleImageView circleImageViewProfile;
    private ListView profileEditInformationListView;
    private TextView nameTextView, hasOABTextView;

    private final int CONST_IMAGE_BLUR = 25;
    private final int SIZE_PHOTO = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getInstanceViews();
        getImage();
        adjustLayoutListView();
        setPropertiesViews();
        adjustLayoutHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save_edit_profile:
                //TODO: Logica para salvar o perfil caso tenha modificado algo...
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getInstanceViews(){
        header = getLayoutInflater().inflate(
                R.layout.register_profile_photo, null, false);

        nameTextView = (TextView) header.findViewById(R.id.nameTextView);
        hasOABTextView = (TextView) header.findViewById(R.id.hasOABTextView);
        cameraButton = (ImageButton) header.findViewById(R.id.cameraButton);
        circleImageViewProfile = (CircleImageView) header.findViewById(R.id.profileImage);
        backgroundImageViewProfile = (ImageView) header.findViewById(R.id.backgroundPhotoImage);
    }

    private void setPropertiesViews() {
        setTitle(R.string.title_activity_edit_profile);
        cameraButton.setOnClickListener(this);
        nameTextView.setText(ApplicationState.sharedState().getCurrentUser(this).getName());
        hasOABTextView.setText(ApplicationState.sharedState().getCurrentUser(this).getOab());
    }

    private void adjustLayoutHeader(){
        //TODO: Crash aqui... tem que dar um jeito de adicionar esse header com uma altura definida de 200.
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) header.getLayoutParams();
        params.height = SIZE_PHOTO;
        header.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cameraButton:
                CameraConfiguration.getPicture(this);
                break;
        }

    }

    private void adjustLayoutListView() {
        profileEditInformationListView = (ListView) findViewById(R.id.profileEditInformationListView);
        loadRequestedProfileEditInformationList(getProfileData());
        profileEditInformationListView.addHeaderView(header);
    }

    private void loadRequestedProfileEditInformationList(ArrayList<ProfileInformationModel> profileEditInformationsRequested) {
        //TODO: Esse adapter é provisorio, ele é para apresentar informacoes... tem que fazer uma celular praticamente igual,
        //TODO: mudando somente o tipo do texto, nesse adapter é textview e tem que trocar para editTextView, para ser editavel
        ProfileInformationListAdapter adapter = new ProfileInformationListAdapter(this, profileEditInformationsRequested);
        profileEditInformationListView.setAdapter(adapter);
    }

    private ArrayList<ProfileInformationModel> getProfileData() {

        ArrayList<ProfileInformationModel> profileInformationModels = new ArrayList<>();

        for (UserDataModel userDataModel: ApplicationState.sharedState().getCurrentUserDataModels()){
            ProfileInformationModel informationModel = new ProfileInformationModel(userDataModel.getDataTitle(), userDataModel.getDataValue());
            profileInformationModels.add(informationModel);
        }

        return profileInformationModels;
    }

    private void getImage(){
        UserRequest.getImage(ApplicationState.sharedState().getCurrentUser(this).getPhoto(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap imageBitmap = ImageConvert.getDecode64ImageStringFromByte(responseBody);
                circleImageViewProfile.setImageBitmap(imageBitmap);
                Bitmap blurred = ImageConvert.blurRenderScript(getApplicationContext(),imageBitmap, CONST_IMAGE_BLUR);
                backgroundImageViewProfile.setImageBitmap(blurred);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CameraConfiguration.KEY_GET_PICTURE:
                if (resultCode == RESULT_OK) {
                    if (data.getExtras()==null){
                        //Image from Galerry
                        Uri selectedImage = data.getData();
                        circleImageViewProfile.setImageURI(selectedImage);
                        backgroundImageViewProfile.setImageURI(selectedImage);
                        Bitmap bitmap = ((BitmapDrawable)backgroundImageViewProfile.getDrawable()).getBitmap();
                        Bitmap blurred = ImageConvert.blurRenderScript(this,bitmap, CONST_IMAGE_BLUR);
                        backgroundImageViewProfile.setImageBitmap(blurred);
                    }else {
                        //Image from Camera
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        circleImageViewProfile.setImageBitmap(imageBitmap);
                        Bitmap blurred = ImageConvert.blurRenderScript(this,imageBitmap, CONST_IMAGE_BLUR);
                        backgroundImageViewProfile.setImageBitmap(blurred);
                    }

                }
                break;
        }
    }


}
