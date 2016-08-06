package mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.R;

/**
 * Created by thiagoMB on 7/29/16.
 */
public class LawyerServiceStatusChatListAdapter extends BaseAdapter {

    private static ServiceRequestModel[] data;
    private Context context;

    public LawyerServiceStatusChatListAdapter(Context context, ServiceRequestModel[] data) {
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

        LawyerServiceStatusChatListRow row;
        ServiceRequestModel currentModel = data[position];

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.fragment_lawyer_service_request_list_row, parent, false);

            row = new LawyerServiceStatusChatListRow();
            row.serviceRequesetRowProfileImageView = (CircleImageView) convertView.findViewById(R.id.serviceRequestRowProfileImageView);
            row.serviceRequestRowTitle = (TextView) convertView.findViewById(R.id.serviceRequestRowTitle);
            row.serviceRequestRowDescription = (TextView) convertView.findViewById(R.id.serviceRequestRowDescription);
            row.serviceRequestAlertImage = (ImageView) convertView.findViewById(R.id.serviceRequestAlertImage);

            convertView.setTag(row);

        }else{
            row = (LawyerServiceStatusChatListRow) convertView.getTag();
        }

        if (!currentModel.isWarning) {
            row.serviceRequestAlertImage.setVisibility(View.INVISIBLE);
        } else {
            row.serviceRequestAlertImage.setVisibility(View.VISIBLE);
        }
        //TODO: Set the real image
//        row.serviceRequesetRowProfileImageView.setImageBitmap();
        row.serviceRequestRowDescription.setText(currentModel.description);
        row.serviceRequestRowTitle.setText(currentModel.title);

        return convertView;
    }
}
