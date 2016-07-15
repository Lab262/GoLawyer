package mobigap.golawyer.Register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Extensions.CameraConfiguration;
import mobigap.golawyer.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    View headerPersonalInformation, headerWhereFound, headerOfficeInformation, headerBankInformation, headerTermsUse;
    TextView namePersonalInformation, nameWhereFound, nameOfficeInformation, nameBankInformation, nameTermsUse;
    ExpandableRelativeLayout expandablePersonalInformation, expandableWhereFound, expandableOfficeInformation,
            expandableBankInformation, expandableTermsUse;
    View registerProfilePhoto;
    ImageButton cameraButton;
    CircleImageView circleImageViewProfile;
    ImageView backgroundImageViewProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getInstanceViews();
        setPropertiesViews();
    }

    private void getInstanceViews() {
        headerPersonalInformation = findViewById(R.id.headerPersonalInformation);
        namePersonalInformation = (TextView) headerPersonalInformation.findViewById(R.id.nameHeader);
        expandablePersonalInformation = (ExpandableRelativeLayout) findViewById(R.id.personal_information);

        headerWhereFound = findViewById(R.id.headerWhereFound);
        nameWhereFound = (TextView) headerWhereFound.findViewById(R.id.nameHeader);
        expandableWhereFound = (ExpandableRelativeLayout) findViewById(R.id.where_found);

        headerOfficeInformation = findViewById(R.id.headerOfficeInformation);
        nameOfficeInformation = (TextView) headerOfficeInformation.findViewById(R.id.nameHeader);
        expandableOfficeInformation = (ExpandableRelativeLayout) findViewById(R.id.office_information);

        headerBankInformation = findViewById(R.id.headerBankInformation);
        nameBankInformation = (TextView) headerBankInformation.findViewById(R.id.nameHeader);
        expandableBankInformation = (ExpandableRelativeLayout) findViewById(R.id.bank_information);

        headerTermsUse = findViewById(R.id.headerTermsUse);
        nameTermsUse = (TextView) headerTermsUse.findViewById(R.id.nameHeader);
        expandableTermsUse = (ExpandableRelativeLayout) findViewById(R.id.terms_use);

        registerProfilePhoto = findViewById(R.id.registerProfilePhoto);
        cameraButton = (ImageButton) registerProfilePhoto.findViewById(R.id.cameraButton);
        circleImageViewProfile = (CircleImageView) registerProfilePhoto.findViewById(R.id.profileImage);
        backgroundImageViewProfile = (ImageView) registerProfilePhoto.findViewById(R.id.backgroundPhotoImage);
    }

    private void setPropertiesViews() {
        setTitle(R.string.title_activity_register);

        namePersonalInformation.setText(R.string.name_personal_information);
        nameWhereFound.setText(R.string.name_where_found);
        nameOfficeInformation.setText(R.string.name_office_information);
        nameBankInformation.setText(R.string.name_bank_information);
        nameTermsUse.setText(R.string.name_terms_use);

        headerPersonalInformation.setOnClickListener(this);
        headerWhereFound.setOnClickListener(this);
        headerOfficeInformation.setOnClickListener(this);
        headerBankInformation.setOnClickListener(this);
        headerTermsUse.setOnClickListener(this);

        cameraButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerPersonalInformation:
                toggleExpandableLayout(expandablePersonalInformation, headerPersonalInformation);
                break;
            case R.id.headerWhereFound:
                toggleExpandableLayout(expandableWhereFound, headerWhereFound);
                break;
            case R.id.headerOfficeInformation:
                toggleExpandableLayout(expandableOfficeInformation, headerOfficeInformation);
                break;
            case R.id.headerBankInformation:
                toggleExpandableLayout(expandableBankInformation, headerBankInformation);
                break;
            case R.id.headerTermsUse:
                toggleExpandableLayout(expandableTermsUse, headerTermsUse);
                break;
            case R.id.cameraButton:
                CameraConfiguration.getPicture(this);
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
