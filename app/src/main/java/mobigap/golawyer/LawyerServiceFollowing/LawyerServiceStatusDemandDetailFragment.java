package mobigap.golawyer.LawyerServiceFollowing;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobigap.golawyer.R;

/**
 * Created by thiagoMB on 8/3/16.
 */
public class LawyerServiceStatusDemandDetailFragment extends LinearLayout {


    private TextView forumTextView, serviceTypeTextView, serviceValueTextView, serviceDeliveryTypeTextView, serviceTotalValueTextView;

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
        this.forumTextView = (TextView)findViewById(R.id.forumTextView);
        this.serviceTypeTextView = (TextView)findViewById(R.id.serviceTypeTextView);
        this.serviceValueTextView = (TextView)findViewById(R.id.serviceValueTextView);
        this.serviceDeliveryTypeTextView = (TextView)findViewById(R.id.serviceDeliveryTypeTextView);
        this.serviceTotalValueTextView = (TextView)findViewById(R.id.serviceTotalValueTextView);
    }

    public void setupTextsFields(String forumName, String serviceTypeName, String serviceValue, String serviceDeliveryType, String serviceTotalValue){
        this.forumTextView.setText(forumName);
        this.serviceTypeTextView.setText(serviceTypeName);
        this.serviceValueTextView.setText(serviceValue);
        this.serviceDeliveryTypeTextView.setText(serviceDeliveryType);
        this.serviceTotalValueTextView.setText(serviceTotalValue);

    }

}
