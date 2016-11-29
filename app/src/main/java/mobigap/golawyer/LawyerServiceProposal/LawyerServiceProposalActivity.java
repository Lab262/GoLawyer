package mobigap.golawyer.LawyerServiceProposal;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Model.EvaluationModel;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_service_proposal);
        Bundle extras = getIntent().getExtras();

        getInstanceViews();
        adjustLayout();

        if (extras!=null){
            int positionLawyer = extras.getInt(LawyerModel.keyIdLawyer);
            profileImageBytes = extras.getByteArray(LawyerModel.keyPhoto);
            lawyerModel = ApplicationState.sharedState().getLawyersRequestModels().get(positionLawyer);
            setPropertiesViewsLawyer();
        }
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
]        radioGroup.check(idRadioButton);
    }
}
