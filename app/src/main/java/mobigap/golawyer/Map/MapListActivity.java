package mobigap.golawyer.Map;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Model.LawyerRowModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.LawyerRequest;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

public class MapListActivity extends AppCompatActivity {

    ListView listView;
    private ProgressDialog progressDialog;
    private ArrayList<LawyerModel> lawyersRequestModels;
    private MapListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        getInstanceViews();
        setPropertiesView();
        getLawyers();
    }

    private void getInstanceViews(){
        this.listView = (ListView) findViewById(R.id.mapListView);
    }

    private void setPropertiesView(){
        SearchView searchView = (SearchView) findViewById(R.id.searchViewList);

        int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) searchView.findViewById(searchSrcTextId);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        int closeButtonId = getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButtonImage = (ImageView) searchView.findViewById(closeButtonId);
        closeButtonImage.setColorFilter(getResources().getColor(R.color.white));

        int searchButtonId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView searchButtonImage = (ImageView) searchView.findViewById(searchButtonId);
        searchButtonImage.setColorFilter(getResources().getColor(R.color.white));

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadRequestedLawyersList(ArrayList<LawyerModel> lawyersRequested) {
        adapter = new MapListAdapter(getApplicationContext(), lawyersRequested);
        listView.setAdapter(adapter);
    }

    private void getLawyers(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_message_dialog));

        LawyerRequest.getLawyers("-14","55", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();
                        lawyersRequestModels = new ArrayList<>();
                        if (Requester.haveSuccess(response)){
                            //Get ServiceRequestModel
                            JSONArray arrayLawyersRequestModel = Requester.getJsonArray(response, LawyerModel.keyItensDataModel);
                            for (int i=0; i<arrayLawyersRequestModel.length(); i++){
                                JSONObject jsonObject = Requester.getJsonObject(arrayLawyersRequestModel,i);
                                LawyerModel lawyerRequestModel = new LawyerModel(jsonObject);
                                setImage(lawyerRequestModel);
                                lawyersRequestModels.add(lawyerRequestModel);
                            }

                            //Update view
                            loadRequestedLawyersList(lawyersRequestModels);

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
        FeedbackManager.createToast(this,response);
    }

    private void createErrorToast(){
        FeedbackManager.feedbackErrorResponse(this,progressDialog);
    }

    private void setImage(final LawyerModel lawyerRequestModel){

        UserRequest.getImage(lawyerRequestModel.getPhoto(), new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                lawyerRequestModel.setImageBytes(responseBody);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
