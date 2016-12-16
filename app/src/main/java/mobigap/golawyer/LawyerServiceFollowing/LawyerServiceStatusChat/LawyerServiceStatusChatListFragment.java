package mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusChat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Model.ServiceStatusChatModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;

public class LawyerServiceStatusChatListFragment extends LinearLayout {

    ListView listView;
    Button sendChatMessageButton;
    EditText newChatMessageEditText;
    LawyerServiceStatusChatActionButtonsView header;

    ArrayList<ServiceStatusChatModel> chatModels = new ArrayList<>();

    private View.OnClickListener sendChatMessageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            String currentDate =dateFormat.format(cal.getTime());

            String typedText = newChatMessageEditText.getText().toString();

            ServiceStatusChatModel serviceStatusChatModel = new ServiceStatusChatModel(ApplicationState.sharedState().getCurrentUser(getContext()).getName(),currentDate ,typedText);
            chatModels.add(serviceStatusChatModel);
            loadChatItemsList();
            adjustListView();

            newChatMessageEditText.setText("");
            newChatMessageEditText.clearComposingText();
            newChatMessageEditText.clearFocus();

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
        header = (LawyerServiceStatusChatActionButtonsView)listView.inflate(getContext(),R.layout.view_lawyer_service_chat_action_button,null);
        listView.addHeaderView(header);

    }
    
    public void loadChatItemsList() {
        LawyerServiceStatusChatListAdapter adapter = new LawyerServiceStatusChatListAdapter(getContext(), chatModels);
        listView.setAdapter(adapter);
    }

    public void adjustListView(){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) listView.getLayoutParams();
        layoutParams.height = 100*(listView.getCount()+1);
    }

    public  void adjustButtons(TypeProfile typeProfile){
        if (typeProfile==TypeProfile.CLIENT){
            ImageButton confirmButton = (ImageButton) header.findViewById(R.id.confirmServiceStatusChatButton);
            confirmButton.setVisibility(GONE);
        }
    }

    public void setChatModels(ArrayList<ServiceStatusChatModel> chatModels) {
        this.chatModels = chatModels;
    }
}
