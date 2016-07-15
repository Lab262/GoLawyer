package mobigap.golawyer.Register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Enums.WhereFound;
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
    WhereFound whereFound;
    RadioButton cellPhoneRadioButton,officeRadioButton,bothRadioButton,agreeRadioButton,disagreeRadioButton;
    Boolean agreeTerms;

    private final int SIZE_PROFILE_PHOTO = 350;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getInstanceViews();
        setPropertiesViews();
        adjustLayoutHeader();
    }

    private void getInstanceViews() {
        headerPersonalInformation = findViewById(R.id.headerPersonalInformation);
        namePersonalInformation = (TextView) headerPersonalInformation.findViewById(R.id.nameHeader);
        expandablePersonalInformation = (ExpandableRelativeLayout) findViewById(R.id.personal_information);

        headerWhereFound = findViewById(R.id.headerWhereFound);
        nameWhereFound = (TextView) headerWhereFound.findViewById(R.id.nameHeader);
        expandableWhereFound = (ExpandableRelativeLayout) findViewById(R.id.where_found);
        cellPhoneRadioButton = (RadioButton) expandableWhereFound.findViewById(R.id.cellPhoneRadioButton);
        officeRadioButton = (RadioButton) expandableWhereFound.findViewById(R.id.officeRadioButton);
        bothRadioButton = (RadioButton) expandableWhereFound.findViewById(R.id.bothRadioButton);

        headerOfficeInformation = findViewById(R.id.headerOfficeInformation);
        nameOfficeInformation = (TextView) headerOfficeInformation.findViewById(R.id.nameHeader);
        expandableOfficeInformation = (ExpandableRelativeLayout) findViewById(R.id.office_information);

        headerBankInformation = findViewById(R.id.headerBankInformation);
        nameBankInformation = (TextView) headerBankInformation.findViewById(R.id.nameHeader);
        expandableBankInformation = (ExpandableRelativeLayout) findViewById(R.id.bank_information);

        headerTermsUse = findViewById(R.id.headerTermsUse);
        nameTermsUse = (TextView) headerTermsUse.findViewById(R.id.nameHeader);
        expandableTermsUse = (ExpandableRelativeLayout) findViewById(R.id.terms_use);
        agreeRadioButton = (RadioButton) expandableTermsUse.findViewById(R.id.agreeRadioButton);
        disagreeRadioButton = (RadioButton) expandableTermsUse.findViewById(R.id.disagreeRadioButton);

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

        cellPhoneRadioButton.setOnClickListener(this);
        officeRadioButton.setOnClickListener(this);
        bothRadioButton.setOnClickListener(this);
        agreeRadioButton.setOnClickListener(this);
        disagreeRadioButton.setOnClickListener(this);
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
            case R.id.cellPhoneRadioButton:
                whereFound = WhereFound.CELL_PHONE;
                unselectedWhereFoundRadioButtons();
                break;
            case R.id.officeRadioButton:
                whereFound = WhereFound.OFFICE;
                unselectedWhereFoundRadioButtons();
                break;
            case R.id.bothRadioButton:
                whereFound = WhereFound.CELL_AND_OFFICE;
                unselectedWhereFoundRadioButtons();
                break;
            case R.id.agreeRadioButton:
                agreeTerms = true;
                unselectedTermsRadioButtons();
                break;
            case R.id.disagreeRadioButton:
                agreeTerms = false;
                unselectedTermsRadioButtons();
                break;
        }
    }

    private void unselectedWhereFoundRadioButtons(){
        switch (whereFound){
            case CELL_PHONE:
                officeRadioButton.setChecked(false);
                bothRadioButton.setChecked(false);
                break;
            case OFFICE:
                cellPhoneRadioButton.setChecked(false);
                bothRadioButton.setChecked(false);
                break;
            case CELL_AND_OFFICE:
                cellPhoneRadioButton.setChecked(false);
                officeRadioButton.setChecked(false);
                break;
        }

    }

    private void unselectedTermsRadioButtons(){

        if (agreeTerms){
            disagreeRadioButton.setChecked(false);
        }else {
            agreeRadioButton.setChecked(false);
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
