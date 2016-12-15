package mobigap.golawyer.LawyerServiceFollowing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Enums.ServiceStatusEnum;
import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Extensions.LayoutManagerExtension;
import mobigap.golawyer.LawyerServiceStatusDemand.LawyerServiceStatusDemandDetailFragment;
import mobigap.golawyer.Model.DemandModel;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.UserRequest;

public class LawyerServiceStatusActivity extends AppCompatActivity {

    ImageView backgroundPhotoImage;
    ImageButton cameraButton;
    CircleImageView profileImageView;
    TextView hasOABTextView,nameTextView;

    ImageView demandImage, paymentImage, chatImage, deliveryImage, rateImage;
    TextView demandDescription, paymentDescription, chatDescription, deliveryDescription, rateDescription;

    private int requestedServiceId;
    private ServiceStatusEnum currentStatus = ServiceStatusEnum.DELIVERY;
    private LawyerModel lawyerModel=null;
    private static int CONST_IMAGE_BLUR = 25;
    private DemandModel demandModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_service_status);

        demandModel = ApplicationState.sharedState().getDemandModel();

        this.requestedServiceId = demandModel.getStep();

        String idLawyer = demandModel.getIdLawyer();
        ArrayList<LawyerModel> lawyerModelArrayList = ApplicationState.sharedState().getLawyersRequestModels();

        for (LawyerModel lawyerModelArray: lawyerModelArrayList){
            if (lawyerModelArray.getIdLawyer().equals(idLawyer)){
                lawyerModel = lawyerModelArray;
                break;
            }
        }

        switch (this.requestedServiceId) {
            case 1:
                currentStatus = ServiceStatusEnum.DEMAND;
                break;
            case 2:
                currentStatus = ServiceStatusEnum.PAYMENT;
                break;
            case 3:
                currentStatus = ServiceStatusEnum.CHAT;
                break;
            case 4:
                currentStatus = ServiceStatusEnum.DELIVERY;
                break;
            case 5:
                currentStatus = ServiceStatusEnum.RATE;
                break;
            //TODO:ADICIONAR O PASSO 6: DEMANDA FINALIZADA
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
        getImage(lawyerModel.getPhoto());
        cameraButton.setVisibility(View.INVISIBLE);
        hasOABTextView.setText(lawyerModel.getOab());
        nameTextView.setText(lawyerModel.getName());
    }

    private void getImage(String photo){
        UserRequest.getImage(photo, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap imageBitmap = ImageConvert.getDecode64ImageStringFromByte(responseBody);
                profileImageView.setImageBitmap(imageBitmap);
                Bitmap blurred = ImageConvert.blurRenderScript(getApplicationContext(),imageBitmap, CONST_IMAGE_BLUR);
                backgroundPhotoImage.setImageBitmap(blurred);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void setupServiceStatusSegment() {
        switch (currentStatus) {
            case DEMAND:
                demandImage.setImageResource(R.drawable.ic_demand_active);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                LayoutManagerExtension.addLayout(this,R.id.serviceStatusInfoStub,R.layout.fragment_lawyer_service_status_demand_detail);
                LawyerServiceStatusDemandDetailFragment lawyerServiceStatusDemandDetailFragment = (LawyerServiceStatusDemandDetailFragment) findViewById(R.id.serviceStatusInfoLayout);
                lawyerServiceStatusDemandDetailFragment.setupView(demandModel.getInformationDemand(),demandModel.getTypeProfile());
                break;
            case PAYMENT:
                demandImage.setImageResource(R.drawable.ic_demand_passed);
                demandDescription.setTextColor(ContextCompat.getColor(this, R.color.blueLightApp));

                paymentImage.setImageResource(R.drawable.ic_payment_active);
                paymentDescription.setTextColor(ContextCompat.getColor(this, R.color.blueApp));

                if (demandModel.getTypeProfile()== TypeProfile.LAWYER){
                    LayoutManagerExtension.addLayout(this,R.id.serviceStatusInfoStub,R.layout.fragment_lawyer_service_status_payment_lawyer);
                    TextView feedbackTextView = (TextView) findViewById(R.id.feedbackTextView);
                    feedbackTextView.setText(demandModel.getFeedbackText());
                }else {
                    LayoutManagerExtension.addLayout(this,R.id.serviceStatusInfoStub,R.layout.fragment_lawyer_service_status_payment);
                }

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
}
