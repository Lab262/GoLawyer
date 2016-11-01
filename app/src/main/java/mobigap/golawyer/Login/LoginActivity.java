package mobigap.golawyer.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class LoginActivity extends Activity implements View.OnClickListener {

    ImageButton registerButton, loginButton;
    EditText emailEditText, passwordEditText;
    private ProgressDialog progressDialog;

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
    }

    private void setPropertiesViews() {
        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        emailEditText.setOnClickListener(this);
        passwordEditText.setOnClickListener(this);
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

    private void login(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_message_dialog));
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        UserRequest.login(email,password, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (Requester.haveSuccess(response)){
                    progressDialog.dismiss();
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

    private void createToast(JSONObject response){
        FeedbackManager.createToast(this,response);
    }

    private void createErrorToast(){
        FeedbackManager.feedbackErrorResponse(this,progressDialog);
    }
}

