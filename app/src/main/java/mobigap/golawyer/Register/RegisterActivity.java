package mobigap.golawyer.Register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.BottomBarActivity;
import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.CameraConfiguration;
import mobigap.golawyer.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private View headerPersonalInformation, headerTermsUse;
    private TextView namePersonalInformation, nameTermsUse;
    private ExpandableRelativeLayout expandablePersonalInformation, expandableTermsUse;
    private View registerProfilePhoto;
    private ImageButton cameraButton, finalizeRegisterButton;
    private CircleImageView circleImageViewProfile;
    private ImageView backgroundImageViewProfile;
    private Button linkTermsUse, linkPrivacyPolicy;

    private final int SIZE_PROFILE_PHOTO = 350;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

        linkTermsUse = (Button) findViewById(R.id.linkTermsUse);
        linkPrivacyPolicy = (Button) findViewById(R.id.linkPrivacyPolicy);
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
                ActivityManager.changeActivityAndRemoveParentActivity(RegisterActivity.this, BottomBarActivity.class);
                break;
            case R.id.linkTermsUse:
                break;
            case R.id.linkPrivacyPolicy:
                break;
        }
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
                    }else {
                        //Image from Camera
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        circleImageViewProfile.setImageBitmap(imageBitmap);
                        backgroundImageViewProfile.setImageBitmap(imageBitmap);
                    }

                }
                break;
        }
    }
}
