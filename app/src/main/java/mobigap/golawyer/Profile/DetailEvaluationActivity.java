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

        CommentModel comment1 = new CommentModel("http://image","Troy Beck", "Avaliacao 5 estrelas",5);
        CommentModel comment2 = new CommentModel("http://image","Mathilda Robbins", "Avaliacao 4 estrelas",4);
        CommentModel comment3 = new CommentModel("http://image","Samuel Cook", "Avaliacao 3 estrelas",3);
        CommentModel comment4 = new CommentModel("http://image","Bettie Mills", "Avaliacao 2 estrelas",2);
        CommentModel comment5 = new CommentModel("http://image","Alexander Hill ", "Avaliacao 1 estrela",1);
        CommentModel comment6 = new CommentModel("http://image","Caroly Stanley ", "Avaliacao 0 estrelas",0);

        CommentModel[] dummyData = new CommentModel[6];
        dummyData[0] = comment1;
        dummyData[1] = comment2;
        dummyData[2] = comment3;
        dummyData[3] = comment4;
        dummyData[4] = comment5;
        dummyData[5] = comment6;

        return dummyData;

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
