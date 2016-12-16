package mobigap.golawyer.LawyerServiceStatusDemand;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusActivity;
import mobigap.golawyer.Model.DemandModel;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

/**
 * Created by thiagoMB on 8/3/16.
 */
public class LawyerServiceStatusDemandDetailFragment extends ScrollView implements View.OnClickListener {


    private ListView demandListView;

    private ImageButton acceptButton, refuseButton, againstProposalButton, cancelButton;
    private LinearLayout buttonsLawyer, buttonsClient;
    private ProgressDialog progressDialog;

    public LawyerServiceStatusDemandDetailFragment(Context context){
        super(context);

    }

    public LawyerServiceStatusDemandDetailFragment(android.content.Context context, android.util.AttributeSet attrs){
        super(context,attrs);

    }

    public LawyerServiceStatusDemandDetailFragment(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        this.buildViews();
        this.buildButtons();
    }

    public void buildViews() {
        this.demandListView = (ListView) findViewById(R.id.demandListView);
    }

    public void buildButtons(){
        this.buttonsLawyer = (LinearLayout)findViewById(R.id.buttonsLawyer);

        this.acceptButton = (ImageButton)this.buttonsLawyer.findViewById(R.id.acceptButton);
        this.acceptButton.setOnClickListener(this);
        this.refuseButton = (ImageButton)this.buttonsLawyer.findViewById(R.id.refuseButton);
        this.refuseButton.setOnClickListener(this);
        this.againstProposalButton = (ImageButton)this.buttonsLawyer.findViewById(R.id.againstProposalButton);
        this.againstProposalButton.setOnClickListener(this);

        this.buttonsClient = (LinearLayout)findViewById(R.id.buttonsClient);

        this.cancelButton = (ImageButton)this.buttonsClient.findViewById(R.id.cancelProposalButton);
        this.cancelButton.setOnClickListener(this);
    }

    private void adjustLayoutButtons(TypeProfile typeProfile){
        if (typeProfile==TypeProfile.CLIENT){
            this.buttonsLawyer.setVisibility(GONE);
        }else {
            this.buttonsClient.setVisibility(GONE);
        }

    }

    private void adjustListView(){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) demandListView.getLayoutParams();
        layoutParams.height = 60*demandListView.getAdapter().getCount();
        demandListView.setLayoutParams(layoutParams);
    }

    public void setupView(HashMap<Integer,Pair<String,String>> informationDemand, TypeProfile typeProfile){
        adjustLayoutButtons(typeProfile);
        DemandListAdapter adapter = new DemandListAdapter(getContext(),informationDemand);
        demandListView.setAdapter(adapter);
        demandListView.setEnabled(false);
        adjustListView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.acceptButton:
                break;
            case R.id.refuseButton:
                break;
            case R.id.againstProposalButton:
                break;
            case R.id.cancelProposalButton:
                cancelDemandOrder();
                break;
        }

    }

    private void cancelDemandOrder(){
        progressDialog = FeedbackManager.createProgressDialog(getContext(),getResources().getString(R.string.placeholder_message_dialog));

        UserRequest.setCancelDemandOrder(ApplicationState.sharedState().getDemandModel().getIdUser(), ApplicationState.sharedState().getDemandModel().getIdOrder(),
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();
                        createToast(response);
                        if (Requester.haveSuccess(response)){
                            ((LawyerServiceStatusActivity) getContext()).finish();
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
        FeedbackManager.createToast(getContext(),response);
    }

    private void createErrorToast(){
        FeedbackManager.feedbackErrorResponse(getContext(),progressDialog);
    }
}
