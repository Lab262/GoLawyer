package mobigap.golawyer.LawyerServiceProposal;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Enums.TypeDemand;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.CustomRadioButton;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Model.EvaluationModel;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

public class LawyerServiceProposalActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    private View profileInformationHeader;
    private ImageButton ratingButton;
    private CircleImageView circleImageViewProfile;
    private ImageView backgroundImageViewProfile;
    private TextView nameTextView, hasOABTextView, numberAttendanceTextView, numberCompletedTextView;
    private final int CONST_IMAGE_BLUR = 25;
    private LawyerModel lawyerModel;
    private byte[] profileImageBytes;
    private View headerDemandSelection, headerDetailCopy;
    private ExpandableRelativeLayout expandableDemandSelection, expandableDetailCopy;
    private TextView nameDemandSelection, nameDatailCopy;
    private RadioGroup selectDemandRadios;
    private ImageButton sendProposalButton;
    private ProgressDialog progressDialog;
    private EditText valueProposalEditText,localEditText, observationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_service_proposal);
        setTitle(getString(R.string.title_activity_proposal));
        Bundle extras = getIntent().getExtras();

        getInstanceViews();
        adjustLayout();

        if (extras!=null){
            int positionLawyer = extras.getInt(LawyerModel.keyIdLawyer);
            profileImageBytes = extras.getByteArray(LawyerModel.keyPhoto);
            lawyerModel = ApplicationState.sharedState().getLawyersRequestModels().get(positionLawyer);
        }else {
            lawyerModel = ApplicationState.sharedState().getLawyerModel();
            profileImageBytes = lawyerModel.getImageBytes();
        }

        setPropertiesViewsLawyer();
        setPropertysRadioButtons();
    }

    private void getInstanceViews(){
        profileInformationHeader = findViewById(R.id.headerProfile);
        View profileHeader = profileInformationHeader.findViewById(R.id.profileHeader);
        ratingButton = (ImageButton)profileHeader.findViewById(R.id.ratingButton);
        View registerProfilePhoto = profileInformationHeader.findViewById(R.id.registerProfilePhoto);
        circleImageViewProfile = (CircleImageView) registerProfilePhoto.findViewById(R.id.profileImage);
        backgroundImageViewProfile = (ImageView) registerProfilePhoto.findViewById(R.id.backgroundPhotoImage);
        nameTextView = (TextView) registerProfilePhoto.findViewById(R.id.nameTextView);
        hasOABTextView = (TextView) registerProfilePhoto.findViewById(R.id.hasOABTextView);
        numberAttendanceTextView = (TextView) profileHeader.findViewById(R.id.numberAttendanceTextView);
        numberCompletedTextView = (TextView) profileHeader.findViewById(R.id.numberCompletedTextView);

        headerDemandSelection = findViewById(R.id.headerDemand);
        nameDemandSelection = (TextView) headerDemandSelection.findViewById(R.id.nameHeader);
        expandableDemandSelection = (ExpandableRelativeLayout) findViewById(R.id.demand_selection);
        selectDemandRadios = (RadioGroup) expandableDemandSelection.findViewById(R.id.selectDemandRadios);

        headerDetailCopy = findViewById(R.id.headerCopy);
        nameDatailCopy = (TextView) headerDetailCopy.findViewById(R.id.nameHeader);
        expandableDetailCopy = (ExpandableRelativeLayout) findViewById(R.id.detail_copy);
        valueProposalEditText = (EditText) expandableDetailCopy.findViewById(R.id.valueProposalEditText);
        localEditText = (EditText) expandableDetailCopy.findViewById(R.id.localEditText);
        observationEditText = (EditText) expandableDetailCopy.findViewById(R.id.observationEditText);

        sendProposalButton = (ImageButton) findViewById(R.id.sendProposalButton);
    }

    private void adjustLayout() {
        View registerProfilePhoto = profileInformationHeader.findViewById(R.id.registerProfilePhoto);
        ImageButton cameraButton = (ImageButton) registerProfilePhoto.findViewById(R.id.cameraButton);
        cameraButton.setVisibility(View.INVISIBLE);
    }

    private void setPropertiesViewsLawyer(){
        nameTextView.setText(lawyerModel.getName());
        hasOABTextView.setText(lawyerModel.getMiniCurriculum());
        numberAttendanceTextView.setText(String.valueOf(lawyerModel.getTotalOrders()));
        numberCompletedTextView.setText(String.valueOf(lawyerModel.getTotalConcludedOrders()));
        EvaluationModel evaluationModel = lawyerModel.getEvaluation();
        ratingButton.setImageResource(evaluationModel.getIdTotal());

        Bitmap imageBitmap = ImageConvert.getDecode64ImageStringFromByte(profileImageBytes);
        circleImageViewProfile.setImageBitmap(imageBitmap);
        Bitmap blurred = ImageConvert.blurRenderScript(this,imageBitmap, CONST_IMAGE_BLUR);
        backgroundImageViewProfile.setImageBitmap(blurred);

        nameDemandSelection.setText(R.string.placeholder_select_demand);
        nameDatailCopy.setText(R.string.placeholder_detail_copy);

        headerDemandSelection.setOnClickListener(this);
        headerDetailCopy.setOnClickListener(this);

        selectDemandRadios.setOnCheckedChangeListener(this);
        sendProposalButton.setOnClickListener(this);
    }

    private void setPropertysRadioButtons(){
        int count = selectDemandRadios.getChildCount();
        int numberButton=0;
        for (int i=0;i<count;i++) {
            View view = selectDemandRadios.getChildAt(i);
            if (view instanceof CustomRadioButton) {
                CustomRadioButton radioButton = (CustomRadioButton) view;
                radioButton.setTypeDemand(TypeDemand.getTypeDemandByOrdinal(numberButton));
                numberButton++;
            }
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.headerCopy:
                toggleExpandableLayout(expandableDetailCopy,headerDetailCopy);
                break;
            case R.id.headerDemand:
                toggleExpandableLayout(expandableDemandSelection,headerDemandSelection);
                break;
            case R.id.sendProposalButton:
                makeProposalOrder();
                break;

        }

    }

    private void toggleExpandableLayout(ExpandableRelativeLayout expandableRelativeLayout, View headerView){
        expandableRelativeLayout.toggle();
        ImageView arrow = (ImageView) headerView.findViewById(R.id.headerArrow);
        if (expandableRelativeLayout.isExpanded()){
            arrow.setImageResource(R.drawable.ic_arrow_select_item);
        }else {
            arrow.setImageResource(R.drawable.ic_arrow_down_select_item);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int idRadioButton = radioGroup.getCheckedRadioButtonId();
        radioGroup.check(idRadioButton);
    }

    private void makeProposalOrder(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_message_dialog));

        CustomRadioButton customRadioButton = (CustomRadioButton) selectDemandRadios.findViewById(selectDemandRadios.getCheckedRadioButtonId());

        String valueProposal = valueProposalEditText.getText().toString();
        String local = localEditText.getText().toString();
        String observation = observationEditText.getText().toString();
        String idUser = ApplicationState.sharedState().getCurrentUser(this).getId();
        String idLawyer = lawyerModel.getIdLawyer();

        Boolean validFields = true;

        if (valueProposal.equals("")){
            FeedbackManager.createToast(this,getString(R.string.error_value_proposal));
            validFields = false;
        }else {
            if (observation.equals("")){
                FeedbackManager.createToast(this,getString(R.string.error_observation_proposal));
                validFields = false;
            }
        }

        if (validFields){
            UserRequest.setOrder(customRadioButton.getTypeDemand(),valueProposal,local,observation,idUser,idLawyer,
                    new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            progressDialog.dismiss();
                            createToast(response);
                            finish();
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
    }

    private void createToast(JSONObject response){
        FeedbackManager.createToast(this,response);
    }

    private void createErrorToast(){
        FeedbackManager.feedbackErrorResponse(this,progressDialog);
    }
}
