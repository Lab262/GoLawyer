package mobigap.golawyer.LawyerServiceFollowing;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;


public class LawyerServiceStatusPaymentFragment extends ScrollView implements View.OnClickListener {


    private EditText creditCardName, creditCardNumber, creditCardExpireDate, creditCardCVV, creditCardCellphoneNumber;
    private Spinner spinnerCreditCardFlag;
    private ImageButton confirmButton, cancelButton;
    private ProgressDialog progressDialog;


    public LawyerServiceStatusPaymentFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawyerServiceStatusPaymentFragment(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        buildViews();
        buildButtons();
    }

    public void buildViews() {
        this.creditCardName = (EditText) findViewById(R.id.editTextCreditCardName);
        this.creditCardNumber = (EditText)findViewById(R.id.editTextCreditCardNumber);
        this.creditCardExpireDate = (EditText)findViewById(R.id.editTextCreditCardExpireDate);
        this.creditCardCVV = (EditText)findViewById(R.id.editTextCreditCardCVV);
        this.creditCardCellphoneNumber = (EditText)findViewById(R.id.editTextCreditCardCellPhoneNumber);
        this.spinnerCreditCardFlag = (Spinner) findViewById(R.id.spinnerCreditCardFlag);
    }

    public void buildButtons(){
        this.confirmButton = (ImageButton)findViewById(R.id.paymentConfirmButton);
        this.confirmButton.setOnClickListener(this);
        this.cancelButton = (ImageButton)findViewById(R.id.paymentCancelButton);
        this.cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.paymentConfirmButton:
                setDemandOrder();
                break;
            case R.id.paymentCancelButton:
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

    private void setDemandOrder(){
        progressDialog = FeedbackManager.createProgressDialog(getContext(),getResources().getString(R.string.placeholder_message_dialog));

        UserRequest.setDemandStepPaymentOrder(ApplicationState.sharedState().getDemandModel().getIdUser(), ApplicationState.sharedState().getDemandModel().getIdOrder(),
                spinnerCreditCardFlag.getSelectedItem().toString(),creditCardName.getText().toString(),creditCardNumber.getText().toString(),
                creditCardExpireDate.getText().toString(),creditCardCVV.getText().toString(),creditCardCellphoneNumber.getText().toString(),
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
