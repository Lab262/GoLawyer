package mobigap.golawyer.Extensions;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.TextView;

import mobigap.golawyer.Enums.TypeDemand;
import mobigap.golawyer.R;

/**
 * Created by luisresende on 29/11/16.
 */

public class CustomRadioButton extends RadioButton {

    private TypeDemand typeDemand;

    public CustomRadioButton(Context context) {
        super(context);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (checked){
            this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.radio_button_select, 0);
        }else {
            this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.radio_button_unselect, 0);
        }
    }

    public TypeDemand getTypeDemand() {
        return typeDemand;
    }

    public void setTypeDemand(TypeDemand typeDemand) {
        this.typeDemand = typeDemand;
    }
}
