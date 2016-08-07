package mobigap.golawyer.LawyerServiceFollowing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Enums.ServiceStatusEnum;
import mobigap.golawyer.Extensions.LayoutManagerExtension;
import mobigap.golawyer.R;

public class LawyerServiceStatusActivity extends AppCompatActivity {

    ImageView backgroundPhotoImage;
    ImageButton cameraButton;
    CircleImageView profileImageView;
    TextView hasOABTextView, specialtyTextView,nameTextView;

    ImageView demandImage, paymentImage, chatImage, deliveryImage, rateImage;
    TextView demandDescription, paymentDescription, chatDescription, deliveryDescription, rateDescription;

    private int requestedServiceId;
    private ServiceStatusEnum currentStatus = ServiceStatusEnum.DELIVERY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_service_status);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        this.requestedServiceId = bundle.getInt("requestedServiceId");

        switch (this.requestedServiceId) {
            case 0:
                currentStatus = ServiceStatusEnum.DEMAND;
                break;
            case 1:
                currentStatus = ServiceStatusEnum.PAYMENT;
                break;
            case 2:
                currentStatus = ServiceStatusEnum.CHAT;
                break;
            case 3:
                currentStatus = ServiceStatusEnum.DELIVERY;
                break;
            case 4:
                currentStatus = ServiceStatusEnum.RATE;
                break;
        }

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


    }

    private void setPropertiesViews(){

        cameraButton.setVisibility(View.INVISIBLE);
    }

    public void setupServiceStatusSegment() {
        switch (currentStatus) {
            case DEMAND:
                demandImage.setImageResource(R.drawable.ic_demand_active);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                LayoutManagerExtension.addLayout(this,R.id.serviceStatusInfoStub,R.layout.fragment_lawyer_service_status_demand_detail);
                LawyerServiceStatusDemandDetailFragment lawyerServiceStatusDemandDetailFragment = (LawyerServiceStatusDemandDetailFragment) findViewById(R.id.serviceStatusInfoLayout);
                lawyerServiceStatusDemandDetailFragment.setupTextsFields("Fórum de Novo Gama","Criação de contrato", "R$: 500,00","Correios -> Carta","R$: 1500,00");

                break;
            case PAYMENT:
                demandImage.setImageResource(R.drawable.ic_demand_passed);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                paymentImage.setImageResource(R.drawable.ic_payment_active);
                paymentDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                LayoutManagerExtension.addLayout(this,R.id.serviceStatusInfoStub,R.layout.fragment_lawyer_service_status_payment);

                break;
            case CHAT:
                demandImage.setImageResource(R.drawable.ic_demand_passed);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                paymentImage.setImageResource(R.drawable.ic_payment_passed);
                paymentDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                chatImage.setImageResource(R.drawable.chat_active);
                chatDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                LayoutManagerExtension.addLayout(this,R.id.serviceStatusInfoStub,R.layout.fragment_lawyer_service_status_chat_list);


                break;
            case DELIVERY:
                demandImage.setImageResource(R.drawable.ic_demand_passed);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                paymentImage.setImageResource(R.drawable.ic_payment_passed);
                paymentDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                chatImage.setImageResource(R.drawable.chat_passed);
                chatDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                deliveryImage.setImageResource(R.drawable.ic_delivery_active);
                deliveryDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                LayoutManagerExtension.addLayout(this,R.id.serviceStatusInfoStub,R.layout.fragment_lawyer_service_status_delivery);
                LawyerServiceStatusDeliveryFragment lawyerServiceStatusDeliveryFragment = (LawyerServiceStatusDeliveryFragment) findViewById(R.id.serviceStatusInfoLayout);
                lawyerServiceStatusDeliveryFragment.setupTextsFields("Por favor, aguarde a finalização da demanda","Iniciado em 31/05/2016 ás 13:00 h", "Você poderá cancelar a demanda a qualquer momento. O pagamento será extornado mas fique atento para não ser classificado negativamente.");

                break;
            case RATE:
                demandImage.setImageResource(R.drawable.ic_demand_passed);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                paymentImage.setImageResource(R.drawable.ic_payment_passed);
                paymentDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                chatImage.setImageResource(R.drawable.chat_passed);
                chatDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                deliveryImage.setImageResource(R.drawable.chat_passed);
                deliveryDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                rateImage.setImageResource(R.drawable.ic_rate_active);
                rateDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                LayoutManagerExtension.addLayout(this,R.id.serviceStatusInfoStub,R.layout.fragment_lawyer_service_status_rate);
                LawyerServiceStatusRateFragment lawyerServiceStatusRateFragment = (LawyerServiceStatusRateFragment) findViewById(R.id.serviceStatusInfoLayout);
                lawyerServiceStatusRateFragment.setupTextsFields("Avalie o atendimento do profissional","", "Após efetuar sua avaliação o pagamento será liberado para o advogado automaticamente.");


                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.service_status_action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_map:
                Toast.makeText(this, "map selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_update:
                Toast.makeText(this, "update selected", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
