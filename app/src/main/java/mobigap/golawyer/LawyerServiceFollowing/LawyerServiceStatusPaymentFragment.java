package mobigap.golawyer.LawyerServiceFollowing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import mobigap.golawyer.R;


public class LawyerServiceStatusPaymentFragment extends LinearLayout {


    private EditText creditCardName, creditCardNumber, creditCardExpireDate, creditCardCVV, creditCardCellphoneNumber;

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


    public LawyerServiceStatusPaymentFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawyerServiceStatusPaymentFragment(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        buildViews();
        buildButtons();
    }

    public void buildViews() {
        this.creditCardName = (EditText) findViewById(R.id.editTextCreditCardName);
        this.creditCardNumber = (EditText)findViewById(R.id.editTextCreditCardNumber);
        this.creditCardExpireDate = (EditText)findViewById(R.id.editTextCreditCardExpireDate);
        this.creditCardCVV = (EditText)findViewById(R.id.editTextCreditCardCVV);
        this.creditCardCellphoneNumber = (EditText)findViewById(R.id.editTextCreditCardCellPhoneNumber);
    }

    public void buildButtons(){
        this.confirmButton = (ImageButton)findViewById(R.id.paymentConfirmButton);
        this.confirmButton.setOnClickListener(confirmClickListener);
        this.cancelButton = (ImageButton)findViewById(R.id.paymentCancelButton);
        this.cancelButton.setOnClickListener(cancelClickListener);
    }

    public void setupTextsFields(String creditCardName, String creditCardNumber, String creditCardExpireDate, String creditCardCVV, String creditCardCellphoneNumber){
        this.creditCardName.setText(creditCardName);
        this.creditCardNumber.setText(creditCardNumber);
        this.creditCardExpireDate.setText(creditCardExpireDate);
        this.creditCardCVV.setText(creditCardCVV);
        this.creditCardCellphoneNumber.setText(creditCardCellphoneNumber);
    }

}
