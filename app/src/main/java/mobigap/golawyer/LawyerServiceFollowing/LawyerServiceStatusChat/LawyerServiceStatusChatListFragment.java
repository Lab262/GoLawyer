package mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusChat;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusActivity;
import mobigap.golawyer.Model.ServiceStatusChatModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

public class LawyerServiceStatusChatListFragment extends LinearLayout implements View.OnClickListener {

    ListView listView;
    Button sendChatMessageButton;
    EditText newChatMessageEditText;
    LawyerServiceStatusChatActionButtonsView header;
    ImageButton confirmButton, cancelButton;
    ProgressDialog progressDialog;

    ArrayList<ServiceStatusChatModel> chatModels = new ArrayList<>();

    private View.OnClickListener sendChatMessageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            String currentDate =dateFormat.format(cal.getTime());

            String typedText = newChatMessageEditText.getText().toString();

            setDemandMessageOrder(typedText);

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
        confirmButton = (ImageButton) header.findViewById(R.id.confirmServiceStatusChatButton);
        cancelButton = (ImageButton) header.findViewById(R.id.cancelServiceStatusChatButton);
        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }
    
    public void loadChatItemsList() {
        LawyerServiceStatusChatListAdapter adapter = new LawyerServiceStatusChatListAdapter(getContext(), chatModels);
        listView.setAdapter(adapter);
    }

    public void adjustListView(){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) listView.getLayoutParams();
        layoutParams.height = 80*(listView.getCount()+1);
    }

    public  void adjustButtons(TypeProfile typeProfile){
        if (typeProfile==TypeProfile.CLIENT){
            confirmButton.setVisibility(GONE);
        }
    }

    public void setChatModels(ArrayList<ServiceStatusChatModel> chatModels) {
        this.chatModels = chatModels;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirmServiceStatusChatButton:
                finalizeDemandChatOrder();
                break;
            case R.id.cancelServiceStatusChatButton:
                cancelDemandOrder();
                break;
        }
    }

    private void cancelDemandOrder(){
        progressDialog = FeedbackManager.createProgressDialog(getContext(),getResources().getString(R.string.placeholder_message_dialog));

        UserRequest.setCancelDemandOrder(getContext(), ApplicationState.sharedState().getDemandModel().getIdOrder(),
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();
                        createToast(response);
                        if (Requester.haveSuccess(response)){
                            ((LawyerServiceStatusActivity) getContext()).finish();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        createErrorToast();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        createErrorToast();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        createErrorToast();
                    }
                });
    }

    private void createToast(JSONObject response){
        FeedbackManager.createToast(getContext(),response);
    }

    private void createErrorToast(){
        FeedbackManager.feedbackErrorResponse(getContext(),progressDialog);
    }

    private void setDemandMessageOrder(String message){
        progressDialog = FeedbackManager.createProgressDialog(getContext(),getResources().getString(R.string.placeholder_message_dialog));

        UserRequest.setDemandStepChatMessageOrder(getContext(), ApplicationState.sharedState().getDemandModel().getIdOrder(),
                message, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();
                        createToast(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        createErrorToast();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        createErrorToast();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        createErrorToast();
                    }
                });
    }

    private void finalizeDemandChatOrder(){
        progressDialog = FeedbackManager.createProgressDialog(getContext(),getResources().getString(R.string.placeholder_message_dialog));

        UserRequest.setFinalizeDemandOrder(getContext(), ApplicationState.sharedState().getDemandModel().getIdOrder(),
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();
                        createToast(response);
                        if (Requester.haveSuccess(response)){
                            ((LawyerServiceStatusActivity) getContext()).finish();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        createErrorToast();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        createErrorToast();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        createErrorToast();
                    }
                });
    }
}
