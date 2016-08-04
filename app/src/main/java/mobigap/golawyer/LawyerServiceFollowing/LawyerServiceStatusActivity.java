package mobigap.golawyer.LawyerServiceFollowing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Enums.ServiceStatusEnum;
import mobigap.golawyer.R;

public class LawyerServiceStatusActivity extends AppCompatActivity {

    ImageView backgroundPhotoImage;
    ImageButton cameraButton;
    CircleImageView profileImageView;
    TextView hasOABTextView, specialtyTextView,nameTextView;

    ImageView demandImage, paymentImage, chatImage, deliveryImage, rateImage;
    TextView demandDescription, paymentDescription, chatDescription, deliveryDescription, rateDescription;

    LawyerServiceStatusDemandDetailFragment lawyerServiceStatusDemandDetailFragment;

    private int requestedServiceId;
    private ServiceStatusEnum currentStatus = ServiceStatusEnum.DEMAND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_service_status);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.requestedServiceId = bundle.getInt("requestedServiceId");

        this.getViewInstances();
        this.setPropertiesViews();
        this.setupServiceStatusSegment();
    }

    public void getViewInstances() {
        View profilePhotoView = findViewById(R.id.lawyerProfilePhoto);
        cameraButton = (ImageButton) profilePhotoView.findViewById(R.id.cameraButton);
        profileImageView = (CircleImageView) profilePhotoView.findViewById(R.id.profileImage);
        hasOABTextView = (TextView) profilePhotoView.findViewById(R.id.hasOABTextView);
        specialtyTextView = (TextView) profilePhotoView.findViewById(R.id.specialtyTextView);
        nameTextView = (TextView) profilePhotoView.findViewById(R.id.nameTextView);
        backgroundPhotoImage = (ImageView) profilePhotoView.findViewById(R.id.backgroundPhotoImage);

        View serviceStatusSegment = findViewById(R.id.lawyerServiceStatusSegment);

        demandImage = (ImageView) serviceStatusSegment.findViewById(R.id.demandImage);
        paymentImage = (ImageView) serviceStatusSegment.findViewById(R.id.paymentImage);
        chatImage = (ImageView) serviceStatusSegment.findViewById(R.id.chatImage);
        deliveryImage = (ImageView) serviceStatusSegment.findViewById(R.id.deliveryImage);
        rateImage = (ImageView) serviceStatusSegment.findViewById(R.id.rateImage);

        demandDescription = (TextView) serviceStatusSegment.findViewById(R.id.demandDescription);
        paymentDescription = (TextView) serviceStatusSegment.findViewById(R.id.paymentDescription);
        chatDescription = (TextView) serviceStatusSegment.findViewById(R.id.chatDescription);
        deliveryDescription = (TextView) serviceStatusSegment.findViewById(R.id.deliveryDescription);
        rateDescription = (TextView) serviceStatusSegment.findViewById(R.id.rateDescription);

        lawyerServiceStatusDemandDetailFragment = (LawyerServiceStatusDemandDetailFragment) findViewById(R.id.demandInformationFragment);

    }

    private void setPropertiesViews(){

        cameraButton.setVisibility(View.INVISIBLE);
        lawyerServiceStatusDemandDetailFragment.setupTextsFields("Fórum de Novo Gama","Criação de contrato", "R$: 500,00","Correios -> Carta","R$: 1500,00");
    }

    public void setupServiceStatusSegment() {
        switch (currentStatus) {
            case DEMAND:
                demandImage.setImageResource(R.drawable.back_arrow);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                break;
            case PAYMENT:
                demandImage.setImageResource(R.drawable.down_mini_arrow);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                paymentImage.setImageResource(R.drawable.back_arrow);
                paymentDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                break;
            case CHAT:
                demandImage.setImageResource(R.drawable.down_mini_arrow);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                paymentImage.setImageResource(R.drawable.down_mini_arrow);
                paymentDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                chatImage.setImageResource(R.drawable.back_arrow);
                chatDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                break;
            case DELIVERY:
                demandImage.setImageResource(R.drawable.down_mini_arrow);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                paymentImage.setImageResource(R.drawable.down_mini_arrow);
                paymentDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                chatImage.setImageResource(R.drawable.down_mini_arrow);
                chatDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                deliveryImage.setImageResource(R.drawable.back_arrow);
                deliveryDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                break;
            case RATE:
                demandImage.setImageResource(R.drawable.down_mini_arrow);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                paymentImage.setImageResource(R.drawable.down_mini_arrow);
                paymentDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                chatImage.setImageResource(R.drawable.down_mini_arrow);
                chatDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                deliveryImage.setImageResource(R.drawable.down_mini_arrow);
                deliveryDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                rateImage.setImageResource(R.drawable.back_arrow);
                rateDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                break;
        }
    }
}
