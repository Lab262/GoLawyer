package mobigap.golawyer.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Model.LawyerRowModel;
import mobigap.golawyer.R;

/**
 * Created by luisresende on 12/08/16.
 */
public class MapListAdapter extends BaseAdapter {

    private static LawyerRowModel[] data;
    private Context context;

    public MapListAdapter(Context context, LawyerRowModel[] data) {
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

        MapListRow row;
        LawyerRowModel currentModel = data[position];

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.fragment_map_list_row, parent, false);

            row = new MapListRow();
            row.rowProfileImageView = (CircleImageView) convertView.findViewById(R.id.rowProfileImageView);
            row.rowTitle = (TextView) convertView.findViewById(R.id.rowTitle);
            row.rowDescription = (TextView) convertView.findViewById(R.id.rowDescription);

            convertView.setTag(row);

        }else{
            row = (MapListRow) convertView.getTag();
        }

        //TODO: Set the real image
//        row.serviceRequesetRowProfileImageView.setImageBitmap();
        row.rowDescription.setText(currentModel.description);
        row.rowTitle.setText(currentModel.title);

        return convertView;
    }

}
