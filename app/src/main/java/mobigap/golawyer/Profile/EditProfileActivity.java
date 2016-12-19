package mobigap.golawyer.Profile;

import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Extensions.CameraConfiguration;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Model.ProfileInformationEditModel;
import mobigap.golawyer.Model.UserDataModel;
import mobigap.golawyer.Model.UserInformationModel;
import mobigap.golawyer.Model.UserModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Profile.Information.ProfileInformationEditListAdapter;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

public class  EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private View header;
    private ImageButton cameraButton;
    private ImageView backgroundImageViewProfile;
    private CircleImageView circleImageViewProfile;
    private ListView profileEditInformationListView;
    private TextView nameTextView, hasOABTextView;
    private ProgressDialog progressDialog;
    private ProfileInformationEditListAdapter adapter;

    private final int CONST_IMAGE_BLUR = 25;
    private final int SIZE_PHOTO = 350;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getInstanceViews();
        getImage();
        adjustLayoutListView();
        setPropertiesViews();
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
                setDataProfile();
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
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) backgroundImageViewProfile.getLayoutParams();
        params.height = SIZE_PHOTO;
        backgroundImageViewProfile.setLayoutParams(params);
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

    private void loadRequestedProfileEditInformationList(ArrayList<ProfileInformationEditModel> profileEditInformationsRequested) {
         adapter = new ProfileInformationEditListAdapter(this, profileEditInformationsRequested);
        profileEditInformationListView.setAdapter(adapter);
    }

    private ArrayList<ProfileInformationEditModel> getProfileData() {

        ArrayList<ProfileInformationEditModel> profileInformationModels = new ArrayList<>();

        for (UserDataModel userDataModel: ApplicationState.sharedState().getCurrentUserDataModels()){
            ProfileInformationEditModel informationEditModel = new ProfileInformationEditModel(
                    userDataModel.getDataTitle(), userDataModel.getDataValue(),userDataModel.getType(), userDataModel.getDataName());
            profileInformationModels.add(informationEditModel);
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

    private void setDataProfile(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_message_dialog));

        final String photo = ImageConvert.getEncoded64ImageStringFromImageView(circleImageViewProfile);

        UserRequest.updateProfileData(ApplicationState.sharedState().getCurrentUser(this).getId(), photo, adapter.data,
                new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                createToast(response);
                if (Requester.haveSuccess(response)){
                    UserModel userModel = new UserModel(response);
                    ApplicationState.sharedState().setCurrentUser(userModel,getApplicationContext());
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                createErrorToast();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                createErrorToast();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                createErrorToast();
            }
        });
    }

    private void createToast(JSONObject response){
        FeedbackManager.createToast(this,response);
    }

    private void createErrorToast(){
        FeedbackManager.feedbackErrorResponse(this,progressDialog);
    }


}
