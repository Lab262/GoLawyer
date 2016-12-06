package mobigap.golawyer.LawyerServiceRequest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusActivity;
import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

public class LawyerServiceRequestListFragment extends Fragment {

    private ListView listView;
    private View view;
    private ProgressDialog progressDialog;
    private ArrayList<ServiceRequestModel> serviceRequestModels;
    private LawyerServiceRequestListAdapter adapter;

    public AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            getOrder(((ServiceRequestModel)adapter.getItem(position)).getIdOrder(),position);
        }
    };

    public static LawyerServiceRequestListFragment newInstance() {
        LawyerServiceRequestListFragment fragment = new LawyerServiceRequestListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_lawyer_service_request_list, container, false);

        this.listView = (ListView) this.view.findViewById(R.id.serviceRequestListView);

        this.listView.setOnItemClickListener(this.clickListener);
        getDataProfile();
        return this.view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadRequestedServicesList(ArrayList<ServiceRequestModel> servicesRequested) {
        adapter = new LawyerServiceRequestListAdapter(getActivity().getApplicationContext(), servicesRequested);
        listView.setAdapter(adapter);
    }

    private void setImage(final ServiceRequestModel serviceRequestModel){

        UserRequest.getImage(serviceRequestModel.getProfileImageUrl(), new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                serviceRequestModel.setImageBytes(responseBody);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void getDataProfile(){
        progressDialog = FeedbackManager.createProgressDialog(getActivity(),getString(R.string.placeholder_message_dialog));

        UserRequest.getOrders(ApplicationState.sharedState().getCurrentUser(getActivity().getApplicationContext()).getId(),
                new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                serviceRequestModels = new ArrayList<>();
                if (Requester.haveSuccess(response)){
                    //Get ServiceRequestModel
                    JSONArray arrayServiceRequestModel = Requester.getJsonArray(response, ServiceRequestModel.keyItens);
                    for (int i=0; i<arrayServiceRequestModel.length(); i++){
                        JSONObject jsonObject = Requester.getJsonObject(arrayServiceRequestModel,i);
                        ServiceRequestModel serviceRequestModel = new ServiceRequestModel(jsonObject);
                        setImage(serviceRequestModel);
                        serviceRequestModels.add(serviceRequestModel);
                    }

                    //Update view
                    loadRequestedServicesList(serviceRequestModels);

                }else {
                    createToast(response);
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
        FeedbackManager.createToast(getActivity(),response);
    }

    private void createErrorToast(){
        FeedbackManager.feedbackErrorResponse(getActivity(),progressDialog);
    }

    private void getOrder(String idOrder, final int position){
        progressDialog = FeedbackManager.createProgressDialog(getActivity(),getString(R.string.placeholder_message_dialog));

        UserRequest.getOrder(ApplicationState.sharedState().getCurrentUser(getActivity().getApplicationContext()).getId(), idOrder,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();
                        //TODO: PARSER DA RESPOSTA PARA MONTAR

                        Bundle requestedServiceData = new Bundle();
                        requestedServiceData.putInt(ServiceRequestModel.keyStatus, position);
                        requestedServiceData.putString(ServiceRequestModel.keyIdLawyer, ((ServiceRequestModel)adapter.getItem(position)).getIdLawyer());
                        ActivityManager.changeActivity(getContext(), LawyerServiceStatusActivity.class, requestedServiceData);
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
