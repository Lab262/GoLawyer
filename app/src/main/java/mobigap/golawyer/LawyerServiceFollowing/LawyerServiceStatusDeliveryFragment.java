package mobigap.golawyer.LawyerServiceFollowing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.R;


public class LawyerServiceStatusDeliveryFragment extends ScrollView {


    private TextView seviceStatusDeliveryDescriptionTextView, seviceStatusDeliveryDateTextView, seviceStatusDeliveryTitleTextView;

    private ImageButton confirmButton, cancelButton;

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


    public LawyerServiceStatusDeliveryFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawyerServiceStatusDeliveryFragment(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        buildViews();
        buildButtons();
    }

    public void buildViews() {
        this.seviceStatusDeliveryDescriptionTextView = (TextView) findViewById(R.id.seviceStatusDeliveryDescriptionTextView);
        this.seviceStatusDeliveryDateTextView = (TextView) findViewById(R.id.seviceStatusDeliveryDateTextView);
        this.seviceStatusDeliveryTitleTextView = (TextView) findViewById(R.id.seviceStatusDeliveryTitleTextView);

    }

    public void buildButtons(){
        this.confirmButton = (ImageButton)findViewById(R.id.deliveryConfirmButton);
        this.confirmButton.setOnClickListener(confirmClickListener);
        this.cancelButton = (ImageButton)findViewById(R.id.deliveryCancelButton);
        this.cancelButton.setOnClickListener(cancelClickListener);
    }

    public void setupTextsFields(String deliveryStatusTitle, String deliveryStatusDate, String deliveryStatusDescription, TypeProfile typeProfile, Boolean isCharge){
        this.seviceStatusDeliveryDescriptionTextView.setText(deliveryStatusDescription);
        this.seviceStatusDeliveryDateTextView.setText(deliveryStatusDate);
        this.seviceStatusDeliveryTitleTextView.setText(deliveryStatusTitle);
        if (typeProfile==TypeProfile.LAWYER){
            this.confirmButton.setVisibility(GONE);
            this.cancelButton.setImageResource(R.drawable.button_cancel);
        }else {
            if (isCharge){
                this.cancelButton.setImageResource(R.drawable.button_cancel);
            }
        }
    }

    public void setupTextsFields(String textCharge){
        this.seviceStatusDeliveryDescriptionTextView.setText(textCharge);
        this.seviceStatusDeliveryDateTextView.setVisibility(GONE);
        this.seviceStatusDeliveryTitleTextView.setVisibility(GONE);
        this.confirmButton.setVisibility(GONE);
        this.cancelButton.setImageResource(R.drawable.button_cancel);
    }

}
