package mobigap.golawyer.LawyerServiceFollowing;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.LawyerServiceProposal.LawyerServiceProposalActivity;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.LawyerRequest;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;


public class LawyerServiceStatusRateFragment extends ScrollView implements View.OnClickListener {


    private TextView serviceStatusRateTitle, seviceStatusDeliveryDescriptionTextView;

    private EditText serviceStatusRateCommentEditText;

    private ImageButton confirmButton;

    private RatingBar ratingBar;

    private ProgressDialog progressDialog;

    private Boolean isClient = false;

    private byte[] profileImageBytes;

    public LawyerServiceStatusRateFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawyerServiceStatusRateFragment(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        buildViews();
        buildButtons();
    }

    public void buildViews() {
        this.serviceStatusRateTitle = (TextView) findViewById(R.id.serviceStatusRateTitle);
        this.serviceStatusRateCommentEditText = (EditText) findViewById(R.id.serviceStatusRateCommentEditText);
        this.seviceStatusDeliveryDescriptionTextView = (TextView) findViewById(R.id.seviceStatusDeliveryDescriptionTextView);
        this.ratingBar = (RatingBar) findViewById(R.id.ratingBar);

    }

    public void buildButtons(){
        this.confirmButton = (ImageButton)findViewById(R.id.rateConfirmButton);
        this.confirmButton.setOnClickListener(this);

    }

    public void setupTextsFields(String seviceStatusDeliveryDescriptionTextView, Boolean isEvaluate){
        if (!isEvaluate){
            this.seviceStatusDeliveryDescriptionTextView.setText(seviceStatusDeliveryDescriptionTextView);
        }else {
            this.ratingBar.setVisibility(GONE);
            this.serviceStatusRateCommentEditText.setVisibility(GONE);
            this.seviceStatusDeliveryDescriptionTextView.setVisibility(GONE);
            this.confirmButton.setVisibility(GONE);
            this.serviceStatusRateTitle.setText(seviceStatusDeliveryDescriptionTextView);
        }
    }

    public void setupTextsFields(String seviceStatusDeliveryDescriptionTextView, TypeProfile typeProfile){
        this.ratingBar.setVisibility(GONE);
        this.serviceStatusRateCommentEditText.setVisibility(GONE);
        this.seviceStatusDeliveryDescriptionTextView.setVisibility(GONE);
        this.serviceStatusRateTitle.setText(seviceStatusDeliveryDescriptionTextView);
        this.seviceStatusDeliveryDescriptionTextView.setText(seviceStatusDeliveryDescriptionTextView);
        if (typeProfile==TypeProfile.CLIENT){
            this.isClient = true;
            this.confirmButton.setImageResource(R.drawable.button_new_demand);
        }else {
            this.confirmButton.setVisibility(GONE);
        }
    }

    public void setUpProfileImageBytes(byte[] profileImageBytes){
        this.profileImageBytes = profileImageBytes;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rateConfirmButton:
                if (isClient){
                    getLawyer();
                }else {
                    evaluationDemandOrder();
                }
                break;
        }

    }

    private void evaluationDemandOrder(){
        progressDialog = FeedbackManager.createProgressDialog(getContext(),getResources().getString(R.string.placeholder_message_dialog));

        UserRequest.setEvaluationDemandOrder(getContext(), ApplicationState.sharedState().getDemandModel().getIdOrder(),
                String.valueOf(ratingBar.getNumStars()), serviceStatusRateCommentEditText.getText().toString(),
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

    private void getLawyer() {
        progressDialog = FeedbackManager.createProgressDialog(getContext(), getContext().getString(R.string.placeholder_message_dialog));

        LawyerRequest.getLawyer(ApplicationState.sharedState().getDemandModel().getIdLawyer(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                ApplicationState.sharedState().setLawyersRequestModels(null);
                if (Requester.haveSuccess(response)) {
                    //Get ServiceRequestModel
                    JSONArray arrayLawyersRequestModel = Requester.getJsonArray(response, LawyerModel.keyItensDataModel);
                    JSONObject jsonObject = Requester.getJsonObject(arrayLawyersRequestModel, 0);
                    LawyerModel lawyerRequestModel = new LawyerModel(jsonObject);
                    lawyerRequestModel.setImageBytes(getProfileImageBytes());
                    ApplicationState.sharedState().setLawyerModel(lawyerRequestModel);
                    ActivityManager.changeActivity(getContext(), LawyerServiceProposalActivity.class);

                } else {
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

    public byte[] getProfileImageBytes() {
        return profileImageBytes;
    }
}
