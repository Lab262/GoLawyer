package mobigap.golawyer.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import mobigap.golawyer.BottomBarActivity;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Model.User;
import mobigap.golawyer.Model.UserModelRequest;
import mobigap.golawyer.Persistence.JsonSpiceService;
import mobigap.golawyer.Persistence.UserLoginRequester;
import mobigap.golawyer.Register.ChooseProfileActivity;
import mobigap.golawyer.R;

public class LoginActivity extends Activity implements View.OnClickListener, RequestListener<UserModelRequest> {

    ImageButton registerButton, loginButton;
    EditText emailEditText, passwordEditText;
    private SpiceManager spiceManagerJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spiceManagerJson =  new SpiceManager(JsonSpiceService.class);
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
                //requestLogin();
                ActivityManager.changeActivityAndRemoveParentActivity(LoginActivity.this, BottomBarActivity.class);
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

    private void requestLogin() {
        if (!spiceManagerJson.isStarted()) {
            spiceManagerJson.start(this.getApplicationContext());
        }
        spiceManagerJson.execute(new UserLoginRequester("luisok@gmail.com", "12345"), "userLogin", DurationInMillis.ALWAYS_EXPIRED, this);

    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {

    }

    @Override
    public void onRequestSuccess(UserModelRequest user) {

    }
}

