package mobigap.golawyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Login.LoginActivity;
import mobigap.golawyer.Persistence.ApplicationState;

public class LaunchScreenActivity extends Activity {

    private int secondsDelayLaunch = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (ApplicationState.sharedState().getCurrentUser(getApplicationContext())==null){
                    ActivityManager.changeActivityAndRemoveParentActivity(LaunchScreenActivity.this, LoginActivity.class);
                }else {
                    ActivityManager.changeActivityAndRemoveParentActivity(LaunchScreenActivity.this, BottomBarActivity.class);
                }
                finish();

            }
        }, secondsDelayLaunch);
    }
}
