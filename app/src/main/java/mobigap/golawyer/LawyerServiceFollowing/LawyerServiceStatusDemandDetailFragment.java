package mobigap.golawyer.LawyerServiceFollowing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobigap.golawyer.R;

/**
 * Created by thiagoMB on 8/3/16.
 */
public class LawyerServiceStatusDemandDetailFragment extends LinearLayout {


    private TextView forumTextView, serviceTypeTextView, serviceValueTextView, serviceDeliveryTypeTextView, serviceTotalValueTextView;

    private ImageButton acceptButton, refuseButton, againstProposalButton;

    private OnClickListener acceptClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private OnClickListener refuseClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private OnClickListener againstProposalClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public LawyerServiceStatusDemandDetailFragment(Context context){
        super(context);

    }

    public LawyerServiceStatusDemandDetailFragment(android.content.Context context, android.util.AttributeSet attrs){
        super(context,attrs);

    }

    public LawyerServiceStatusDemandDetailFragment(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        this.buildViews();
        this.buildButtons();
    }

    public void buildViews() {
        this.forumTextView = (TextView)findViewById(R.id.forumTextView);
        this.serviceTypeTextView = (TextView)findViewById(R.id.serviceTypeTextView);
        this.serviceValueTextView = (TextView)findViewById(R.id.serviceValueTextView);
        this.serviceDeliveryTypeTextView = (TextView)findViewById(R.id.serviceDeliveryTypeTextView);
        this.serviceTotalValueTextView = (TextView)findViewById(R.id.serviceTotalValueTextView);
    }

    public void buildButtons(){
        this.acceptButton = (ImageButton)findViewById(R.id.acceptButton);
        this.acceptButton.setOnClickListener(acceptClickListener);
        this.refuseButton = (ImageButton)findViewById(R.id.refuseButton);
        this.refuseButton.setOnClickListener(refuseClickListener);
        this.againstProposalButton = (ImageButton)findViewById(R.id.againstProposalButton);
        this.againstProposalButton.setOnClickListener(againstProposalClickListener);
    }

    public void setupTextsFields(String forumName, String serviceTypeName, String serviceValue, String serviceDeliveryType, String serviceTotalValue){
        this.forumTextView.setText(forumName);
        this.serviceTypeTextView.setText(serviceTypeName);
        this.serviceValueTextView.setText(serviceValue);
        this.serviceDeliveryTypeTextView.setText(serviceDeliveryType);
        this.serviceTotalValueTextView.setText(serviceTotalValue);
    }

}
