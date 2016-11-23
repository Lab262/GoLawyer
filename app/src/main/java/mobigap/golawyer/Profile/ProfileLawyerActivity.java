package mobigap.golawyer.Profile;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Protocols.OnFragmentInteractionListener;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;

public class ProfileLawyerActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_lawyer);

        Bundle bundleExtras = getIntent().getExtras();
        if (bundleExtras!=null){
            int position = bundleExtras.getInt(Requester.keyMessage);
            ArrayList<LawyerModel> lawyerModelArrayList = ApplicationState.sharedState().getLawyersRequestModels();
            LawyerModel lawyerModel = lawyerModelArrayList.get(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance(lawyerModel, position))
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack("")
                .commit();
    }
}
