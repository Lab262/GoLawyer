package mobigap.golawyer.LawyerServiceFollowing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import mobigap.golawyer.R;


public class LawyerServiceStatusRateFragment extends ScrollView {


    private TextView serviceStatusRateTitle, seviceStatusDeliveryDescriptionTextView;

    private EditText serviceStatusRateCommentEditText;

    private ImageButton confirmButton;

    private RatingBar ratingBar;

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
        this.ratingBar = (RatingBar) findViewById(R.id.ratingBar);

    }

    public void buildButtons(){
        this.confirmButton = (ImageButton)findViewById(R.id.rateConfirmButton);
        this.confirmButton.setOnClickListener(confirmClickListener);

    }

    public void setupTextsFields(String seviceStatusDeliveryDescriptionTextView, Boolean isEvaluate){
        if (!isEvaluate){
            this.seviceStatusDeliveryDescriptionTextView.setText(seviceStatusDeliveryDescriptionTextView);
        }else {
            this.ratingBar.setVisibility(GONE);
            this.serviceStatusRateCommentEditText.setVisibility(GONE);
            this.seviceStatusDeliveryDescriptionTextView.setVisibility(GONE);
            this.confirmButton.setVisibility(GONE);
            this.serviceStatusRateTitle.setText(seviceStatusDeliveryDescriptionTextView);
        }


    }

}
