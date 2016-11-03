package mobigap.golawyer.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.BottomBarActivity;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Model.UserModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Register.ChooseProfileActivity;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

public class LoginActivity extends Activity implements View.OnClickListener, DialogInterface.OnClickListener {

    ImageButton registerButton, loginButton;
    EditText emailEditText, passwordEditText;
    private EditText emailForgotPasswordEditText;
    private ProgressDialog progressDialog;
    private Button forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getInstanceViews();
        setPropertiesViews();
    }

    private void getInstanceViews() {
        registerButton = (ImageButton) findViewById(R.id.registerButton);
        loginButton = (ImageButton) findViewById(R.id.loginButton);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        forgotPasswordButton = (Button) findViewById(R.id.forgotPasswordButton);
    }

    private void setPropertiesViews() {
        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        emailEditText.setOnClickListener(this);
        passwordEditText.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                ActivityManager.changeActivity(LoginActivity.this, ChooseProfileActivity.class);
                break;
            case R.id.loginButton:
                login();
                break;
            case R.id.forgotPasswordButton:
                createAlertDialog();
                break;
            case R.id.emailEditText:
                emailEditText.setText("");
                emailEditText.setOnClickListener(null);
                break;
            case R.id.passwordEditText:
                passwordEditText.setText("");
                passwordEditText.setOnClickListener(null);
                break;
        }
    }

    private void createToast(JSONObject response){
        FeedbackManager.createToast(this,response);
    }

    private void createErrorToast(){
        FeedbackManager.feedbackErrorResponse(this,progressDialog);
    }

    private void login(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_message_dialog));
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        UserRequest.login(email,password, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                if (Requester.haveSuccess(response)){
                    UserModel userModel = new UserModel(response);
                    ApplicationState.sharedState().currentUser = userModel;
                    ActivityManager.changeActivityAndRemoveParentActivity(LoginActivity.this, BottomBarActivity.class);
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

    private void createAlertDialog(){

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //Create custom edit text
        emailForgotPasswordEditText = new EditText(this);
        emailForgotPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailForgotPasswordEditText.setHint(getString(R.string.placeholder_alert_forgot_hint));
        emailForgotPasswordEditText.setLayoutParams(layoutParams);

        //Create custom title
        TextView textView = new TextView(this);
        textView.setText(getString(R.string.title_alert_forgot_password));
        textView.setLayoutParams(layoutParams);

        //Create Alert
        AlertDialog.Builder forgotAlert = new AlertDialog.Builder(this);
        forgotAlert.setCustomTitle(textView);
        forgotAlert.setView(emailForgotPasswordEditText);
        forgotAlert.setPositiveButton(getString(R.string.placeholder_alert_forgot_positive_button), this);
        forgotAlert.setNegativeButton(getString(R.string.placeholder_alert_forgot_negative_button), null);
        forgotAlert.show();
    }

    private void forgotPassword(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_message_dialog));
        String email = emailForgotPasswordEditText.getText().toString();
        UserRequest.forgotPassword(email, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                createToast(response);
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

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        forgotPassword();
    }
}

