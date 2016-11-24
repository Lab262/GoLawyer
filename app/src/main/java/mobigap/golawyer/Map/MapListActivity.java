package mobigap.golawyer.Map;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Model.LawyerRowModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Profile.ProfileFragment;
import mobigap.golawyer.Profile.ProfileLawyerActivity;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.LawyerRequest;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

public class MapListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    private ProgressDialog progressDialog;
    private MapListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        getInstanceViews();
        setPropertiesView();
        loadRequestedLawyersList();
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

    private void loadRequestedLawyersList() {
        adapter = new MapListAdapter(getApplicationContext(), ApplicationState.sharedState().getLawyersRequestModels());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        getImageLawyers();
    }

    private void getImageLawyers(){
        for (LawyerModel lawyerModel: ApplicationState.sharedState().getLawyersRequestModels()){
            setImage(lawyerModel);
        }
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putInt(Requester.keyMessage,i);
        ActivityManager.changeActivity(MapListActivity.this, ProfileLawyerActivity.class,bundle);
    }
}
