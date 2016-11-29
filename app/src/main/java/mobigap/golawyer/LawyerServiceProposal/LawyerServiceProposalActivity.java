package mobigap.golawyer.LawyerServiceProposal;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Model.EvaluationModel;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Model.UserDataModel;
import mobigap.golawyer.Model.UserModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.LawyerRequest;

public class LawyerServiceProposalActivity extends AppCompatActivity {

    private View profileInformationHeader;
    private ImageButton ratingButton;
    private CircleImageView circleImageViewProfile;
    private ImageView backgroundImageViewProfile;
    private TextView nameTextView, hasOABTextView, numberAttendanceTextView, numberCompletedTextView;
    private ProgressDialog progressDialog;
    private LayoutInflater defaultInflater;
    private ArrayList<UserDataModel> userDataModels;
    private final int CONST_IMAGE_BLUR = 25;
    private LawyerModel lawyerModel;
    private byte[] profileImageBytes;
    private final int id_make_proposal_button = 10;
    private final int id_logout_button = 0;

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
    }
}
