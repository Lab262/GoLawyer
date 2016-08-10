package mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusChat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import mobigap.golawyer.R;

/**
 * TODO: document your custom view class.
 */
public class LawyerServiceStatusChatActionButtonsView extends LinearLayout {

    ImageButton confirmServiceStatusChatButton, cancelServiceStatusChatButton;

    OnClickListener confirmServiceStatusChatClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    OnClickListener cancelServiceStatusChatClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public LawyerServiceStatusChatActionButtonsView(Context context) {
        super(context);
    }

    public LawyerServiceStatusChatActionButtonsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawyerServiceStatusChatActionButtonsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        this.confirmServiceStatusChatButton = (ImageButton) findViewById(R.id.cancelServiceStatusChatButton);
        this.cancelServiceStatusChatButton = (ImageButton) findViewById(R.id.cancelServiceStatusChatButton);

        this.confirmServiceStatusChatButton.setOnClickListener(confirmServiceStatusChatClickListener);
        this.cancelServiceStatusChatButton.setOnClickListener(cancelServiceStatusChatClickListener);
    }
}
