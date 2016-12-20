package mobigap.golawyer.Map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Profile.ProfileLawyerActivity;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.Requester;
import mobigap.golawyer.Requests.UserRequest;

public class MapListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {

    ListView listView;
    private MapListAdapter adapter;
    private SearchView searchView;
    private ArrayList<LawyerModel> lawyerModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        setTitle(getString(R.string.title_activity_map_list));
        getInstanceViews();
        setPropertiesView();
        loadRequestedLawyersList();
    }

    private void getInstanceViews(){
        this.listView = (ListView) findViewById(R.id.mapListView);
        this.searchView = (SearchView) findViewById(R.id.searchViewList);
    }

    private void setPropertiesView(){

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

        searchView.setOnQueryTextListener(this);

    }

    private void loadRequestedLawyersList() {
        lawyerModelArrayList = ApplicationState.sharedState().getLawyersRequestModels();
        adapter = new MapListAdapter(getApplicationContext(), lawyerModelArrayList);
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
        bundle.putString(Requester.keyMessage,((MapListRow)view.getTag()).idLawyer);
        ActivityManager.changeActivity(MapListActivity.this, ProfileLawyerActivity.class,bundle);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterList(newText);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                resetFilter();
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshList();
    }

    public void filterList(String stringFilter){
        ArrayList<LawyerModel> arrayFiltered = new ArrayList<>();
        for (LawyerModel lawyerModel : lawyerModelArrayList){
            if (lawyerModel.getName().toLowerCase().contains(stringFilter.toLowerCase())){
                arrayFiltered.add(lawyerModel);
            }
        }
        if (!arrayFiltered.isEmpty()){
            adapter.updateData(arrayFiltered);
        }
    }

    public void resetFilter(){
        adapter.updateData(lawyerModelArrayList);
    }

    public void refreshList(){
        adapter = new MapListAdapter(this, lawyerModelArrayList);
        listView.setAdapter(adapter);
    }


}
