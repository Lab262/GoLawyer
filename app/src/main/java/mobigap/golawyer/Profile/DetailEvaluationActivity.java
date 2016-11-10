package mobigap.golawyer.Profile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Model.CommentModel;
import mobigap.golawyer.Model.EvaluationModel;
import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.Model.UserModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Profile.Comment.CommentListAdapter;
import mobigap.golawyer.R;

public class DetailEvaluationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    private View header_profile_detail_evaluation;
    private ImageView lawyerImageView;
    private TextView nameLawyer,miniCurriculumTextView,oabTextView;

    private View header_profile_information_detail_evaluation;
    private ImageButton ratingButton;
    private TextView numberAttendanceTextView,numberCompletedTextView;

    private View header_evaluation_detail;
    private TextView totalAttendanceTextView;
    private ProgressBar progressBar5, progressBar4, progressBar3, progressBar2, progressBar1;
    private ImageView totalStarsImageView;

    private String name, curriculum, oab;
    private byte[] profileImageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_evaluation);
        getSupportActionBar().setTitle(R.string.name_activity_detail_evaluation);
        Bundle bundleExtras = getIntent().getExtras();
        if (bundleExtras!=null){
            name = (String) bundleExtras.get(UserModel.keyName);
            curriculum = (String) bundleExtras.get(UserModel.keyCurriculum);
            oab = (String) bundleExtras.get(UserModel.keyOab);
            profileImageBytes = (byte[]) bundleExtras.get(UserModel.keyPhoto);
        }else {
            name = "";
            curriculum = "";
            oab = "";
        }
        getInstanceViews();
        loadRequestedCommentsList(ApplicationState.sharedState().currentUserInformationModel.getComments());
        adjustLayout();
    }

    private void getInstanceViews(){
        this.listView = (ListView) findViewById(R.id.commentListView);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadRequestedCommentsList(ArrayList<CommentModel> commentsRequested) {
        CommentListAdapter adapter = new CommentListAdapter(getApplicationContext(), commentsRequested);
        listView.setAdapter(adapter);
    }

    private void adjustLayout(){
        LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View header = inflater.inflate(
                R.layout.fragment_header_detail_evaluation, null, false);

        listView.addHeaderView(header);
        setViewsData(header);
    }

    private void setViewsData(View header){

        //Get instance of header for set
        header_profile_detail_evaluation = header.findViewById(R.id.header_profile_detail_evaluation);
        nameLawyer = (TextView) header_profile_detail_evaluation.findViewById(R.id.nameLawyer);
        miniCurriculumTextView = (TextView) header_profile_detail_evaluation.findViewById(R.id.miniCurriculumTextView);
        oabTextView = (TextView) header_profile_detail_evaluation.findViewById(R.id.oabTextView);
        lawyerImageView = (ImageView) header_profile_detail_evaluation.findViewById(R.id.lawyerImageView);

        header_profile_information_detail_evaluation = header.findViewById(R.id.header_profile_information_detail_evaluation);
        numberAttendanceTextView = (TextView) header_profile_information_detail_evaluation.findViewById(R.id.numberAttendanceTextView);
        numberCompletedTextView = (TextView) header_profile_information_detail_evaluation.findViewById(R.id.numberCompletedTextView);
        ratingButton = (ImageButton) header_profile_information_detail_evaluation.findViewById(R.id.ratingButton);

        header_evaluation_detail = header.findViewById(R.id.header_evaluation_detail);
        totalAttendanceTextView = (TextView) header_evaluation_detail.findViewById(R.id.totalAttendanceTextView);
        totalStarsImageView = (ImageView) header_evaluation_detail.findViewById(R.id.totalStarsImageView);
        progressBar5 = (ProgressBar) header_evaluation_detail.findViewById(R.id.progressBar5);
        progressBar4 = (ProgressBar) header_evaluation_detail.findViewById(R.id.progressBar4);
        progressBar3 = (ProgressBar) header_evaluation_detail.findViewById(R.id.progressBar3);
        progressBar2 = (ProgressBar) header_evaluation_detail.findViewById(R.id.progressBar2);
        progressBar1 = (ProgressBar) header_evaluation_detail.findViewById(R.id.progressBar1);

        setPropertysData();
    }

    private void setPropertysData(){
        nameLawyer.setText(name);
        miniCurriculumTextView.setText(curriculum);
        oabTextView.setText("OAB: " + oab);
        lawyerImageView.setImageBitmap(ImageConvert.getDecode64ImageStringFromByte(profileImageBytes));

        numberAttendanceTextView.setText(String.valueOf(ApplicationState.sharedState().currentUserInformationModel.getTotalOrders()));
        numberCompletedTextView.setText(String.valueOf(ApplicationState.sharedState().currentUserInformationModel.getTotalConcludedOrders()));
        EvaluationModel evaluationModel = ApplicationState.sharedState().currentUserInformationModel.getEvaluation();
        ratingButton.setImageResource(evaluationModel.getIdTotal());


        int maxValue = evaluationModel.getTotalFive()+evaluationModel.getTotalFour()+
                evaluationModel.getTotalThree()+evaluationModel.getTotalTwo()+evaluationModel.getTotalOne();
        totalAttendanceTextView.setText(String.valueOf(maxValue)+ " avaliações feitas");
        totalStarsImageView.setImageResource(evaluationModel.getIdTotal());
        progressBar5.setMax(maxValue);
        progressBar4.setMax(maxValue);
        progressBar3.setMax(maxValue);
        progressBar2.setMax(maxValue);
        progressBar1.setMax(maxValue);
        progressBar5.setProgress(evaluationModel.getTotalFive());
        progressBar4.setProgress(evaluationModel.getTotalFour());
        progressBar3.setProgress(evaluationModel.getTotalThree());
        progressBar2.setProgress(evaluationModel.getTotalTwo());
        progressBar1.setProgress(evaluationModel.getTotalOne());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
