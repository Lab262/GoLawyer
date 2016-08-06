package mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusChat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.R;

public class LawyerServiceStatusChatListFragment extends LinearLayout {

    ListView listView;
    View view;
    Button sendChatMessageButton;
    EditText newChatMessageEditText;

    private View.OnClickListener sendChatMessageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public LawyerServiceStatusChatListFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawyerServiceStatusChatListFragment(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        this.listView = (ListView) this.findViewById(R.id.serviceChatListView);
        this.newChatMessageEditText = (EditText) this.findViewById(R.id.chatMessageEditText);
        this.sendChatMessageButton = (Button) this.findViewById(R.id.sendMessageChatButton);
        this.sendChatMessageButton.setOnClickListener(sendChatMessageClickListener);
        this.loadChatItemsList(this.getDummyData());

    }

    private void loadChatItemsList(ServiceRequestModel[] servicesRequested) {
        LawyerServiceStatusChatListAdapter adapter = new LawyerServiceStatusChatListAdapter(getContext(), servicesRequested);
        listView.setAdapter(adapter);
    }

    private ServiceRequestModel[] getDummyData() {

        ServiceRequestModel service1 = new ServiceRequestModel("http://image","Troy Beck", "Informação do pedido",true);
        ServiceRequestModel service2 = new ServiceRequestModel("http://image","Mathilda Robbins", "Informação do pedido",false);
        ServiceRequestModel service3 = new ServiceRequestModel("http://image","Samuel Cook", "Informação do pedido",false);
        ServiceRequestModel service4 = new ServiceRequestModel("http://image","Bettie Mills", "Informação do pedido",false);
        ServiceRequestModel service5 = new ServiceRequestModel("http://image","Alexander Hill ", "Informação do pedido",false);
        ServiceRequestModel service6 = new ServiceRequestModel("http://image","Caroly Stanley ", "Informação do pedido",false);
        ServiceRequestModel service7 = new ServiceRequestModel("http://image","Benjamin  Medna ", "Informação do pedido",false);

        ServiceRequestModel[] dummyData = new ServiceRequestModel[7];
        dummyData[0] = service1;
        dummyData[1] = service2;
        dummyData[2] = service3;
        dummyData[3] = service4;
        dummyData[4] = service5;
        dummyData[5] = service6;
        dummyData[6] = service7;

        return dummyData;

    }
}
