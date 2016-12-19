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
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

/**
 * Created by luisresende on 19/12/16.
 */

public class LawyerServiceStatusPaymentLawyerFragment extends ScrollView implements View.OnClickListener {

    private ImageButton cancelButton;
    private ProgressDialog progressDialog;
    TextView feedbackTextView;


    public LawyerServiceStatusPaymentLawyerFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawyerServiceStatusPaymentLawyerFragment(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        buildViews();
        buildButtons();
    }

    private void buildViews() {
        this.feedbackTextView = (TextView) findViewById(R.id.feedbackTextView);

    }

    private void buildButtons(){
        this.cancelButton = (ImageButton)findViewById(R.id.paymentCancelButton);
        this.cancelButton.setOnClickListener(this);
    }

    public void setUpViews(String feedbackText){
        feedbackTextView.setText(feedbackText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.paymentCancelButton:
                cancelDemandOrder();
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
}