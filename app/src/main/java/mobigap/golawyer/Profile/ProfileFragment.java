package mobigap.golawyer.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.LawyerServiceProposal.LawyerServiceProposalActivity;
import mobigap.golawyer.Login.LoginActivity;
import mobigap.golawyer.Model.EvaluationModel;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Model.ProfileInformationModel;
import mobigap.golawyer.Model.UserDataModel;
import mobigap.golawyer.Model.UserInformationModel;
import mobigap.golawyer.Model.UserModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Profile.Information.ProfileInformationListAdapter;
import mobigap.golawyer.Protocols.OnFragmentInteractionListener;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private View view,header;
    private ImageButton ratingButton;
    private ListView profileInformationListView;
    private CircleImageView circleImageViewProfile;
    private ImageView backgroundImageViewProfile;
    private TextView nameTextView, hasOABTextView, numberAttendanceTextView, numberCompletedTextView;
    private ProgressDialog progressDialog;
    private LayoutInflater defaultInflater;
    private ArrayList<UserDataModel> userDataModels;
    private UserModel currentUser;
    private byte[] profileImageBytes;
    private final int CONST_IMAGE_BLUR = 25;
    private LawyerModel lawyerModel;
    private int positionLawyer;
    private final int id_make_proposal_button = 10;
    private final int id_logout_button = 0;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    public static ProfileFragment newInstance(LawyerModel lawyerModel, int positionLawyer) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.lawyerModel = lawyerModel;
        fragment.positionLawyer = positionLawyer;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_profile, container, false);
        defaultInflater = inflater;

        //Case if your profile

        getInstanceViews();
        adjustLayout();

        if (lawyerModel==null){
            currentUser = ApplicationState.sharedState().getCurrentUser(getActivity().getApplicationContext());
            getDataProfile();
            getImage(currentUser.getPhoto());
        }else {
            getImage(lawyerModel.getPhoto());
            setPropertiesViewsLawyer();
            adjustLayoutListViewLawyer();
        }



        return this.view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void adjustLayoutListView() {
        profileInformationListView = (ListView) this.view.findViewById(R.id.profileInformationListView);
        loadRequestedProfileInformationList(getProfileData(userDataModels));
        profileInformationListView.addHeaderView(header);
        profileInformationListView.addFooterView(createLogoutButton());
    }

    private void adjustLayoutListViewLawyer() {
        profileInformationListView = (ListView) this.view.findViewById(R.id.profileInformationListView);
        loadRequestedProfileInformationList(getProfileData(getDataModels()));
        profileInformationListView.addHeaderView(header);
        profileInformationListView.addFooterView(createMakeProposalButton());
    }

    private ArrayList<UserDataModel> getDataModels(){
        ArrayList<UserDataModel> userDataModels = new ArrayList<>();
        UserDataModel userDataModel = new UserDataModel();
        userDataModel.setVisible(true);
        userDataModel.setDataTitle("Curriculo");
        userDataModel.setDataValue("");
        userDataModels.add(userDataModel);
        return userDataModels;
    }

    private ImageButton createLogoutButton(){

        ImageButton logoutButton = new ImageButton(getActivity());
        logoutButton.setId(Integer.valueOf(id_logout_button));
        logoutButton.setBackgroundColor(Color.WHITE);
        logoutButton.setImageResource(R.drawable.button_logout);
        logoutButton.setOnClickListener(this);
        logoutButton.setPadding(0,10,0,10);
        return logoutButton;
    }

    private ImageButton createMakeProposalButton(){

        ImageButton makeProposalButton = new ImageButton(getActivity());
        makeProposalButton.setId(Integer.valueOf(id_make_proposal_button));
        makeProposalButton.setBackgroundColor(Color.WHITE);
        makeProposalButton.setImageResource(R.drawable.button_make_proposal);
        makeProposalButton.setOnClickListener(this);
        makeProposalButton.setPadding(0,10,0,10);
        return makeProposalButton;
    }

    private void getInstanceViews(){
        header = defaultInflater.inflate(
                R.layout.fragment_header_profile, null, false);
        View profileHeader = header.findViewById(R.id.profileHeader);
        ratingButton = (ImageButton)profileHeader.findViewById(R.id.ratingButton);
        View registerProfilePhoto = header.findViewById(R.id.registerProfilePhoto);
        circleImageViewProfile = (CircleImageView) registerProfilePhoto.findViewById(R.id.profileImage);
        backgroundImageViewProfile = (ImageView) registerProfilePhoto.findViewById(R.id.backgroundPhotoImage);
        nameTextView = (TextView) registerProfilePhoto.findViewById(R.id.nameTextView);
        hasOABTextView = (TextView) registerProfilePhoto.findViewById(R.id.hasOABTextView);
        numberAttendanceTextView = (TextView) profileHeader.findViewById(R.id.numberAttendanceTextView);
        numberCompletedTextView = (TextView) profileHeader.findViewById(R.id.numberCompletedTextView);
    }

    private void setPropertiesViews(){
        ratingButton.setOnClickListener(this);
        nameTextView.setText(currentUser.getName());
        hasOABTextView.setText(currentUser.getOab());
        numberAttendanceTextView.setText(String.valueOf(ApplicationState.sharedState().currentUserInformationModel.getTotalOrders()));
        numberCompletedTextView.setText(String.valueOf(ApplicationState.sharedState().currentUserInformationModel.getTotalConcludedOrders()));
        EvaluationModel evaluationModel = ApplicationState.sharedState().currentUserInformationModel.getEvaluation();
        ratingButton.setImageResource(evaluationModel.getIdTotal());
    }

    private void setPropertiesViewsLawyer(){
        ratingButton.setOnClickListener(this);
        nameTextView.setText(lawyerModel.getName());
        hasOABTextView.setText(lawyerModel.getMiniCurriculum());
        numberAttendanceTextView.setText(String.valueOf(lawyerModel.getTotalOrders()));
        numberCompletedTextView.setText(String.valueOf(lawyerModel.getTotalConcludedOrders()));
        EvaluationModel evaluationModel = lawyerModel.getEvaluation();
        ratingButton.setImageResource(evaluationModel.getIdTotal());
    }

    private void adjustLayout() {
        View registerProfilePhoto = header.findViewById(R.id.registerProfilePhoto);
        ImageButton cameraButton = (ImageButton) registerProfilePhoto.findViewById(R.id.cameraButton);
        cameraButton.setVisibility(View.INVISIBLE);
    }

    private void loadRequestedProfileInformationList(ArrayList<ProfileInformationModel> profileInformationsRequested) {
        ProfileInformationListAdapter adapter = new ProfileInformationListAdapter(getActivity().getApplicationContext(), profileInformationsRequested);
        profileInformationListView.setAdapter(adapter);
    }

    private ArrayList<ProfileInformationModel> getProfileData(ArrayList<UserDataModel> dataModels) {

        ArrayList<ProfileInformationModel> profileInformationModels = new ArrayList<>();

        for (UserDataModel userDataModel: dataModels){
            if (userDataModel.getVisible()){
                ProfileInformationModel informationModel = new ProfileInformationModel(userDataModel.getDataTitle(), userDataModel.getDataValue());
                profileInformationModels.add(informationModel);
            }
        }

        return profileInformationModels;
    }

    private void getImage(String photo){
        UserRequest.getImage(photo, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                profileImageBytes = responseBody;
                Bitmap imageBitmap = ImageConvert.getDecode64ImageStringFromByte(responseBody);
                circleImageViewProfile.setImageBitmap(imageBitmap);
                Bitmap blurred = ImageConvert.blurRenderScript(getActivity(),imageBitmap, CONST_IMAGE_BLUR);
                backgroundImageViewProfile.setImageBitmap(blurred);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ratingButton:
                Bundle bundle = new Bundle();

                //Verify my profile
                if (lawyerModel==null){
                    bundle.putString(UserModel.keyName,currentUser.getName());
                    bundle.putString(UserModel.keyCurriculum,currentUser.getCurriculum());
                    bundle.putString(UserModel.keyOab,currentUser.getOab());
                    bundle.putByteArray(UserModel.keyPhoto, profileImageBytes);
                    bundle.putInt("typeProfile",TypeProfile.CLIENT.ordinal());
                }else {
                    bundle.putString(UserModel.keyName,lawyerModel.getName());
                    bundle.putString(UserModel.keyCurriculum,lawyerModel.getMiniCurriculum());
                    bundle.putString(UserModel.keyOab,lawyerModel.getOab());
                    bundle.putByteArray(UserModel.keyPhoto, profileImageBytes);
                    bundle.putInt("typeProfile",TypeProfile.LAWYER.ordinal());
                    bundle.putInt(UserModel.keyID,positionLawyer);
                }

                ActivityManager.changeActivity(getActivity(), DetailEvaluationActivity.class, bundle);
                break;
            //Logout button
            case id_logout_button:
                ApplicationState.sharedState().clearCurrentUser(getActivity().getApplicationContext());
                ActivityManager.changeActivityAndRemoveParentActivity(getActivity(), LoginActivity.class);
                break;
            case id_make_proposal_button:
                Bundle bundleProposal = new Bundle();
                bundleProposal.putInt(LawyerModel.keyIdLawyer,positionLawyer);
                bundleProposal.putByteArray(LawyerModel.keyPhoto,profileImageBytes);
                ActivityManager.changeActivity(getActivity(), LawyerServiceProposalActivity.class, bundleProposal);
                break;
        }
    }

    private void getDataProfile(){
        progressDialog = FeedbackManager.createProgressDialog(getActivity(),getString(R.string.placeholder_message_dialog));

        UserRequest.getProfileData(currentUser.getId(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                if (Requester.haveSuccess(response)){

                    //Get UserDataModels
                    userDataModels = new ArrayList<>();
                    JSONArray arrayUserData = Requester.getJsonArray(response,UserDataModel.keyItensDataModel);
                    for (int i=0; i<arrayUserData.length(); i++){
                        JSONObject jsonObject = Requester.getJsonObject(arrayUserData,i);
                        UserDataModel userDataModel = new UserDataModel(jsonObject);
                        userDataModels.add(userDataModel);
                    }

                    //Set userDataModels
                    ApplicationState.sharedState().setCurrentUserDataModels(userDataModels);

                    //Get UserInformationModel
                    ApplicationState.sharedState().currentUserInformationModel = new UserInformationModel(response);

                    //Update view
                    setPropertiesViews();
                    adjustLayoutListView();

                }else {
                    createToast(response);
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
        FeedbackManager.createToast(getActivity(),response);
    }

    private void createErrorToast(){
        FeedbackManager.feedbackErrorResponse(getActivity(),progressDialog);
    }
}
