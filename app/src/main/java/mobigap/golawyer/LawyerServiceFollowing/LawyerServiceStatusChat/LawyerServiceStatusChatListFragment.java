package mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusChat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import mobigap.golawyer.Model.ServiceStatusChatModel;
import mobigap.golawyer.R;

public class LawyerServiceStatusChatListFragment extends LinearLayout {

    ListView listView;
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
    
    private void loadChatItemsList(ServiceStatusChatModel[] chatItemsRequested) {
        LawyerServiceStatusChatListAdapter adapter = new LawyerServiceStatusChatListAdapter(getContext(), chatItemsRequested);
        listView.setAdapter(adapter);
    }

    private ServiceStatusChatModel[] getDummyData() {

        ServiceStatusChatModel chatItem1 = new ServiceStatusChatModel("Thiago Bernardes", "07/10/1994","O thiago nasceu e gostaria de resolver os documentos.");
        ServiceStatusChatModel chatItem2 = new ServiceStatusChatModel("Thiago Bernardes", "07/10/1994","Distribution of Derivative Works. If You create or to which You contribute are governed by the conditions below give you the freedom to distribute copies of this Agreement shall be interpreted to prohibit Licensor from licensing under different terms from those contained in this License and not on behalf of any works derived from this License to do the following: 2.1 Unmodified Code.");
        ServiceStatusChatModel chatItem3 = new ServiceStatusChatModel("Thiago Bernardes", "07/10/1994","O thiago nasceu e gostaria de resolver os documentos.");
        ServiceStatusChatModel chatItem4 = new ServiceStatusChatModel("Thiago Bernardes", "07/10/1994","O thiago nasceu e gostaria de resolver os documentos.");
        ServiceStatusChatModel chatItem5 = new ServiceStatusChatModel("Thiago Bernardes", "07/10/1994","O thiago nasceu e gostaria de resolver os documentos.");
        ServiceStatusChatModel chatItem6 = new ServiceStatusChatModel("Thiago Bernardes", "07/10/1994","O thiago nasceu e gostaria de resolver os documentos.");
        ServiceStatusChatModel chatItem7 = new ServiceStatusChatModel("Thiago Bernardes", "07/10/1994","O thiago nasceu e gostaria de resolver os documentos.");

        ServiceStatusChatModel[] dummyData = new ServiceStatusChatModel[7];
        dummyData[0] = chatItem1;
        dummyData[1] = chatItem2;
        dummyData[2] = chatItem3;
        dummyData[3] = chatItem4;
        dummyData[4] = chatItem5;
        dummyData[5] = chatItem6;
        dummyData[6] = chatItem7;

        return dummyData;

    }
}
