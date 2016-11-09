package mobigap.golawyer.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.BottomBarActivity;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Login.LoginActivity;
import mobigap.golawyer.Model.CommentModel;
import mobigap.golawyer.Model.EvaluationModel;
import mobigap.golawyer.Model.ProfileInformationModel;
import mobigap.golawyer.Model.UserDataModel;
import mobigap.golawyer.Model.UserInformationModel;
import mobigap.golawyer.Model.UserModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Profile.Information.ProfileInformationListAdapter;
import mobigap.golawyer.Protocols.OnFragmentInteractionListener;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_profile, container, false);
        defaultInflater = inflater;
        Bundle bundleExtras = getActivity().getIntent().getExtras();
        //Case if your profile
        if (bundleExtras==null){
            currentUser = ApplicationState.sharedState().getCurrentUser(getActivity().getApplicationContext());
        }else {

        }

        getInstanceViews();
        adjustLayout();
        getDataProfile();

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

    private void adjustLayoutListView(LayoutInflater inflater) {
        profileInformationListView = (ListView) this.view.findViewById(R.id.profileInformationListView);
        loadRequestedProfileInformationList(getProfileData());
        profileInformationListView.addHeaderView(header);
        profileInformationListView.addFooterView(createLogoutButton());
    }

    private ImageButton createLogoutButton(){

        ImageButton logoutButton = new ImageButton(getActivity());
        logoutButton.setId(0);
        logoutButton.setBackgroundColor(Color.WHITE);
        logoutButton.setImageResource(R.drawable.button_logout);
        logoutButton.setOnClickListener(this);
        logoutButton.setPadding(0,10,0,10);
        return logoutButton;
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
        //TODO: Colocar a imagem real
        nameTextView.setText(currentUser.getName());
        hasOABTextView.setText(currentUser.getOab());
        numberAttendanceTextView.setText(String.valueOf(ApplicationState.sharedState().currentUserInformationModel.getTotalOrders()));
        numberCompletedTextView.setText(String.valueOf(ApplicationState.sharedState().currentUserInformationModel.getTotalConcludedOrders()));
        EvaluationModel evaluationModel = ApplicationState.sharedState().currentUserInformationModel.getEvaluation();
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

    private ArrayList<ProfileInformationModel> getProfileData() {

        ArrayList<ProfileInformationModel> profileInformationModels = new ArrayList<>();

        for (UserDataModel userDataModel: userDataModels){
            if (userDataModel.getVisible()){
                ProfileInformationModel informationModel = new ProfileInformationModel(userDataModel.getDataTitle(), userDataModel.getDataValue());
                profileInformationModels.add(informationModel);
            }
        }

        return profileInformationModels;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ratingButton:
                Bundle bundle = new Bundle();
                bundle.putString(UserModel.keyName,currentUser.getName());
                bundle.putString(UserModel.keyCurriculum,currentUser.getCurriculum());
                bundle.putString(UserModel.keyOab,currentUser.getOab());
                ActivityManager.changeActivity(getActivity(), DetailEvaluationActivity.class, bundle);
                break;
            //Logout button
            case 0:
                ApplicationState.sharedState().clearCurrentUser(getActivity().getApplicationContext());
                ActivityManager.changeActivityAndRemoveParentActivity(getActivity(), LoginActivity.class);
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

                    //Get UserInformationModel
                    ApplicationState.sharedState().currentUserInformationModel = new UserInformationModel(response);

                    //Update view
                    setPropertiesViews();
                    adjustLayoutListView(defaultInflater);

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
