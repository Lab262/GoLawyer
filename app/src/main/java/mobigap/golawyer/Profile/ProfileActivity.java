package mobigap.golawyer.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import mobigap.golawyer.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    View headerCellphone, headerEmail, headerDetailUser;
    TextView nameHeaderCellphone, nameHeaderEmail, nameHeaderDetailUser;
    ExpandableRelativeLayout expandableDetailsUser;
    TextView curriculumText;

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
        headerDetailUser = profileInformationHeader.findViewById(R.id.headerDetailUser);
        nameHeaderCellphone = (TextView) headerCellphone.findViewById(R.id.nameHeaderProfile);
        nameHeaderEmail = (TextView) headerEmail.findViewById(R.id.nameHeaderProfile);
        nameHeaderDetailUser = (TextView) headerDetailUser.findViewById(R.id.nameHeaderProfile);
        expandableDetailsUser = (ExpandableRelativeLayout) findViewById(R.id.curriculum_information);
        curriculumText = (TextView) expandableDetailsUser.findViewById(R.id.curriculumText);
    }

    private void setPropertiesViews(){
        ImageView arrowCellphone = (ImageView) headerCellphone.findViewById(R.id.headerArrowProfile);
        ImageView arrowEmail = (ImageView) headerEmail.findViewById(R.id.headerArrowProfile);
        arrowCellphone.setVisibility(View.INVISIBLE);
        arrowEmail.setVisibility(View.INVISIBLE);
        nameHeaderCellphone.setText("(99)99999-7897");
        nameHeaderEmail.setText("email@email.com");
        nameHeaderDetailUser.setText(R.string.placeholder_details_user);
        headerDetailUser.setOnClickListener(this);
        curriculumText.setText("Meu curriculo vem aqui.");
    }

    private void adjustLayout() {
        View registerProfilePhoto = findViewById(R.id.registerProfilePhoto);
        ImageButton cameraButton = (ImageButton) registerProfilePhoto.findViewById(R.id.cameraButton);
        cameraButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerDetailUser:
                toggleExpandableLayout(expandableDetailsUser, headerDetailUser);
                break;
        }
    }

    private void toggleExpandableLayout(ExpandableRelativeLayout expandableRelativeLayout, View headerView){
        expandableRelativeLayout.toggle();
//        ImageView arrow = (ImageView) headerView.findViewById(R.id.headerArrowProfile);
//        if (expandableRelativeLayout.isExpanded()){
//            arrow.setImageResource(R.drawable.forward_mini_arrow);
//        }else {
//            arrow.setImageResource(R.drawable.down_mini_arrow);
//        }
    }
}
