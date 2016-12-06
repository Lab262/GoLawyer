package mobigap.golawyer.LawyerServiceStatusDemand;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.LawyerServiceRequest.LawyerServiceRequestListRow;
import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.R;

/**
 * Created by luisresende on 06/12/16.
 */

public class DemandListAdapter extends BaseAdapter {

    private static HashMap<Integer,Pair<String,String>> data;
    private Context context;

    public DemandListAdapter(Context context, HashMap<Integer,Pair<String,String>> data) {
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

        DemandInformationRow row;
        Pair pairInformation = data.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.fragment_demand_information_row, parent, false);

            row = new DemandInformationRow();
            row.demandRowTitle = (TextView) convertView.findViewById(R.id.demandRowTitle);
            row.demandRowDescription = (TextView) convertView.findViewById(R.id.demandRowDescription);

            convertView.setTag(row);

        }else{
            row = (DemandInformationRow) convertView.getTag();
        }

        row.demandRowTitle.setText(pairInformation.first.toString());
        row.demandRowDescription.setText(pairInformation.second.toString());

        return convertView;
    }
}
