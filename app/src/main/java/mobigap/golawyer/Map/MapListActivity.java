package mobigap.golawyer.Map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import mobigap.golawyer.Model.LawyerRowModel;
import mobigap.golawyer.R;

public class MapListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        getInstanceViews();
        setPropertiesView();
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

        this.loadRequestedLawyersList(this.getDummyData());
    }

    private void loadRequestedLawyersList(LawyerRowModel[] lawyersRequested) {
        MapListAdapter adapter = new MapListAdapter(getApplicationContext(), lawyersRequested);
        listView.setAdapter(adapter);
    }

    private LawyerRowModel[] getDummyData() {

        LawyerRowModel lawyer1 = new LawyerRowModel("http://image","Troy Beck", "Informação do pedido");
        LawyerRowModel lawyer2 = new LawyerRowModel("http://image","Mathilda Robbins", "Informação do pedido");
        LawyerRowModel lawyer3 = new LawyerRowModel("http://image","Samuel Cook", "Informação do pedido");
        LawyerRowModel lawyer4 = new LawyerRowModel("http://image","Bettie Mills", "Informação do pedido");
        LawyerRowModel lawyer5 = new LawyerRowModel("http://image","Alexander Hill ", "Informação do pedido");
        LawyerRowModel lawyer6 = new LawyerRowModel("http://image","Caroly Stanley ", "Informação do pedido");
        LawyerRowModel lawyer7 = new LawyerRowModel("http://image","Benjamin  Medna ", "Informação do pedido");

        LawyerRowModel[] dummyData = new LawyerRowModel[7];
        dummyData[0] = lawyer1;
        dummyData[1] = lawyer2;
        dummyData[2] = lawyer3;
        dummyData[3] = lawyer4;
        dummyData[4] = lawyer5;
        dummyData[5] = lawyer6;
        dummyData[6] = lawyer7;

        return dummyData;

    }

}
