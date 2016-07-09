package mobigap.golawyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import mobigap.golawyer.Login.LoginActivity;

public class LaunchScreenActivity extends Activity {

    private int secondsDelayLaunch = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(LaunchScreenActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();

            }
        }, secondsDelayLaunch);
    }
}
