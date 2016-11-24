package mobigap.golawyer.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Model.LawyerRowModel;
import mobigap.golawyer.R;

/**
 * Created by luisresende on 12/08/16.
 */
public class MapListAdapter extends BaseAdapter {

    private static ArrayList<LawyerModel> data;
    private Context context;

    public MapListAdapter(Context context, ArrayList<LawyerModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MapListRow row;
        LawyerModel currentModel = data.get(position);

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

        if (currentModel.getImageBytes()!=null){
            row.rowProfileImageView.setImageBitmap(ImageConvert.getDecode64ImageStringFromByte(currentModel.getImageBytes()));
        }
        row.rowDescription.setText(currentModel.getStatus());
        row.rowTitle.setText(currentModel.getName());
        row.idLawyer = currentModel.getIdLawyer();

        return convertView;
    }

    public void updateData(ArrayList<LawyerModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

}
