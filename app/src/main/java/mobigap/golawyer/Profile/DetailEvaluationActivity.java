package mobigap.golawyer.Profile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONObject;

import mobigap.golawyer.Model.CommentModel;
import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.Profile.Comment.CommentListAdapter;
import mobigap.golawyer.R;

public class DetailEvaluationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_evaluation);
        getInstanceViews();
        this.loadRequestedCommentsList(this.getDummyData());
        adjustLayout();
    }

    private void getInstanceViews(){
        this.listView = (ListView) findViewById(R.id.commentListView);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadRequestedCommentsList(CommentModel[] commentsRequested) {
        CommentListAdapter adapter = new CommentListAdapter(getApplicationContext(), commentsRequested);
        listView.setAdapter(adapter);
    }

    private CommentModel[] getDummyData() {

        //TODO: Preencher com dados reais

        return new CommentModel[0];

    }

    private void adjustLayout(){
        LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View header = inflater.inflate(
                R.layout.fragment_header_detail_evaluation, null, false);

        listView.addHeaderView(header);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
