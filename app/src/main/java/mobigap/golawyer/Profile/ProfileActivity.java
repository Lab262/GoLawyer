package mobigap.golawyer.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import mobigap.golawyer.R;

public class ProfileActivity extends AppCompatActivity {

    View headerCellphone, headerEmail;
    TextView nameHeaderCellphone, nameHeaderEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getInstanceViews();
        setPropertiesViews();
        adjustLayout();
    }

    private void getInstanceViews(){
        View profileInformationHeader = findViewById(R.id.profileInformationHeader);
        headerCellphone = profileInformationHeader.findViewById(R.id.headerCellphone);
        headerEmail = profileInformationHeader.findViewById(R.id.headerEmail);
        nameHeaderCellphone = (TextView) headerCellphone.findViewById(R.id.nameHeaderProfile);
        nameHeaderEmail = (TextView) headerEmail.findViewById(R.id.nameHeaderProfile);
    }

    private void setPropertiesViews(){
        ImageView arrowCellphone = (ImageView) headerCellphone.findViewById(R.id.headerArrowProfile);
        ImageView arrowEmail = (ImageView) headerEmail.findViewById(R.id.headerArrowProfile);
        arrowCellphone.setVisibility(View.INVISIBLE);
        arrowEmail.setVisibility(View.INVISIBLE);
        nameHeaderCellphone.setText("(99)99999-7897");
        nameHeaderEmail.setText("email@email.com");
    }

    private void adjustLayout() {
        View registerProfilePhoto = findViewById(R.id.registerProfilePhoto);
        ImageButton cameraButton = (ImageButton) registerProfilePhoto.findViewById(R.id.cameraButton);
        cameraButton.setVisibility(View.INVISIBLE);
    }
}
