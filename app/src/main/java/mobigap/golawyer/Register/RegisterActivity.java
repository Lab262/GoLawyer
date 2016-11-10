package mobigap.golawyer.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.CameraConfiguration;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Login.LoginActivity;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private View headerPersonalInformation, headerTermsUse;
    private TextView namePersonalInformation, nameTermsUse;
    private ExpandableRelativeLayout expandablePersonalInformation, expandableTermsUse;
    private View registerProfilePhoto;
    private ImageButton cameraButton, finalizeRegisterButton;
    private CircleImageView circleImageViewProfile;
    private ImageView backgroundImageViewProfile;
    private Button linkTermsUse, linkPrivacyPolicy;
    private TypeProfile typeProfile;
    private ProgressDialog progressDialog;

    private View personalInformation, termsUse;
    private EditText nameEditText,emailEditText,registerPasswordEditText,confirmPasswordEditText,oabEditText,cpfEditText,
            phoneEditText,cepEditText,adressEditText,stateEditText,cityEditText,neighborhoodEditText,curriculumEditText;

    private final int SIZE_PROFILE_PHOTO = 350;
    private final int CONST_IMAGE_BLUR = 25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            typeProfile = TypeProfile.getTypeProfileByOrdinal(extras.getInt("typeProfile"));
        }
        getInstanceViews();
        setPropertiesViews();
        adjustLayoutHeader();
    }

    private void getInstanceViews() {
        headerPersonalInformation = findViewById(R.id.headerPersonalInformation);
        namePersonalInformation = (TextView) headerPersonalInformation.findViewById(R.id.nameHeader);
        expandablePersonalInformation = (ExpandableRelativeLayout) findViewById(R.id.personal_information);

        headerTermsUse = findViewById(R.id.headerTermsUse);
        nameTermsUse = (TextView) headerTermsUse.findViewById(R.id.nameHeader);
        expandableTermsUse = (ExpandableRelativeLayout) findViewById(R.id.terms_use);

        registerProfilePhoto = findViewById(R.id.registerProfilePhoto);
        cameraButton = (ImageButton) registerProfilePhoto.findViewById(R.id.cameraButton);
        circleImageViewProfile = (CircleImageView) registerProfilePhoto.findViewById(R.id.profileImage);
        backgroundImageViewProfile = (ImageView) registerProfilePhoto.findViewById(R.id.backgroundPhotoImage);

        finalizeRegisterButton = (ImageButton) findViewById(R.id.finalizeRegisterButton);

        termsUse = findViewById(R.id.termsUse);
        linkTermsUse = (Button) termsUse.findViewById(R.id.linkTermsUse);
        linkPrivacyPolicy = (Button) termsUse.findViewById(R.id.linkPrivacyPolicy);

        personalInformation = findViewById(R.id.personalInformation);
        nameEditText = (EditText) personalInformation.findViewById(R.id.nameEditText);
        emailEditText = (EditText) personalInformation.findViewById(R.id.emailEditText);
        registerPasswordEditText = (EditText) personalInformation.findViewById(R.id.registerPasswordEditText);
        confirmPasswordEditText = (EditText) personalInformation.findViewById(R.id.confirmPasswordEditText);
        oabEditText = (EditText) personalInformation.findViewById(R.id.oabEditText);
        cpfEditText = (EditText) personalInformation.findViewById(R.id.cpfEditText);
        phoneEditText = (EditText) personalInformation.findViewById(R.id.phoneEditText);
        cepEditText = (EditText) personalInformation.findViewById(R.id.cepEditText);
        adressEditText = (EditText) personalInformation.findViewById(R.id.adressEditText);
        stateEditText = (EditText) personalInformation.findViewById(R.id.stateEditText);
        cityEditText = (EditText) personalInformation.findViewById(R.id.cityEditText);
        neighborhoodEditText = (EditText) personalInformation.findViewById(R.id.neighborhoodEditText);
        curriculumEditText = (EditText) personalInformation.findViewById(R.id.curriculumEditText);
    }

    private void setPropertiesViews() {
        setTitle(R.string.title_activity_register);

        namePersonalInformation.setText(R.string.name_personal_information);
        nameTermsUse.setText(R.string.name_terms_use);

        headerPersonalInformation.setOnClickListener(this);
        headerTermsUse.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        finalizeRegisterButton.setOnClickListener(this);
        linkTermsUse.setOnClickListener(this);
        linkPrivacyPolicy.setOnClickListener(this);
    }

    private void adjustLayoutHeader(){
        View informationView = registerProfilePhoto.findViewById(R.id.informationView);
        informationView.setVisibility(View.INVISIBLE);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) circleImageViewProfile.getLayoutParams();
        params.height = SIZE_PROFILE_PHOTO;
        params.width = SIZE_PROFILE_PHOTO;
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_TOP, 0);
        circleImageViewProfile.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerPersonalInformation:
                toggleExpandableLayout(expandablePersonalInformation, headerPersonalInformation);
                break;
            case R.id.headerTermsUse:
                toggleExpandableLayout(expandableTermsUse, headerTermsUse);
                break;
            case R.id.cameraButton:
                CameraConfiguration.getPicture(this);
                break;
            case R.id.finalizeRegisterButton:
                registerUser();
                break;
            case R.id.linkTermsUse:
                openLink(getString(R.string.link_terms_use));
                break;
            case R.id.linkPrivacyPolicy:
                openLink(getString(R.string.link_privacy_policy));
                break;
        }
    }

    private void openLink(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void toggleExpandableLayout(ExpandableRelativeLayout expandableRelativeLayout, View headerView){
        expandableRelativeLayout.toggle();
        ImageView arrow = (ImageView) headerView.findViewById(R.id.headerArrow);
        if (expandableRelativeLayout.isExpanded()){
            arrow.setImageResource(R.drawable.forward_mini_arrow);
        }else {
            arrow.setImageResource(R.drawable.down_mini_arrow);
        }
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

    private void registerUser(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_message_dialog));

        String typeAccount = TypeProfile.getStringTypeProfile(typeProfile.ordinal());

        String photo = ImageConvert.getEncoded64ImageStringFromImageView(circleImageViewProfile);
        String name = nameEditText.getText().toString();
        String oab = oabEditText.getText().toString();
        String doc = cpfEditText.getText().toString();
        String phoneNumber = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String curriculum = curriculumEditText.getText().toString();
        String zipCode = cepEditText.getText().toString();
        String address = adressEditText.getText().toString();
        String neighborhood = neighborhoodEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String state = stateEditText.getText().toString();
        String password = registerPasswordEditText.getText().toString();

        UserRequest.registerUser(typeAccount,photo,name,oab,doc,phoneNumber,email,curriculum,zipCode,address,
                neighborhood,city,state,password, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                createToast(response);
                if (Requester.haveSuccess(response)){
                    ActivityManager.changeActivityAndRemoveParentActivity(RegisterActivity.this, LoginActivity.class);
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
