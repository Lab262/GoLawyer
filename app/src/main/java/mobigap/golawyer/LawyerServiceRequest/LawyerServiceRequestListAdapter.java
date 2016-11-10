package mobigap.golawyer.LawyerServiceRequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Extensions.ImageConvert;
import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.R;

/**
 * Created by thiagoMB on 7/29/16.
 */
public class LawyerServiceRequestListAdapter extends BaseAdapter {

    private static ArrayList<ServiceRequestModel> data;
    private Context context;

    public LawyerServiceRequestListAdapter(Context context, ArrayList<ServiceRequestModel> data) {
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

        LawyerServiceRequestListRow row;
        ServiceRequestModel currentModel = data.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.fragment_lawyer_service_request_list_row, parent, false);

            row = new LawyerServiceRequestListRow();
            row.serviceRequesetRowProfileImageView = (CircleImageView) convertView.findViewById(R.id.serviceRequestRowProfileImageView);
            row.serviceRequestRowTitle = (TextView) convertView.findViewById(R.id.serviceRequestRowTitle);
            row.serviceRequestRowDescription = (TextView) convertView.findViewById(R.id.serviceRequestRowDescription);
            row.serviceRequestAlertImage = (ImageView) convertView.findViewById(R.id.serviceRequestAlertImage);

            convertView.setTag(row);

        }else{
            row = (LawyerServiceRequestListRow) convertView.getTag();
        }

        if (!currentModel.getWarning()) {
            row.serviceRequestAlertImage.setVisibility(View.INVISIBLE);
        } else {
            row.serviceRequestAlertImage.setVisibility(View.VISIBLE);
        }
        if (currentModel.getImageBytes()!=null){
            row.serviceRequesetRowProfileImageView.setImageBitmap(ImageConvert.getDecode64ImageStringFromByte(currentModel.getImageBytes()));
        }
        row.serviceRequestRowDescription.setText(currentModel.getStatus());
        row.serviceRequestRowTitle.setText(currentModel.getNameLawyer());

        return convertView;
    }
}
