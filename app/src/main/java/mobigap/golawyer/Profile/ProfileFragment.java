package mobigap.golawyer.Profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import mobigap.golawyer.BottomBarActivity;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Model.ProfileInformationModel;
import mobigap.golawyer.Profile.Information.ProfileInformationListAdapter;
import mobigap.golawyer.Protocols.OnFragmentInteractionListener;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import mobigap.golawyer.R;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View view;
    private ScrollView scrollView;
    private ImageButton ratingButton;
    private ListView profileInformationListView;

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
        getInstanceViews();
        setPropertiesViews();
        adjustLayout();
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

    private void getInstanceViews(){
        scrollView = (ScrollView) this.view.findViewById(R.id.scrollView);

        View profileHeader = this.view.findViewById(R.id.profileHeader);
        ratingButton = (ImageButton)profileHeader.findViewById(R.id.ratingButton);

        View profileInformation = this.view.findViewById(R.id.profileInformation);
        profileInformationListView = (ListView) profileInformation.findViewById(R.id.profileInformationListView);


    }

    private void setPropertiesViews(){
        ratingButton.setOnClickListener(this);
        loadRequestedProfileInformationList(getDummyData());

    }

    private void adjustLayout() {
        View registerProfilePhoto = this.view.findViewById(R.id.registerProfilePhoto);
        ImageButton cameraButton = (ImageButton) registerProfilePhoto.findViewById(R.id.cameraButton);
        cameraButton.setVisibility(View.INVISIBLE);
    }

    private void loadRequestedProfileInformationList(ProfileInformationModel[] profileInformationsRequested) {
        ProfileInformationListAdapter adapter = new ProfileInformationListAdapter(getActivity().getApplicationContext(), profileInformationsRequested);
        profileInformationListView.setAdapter(adapter);
    }

    private ProfileInformationModel[] getDummyData() {

        ProfileInformationModel informationModel1 = new ProfileInformationModel("Nome:", "Huallyd");
        ProfileInformationModel informationModel2 = new ProfileInformationModel("Email:", "huallyd@gmail.com");


        ProfileInformationModel[] dummyData = new ProfileInformationModel[2];
        dummyData[0] = informationModel1;
        dummyData[1] = informationModel2;

        return dummyData;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ratingButton:
                ActivityManager.changeActivity(getActivity(), DetailEvaluationActivity.class);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
