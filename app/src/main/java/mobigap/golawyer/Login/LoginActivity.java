package mobigap.golawyer.Login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import mobigap.golawyer.BottomBarActivity;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Register.ChooseProfileActivity;
import mobigap.golawyer.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    ImageButton registerButton, loginButton;
    EditText emailEditText, passwordEditText;

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
}

