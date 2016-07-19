package mobigap.golawyer.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.R;

public class ChooseProfileActivity extends Activity implements View.OnClickListener {

    RadioButton clientRadioButton;
    RadioButton lawyerRadioButton;
    ImageButton forwardButton;
    TypeProfile typeProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profile);
        getInstanceViews();
        setPropertiesViews();
    }

    private void getInstanceViews(){
        clientRadioButton = (RadioButton) findViewById(R.id.clientRadioButton);
        lawyerRadioButton = (RadioButton) findViewById(R.id.lawyerRadioButton);
        forwardButton = (ImageButton) findViewById(R.id.forwardButton);
    }

    private void setPropertiesViews(){
        clientRadioButton.setOnClickListener(this);
        lawyerRadioButton.setOnClickListener(this);
        forwardButton.setOnClickListener(this);
    }

    private void unselectedRadioButtons(){
        switch (typeProfile){
            case CLIENT:
                lawyerRadioButton.setChecked(false);
                break;
            case LAWYER:
                clientRadioButton.setChecked(false);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.clientRadioButton:
                typeProfile = TypeProfile.CLIENT;
                unselectedRadioButtons();
                break;
            case R.id.lawyerRadioButton:
                typeProfile = TypeProfile.LAWYER;
                unselectedRadioButtons();
                break;
            case R.id.forwardButton:
                Intent intent = new Intent(ChooseProfileActivity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("typeProfile",typeProfile.ordinal());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
