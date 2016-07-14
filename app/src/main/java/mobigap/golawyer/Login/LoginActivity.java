package mobigap.golawyer.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import mobigap.golawyer.Register.ChooseProfileActivity;
import mobigap.golawyer.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    ImageButton registerButton;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getInstanceViews();
        setPropertiesViews();
    }

    private void getInstanceViews(){
        registerButton = (ImageButton) findViewById(R.id.registerButton);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    }

    private void setPropertiesViews(){
        registerButton.setOnClickListener(this);
        emailEditText.setOnClickListener(this);
        passwordEditText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                Intent intent = new Intent(LoginActivity.this, ChooseProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.emailEditText:
                emailEditText.setText("");
                break;
            case R.id.passwordEditText:
                passwordEditText.setText("");
                break;
        }
    }
}
