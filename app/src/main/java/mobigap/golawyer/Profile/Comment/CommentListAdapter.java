package mobigap.golawyer.Profile.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Model.CommentModel;
import mobigap.golawyer.R;

/**
 * Created by luisresende on 24/08/16.
 */
public class CommentListAdapter extends BaseAdapter {

    private static CommentModel[] data;
    private Context context;

    public CommentListAdapter(Context context, CommentModel[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentListRow row;
        CommentModel currentModel = data[position];

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.fragment_comment_list_row, parent, false);

            row = new CommentListRow();
            row.rowProfileImageView = (CircleImageView) convertView.findViewById(R.id.rowProfileImageView);
            row.rowName = (TextView) convertView.findViewById(R.id.rowName);
            row.rowComment = (TextView) convertView.findViewById(R.id.rowComment);
            row.ratingStarsImageView = (ImageView) convertView.findViewById(R.id.ratingStarsImageView);

            convertView.setTag(row);

        }else{
            row = (CommentListRow) convertView.getTag();
        }

        //TODO: Set the real image
        //TODO: Set the real rating stars
        row.rowName.setText(currentModel.getName());
        row.rowComment.setText(currentModel.getComment());
        row.ratingStarsImageView.setImageResource(getImageStarsByID(currentModel.getEvaluation()));

        return convertView;
    }

    private int getImageStarsByID(int imageID){
        switch (imageID){
            case 0:
                return R.drawable.blue_stars_0;
            case 1:
                return R.drawable.blue_stars_1;
            case 2:
                return R.drawable.blue_stars_2;
            case 3:
                return R.drawable.blue_stars_3;
            case 4:
                return R.drawable.blue_stars_4;
            case 5:
                return R.drawable.blue_stars_5;
            default:
                return R.drawable.blue_stars_0;
        }
    }

}
