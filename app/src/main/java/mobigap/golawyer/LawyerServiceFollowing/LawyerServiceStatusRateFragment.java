package mobigap.golawyer.LawyerServiceFollowing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import mobigap.golawyer.R;


public class LawyerServiceStatusRateFragment extends ScrollView {


    private TextView serviceStatusRateTitle, seviceStatusDeliveryDescriptionTextView;

    private EditText serviceStatusRateCommentEditText;

    private ImageButton confirmButton;

    private OnClickListener confirmClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private OnClickListener cancelClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    public LawyerServiceStatusRateFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawyerServiceStatusRateFragment(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        buildViews();
        buildButtons();
    }

    public void buildViews() {
        this.serviceStatusRateTitle = (TextView) findViewById(R.id.serviceStatusRateTitle);
        this.serviceStatusRateCommentEditText = (EditText) findViewById(R.id.serviceStatusRateCommentEditText);
        this.seviceStatusDeliveryDescriptionTextView = (TextView) findViewById(R.id.seviceStatusDeliveryDescriptionTextView);

    }

    public void buildButtons(){
        this.confirmButton = (ImageButton)findViewById(R.id.rateConfirmButton);
        this.confirmButton.setOnClickListener(confirmClickListener);

    }

    public void setupTextsFields(String serviceStatusRateTitle, String serviceStatusRateCommentEditText, String seviceStatusDeliveryDescriptionTextView){
        this.serviceStatusRateTitle.setText(serviceStatusRateTitle);
        this.serviceStatusRateCommentEditText.setText(serviceStatusRateCommentEditText);
        this.seviceStatusDeliveryDescriptionTextView.setText(seviceStatusDeliveryDescriptionTextView);

    }

}
