package mobigap.golawyer.LawyerServiceFollowing;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;


public class LawyerServiceStatusDeliveryFragment extends ScrollView implements View.OnClickListener {


    private TextView seviceStatusDeliveryDescriptionTextView, seviceStatusDeliveryDateTextView, seviceStatusDeliveryTitleTextView;

    private ImageButton confirmButton, cancelButton;
    private ProgressDialog progressDialog;
    private Boolean isCharge = false;

    public LawyerServiceStatusDeliveryFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawyerServiceStatusDeliveryFragment(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        buildViews();
        buildButtons();
    }

    public void buildViews() {
        this.seviceStatusDeliveryDescriptionTextView = (TextView) findViewById(R.id.seviceStatusDeliveryDescriptionTextView);
        this.seviceStatusDeliveryDateTextView = (TextView) findViewById(R.id.seviceStatusDeliveryDateTextView);
        this.seviceStatusDeliveryTitleTextView = (TextView) findViewById(R.id.seviceStatusDeliveryTitleTextView);

    }

    public void buildButtons(){
        this.confirmButton = (ImageButton)findViewById(R.id.deliveryConfirmButton);
        this.confirmButton.setOnClickListener(this);
        this.cancelButton = (ImageButton)findViewById(R.id.deliveryCancelButton);
        this.cancelButton.setOnClickListener(this);
    }

    public void setupTextsFields(String deliveryStatusTitle, String deliveryStatusDate, String deliveryStatusDescription, TypeProfile typeProfile, Boolean isCharge){
        this.seviceStatusDeliveryDescriptionTextView.setText(deliveryStatusDescription);
        this.seviceStatusDeliveryDateTextView.setText(deliveryStatusDate);
        this.seviceStatusDeliveryTitleTextView.setText(deliveryStatusTitle);
        this.isCharge = isCharge;
        if (typeProfile==TypeProfile.LAWYER){
            this.confirmButton.setVisibility(GONE);
            this.cancelButton.setImageResource(R.drawable.button_cancel);
        }else {
            if (this.isCharge){
                this.cancelButton.setImageResource(R.drawable.button_cancel);
            }
        }
    }

    public void setupTextsFields(String textCharge){
        this.seviceStatusDeliveryDescriptionTextView.setText(textCharge);
        this.seviceStatusDeliveryDateTextView.setVisibility(GONE);
        this.seviceStatusDeliveryTitleTextView.setVisibility(GONE);
        this.confirmButton.setVisibility(GONE);
        this.cancelButton.setImageResource(R.drawable.button_cancel);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.deliveryConfirmButton:
                finalizeDemandChatOrder();
                break;
            case R.id.deliveryCancelButton:
                if (isCharge){
                    cancelDemandOrder();
                }else {
                    chargeDemandOrder();
                }
                break;
        }
    }

    private void cancelDemandOrder(){
        progressDialog = FeedbackManager.createProgressDialog(getContext(),getResources().getString(R.string.placeholder_message_dialog));

        UserRequest.setCancelDemandOrder(getContext(), ApplicationState.sharedState().getDemandModel().getIdOrder(),
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

    private void chargeDemandOrder(){
        progressDialog = FeedbackManager.createProgressDialog(getContext(),getResources().getString(R.string.placeholder_message_dialog));

        UserRequest.setChargeDemandOrder(getContext(), ApplicationState.sharedState().getDemandModel().getIdOrder(),
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

    private void finalizeDemandChatOrder(){
        progressDialog = FeedbackManager.createProgressDialog(getContext(),getResources().getString(R.string.placeholder_message_dialog));

        UserRequest.setFinalizeDemandOrder(getContext(), ApplicationState.sharedState().getDemandModel().getIdOrder(),
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
}
