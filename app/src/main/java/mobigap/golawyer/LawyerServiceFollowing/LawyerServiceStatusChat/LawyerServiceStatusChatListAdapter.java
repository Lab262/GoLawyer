package mobigap.golawyer.LawyerServiceFollowing.LawyerServiceStatusChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mobigap.golawyer.Model.ServiceStatusChatModel;
import mobigap.golawyer.R;

/**
 * Created by thiagoMB on 7/29/16.
 */
public class LawyerServiceStatusChatListAdapter extends BaseAdapter {

    private static ArrayList<ServiceStatusChatModel> data;
    private Context context;

    public LawyerServiceStatusChatListAdapter(Context context, ArrayList<ServiceStatusChatModel> data) {
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

        LawyerServiceStatusChatListRow row;
        ServiceStatusChatModel currentModel = data.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.fragment_lawyer_service_status_chat_list_row, parent, false);

            row = new LawyerServiceStatusChatListRow();
            row.serviceStatusChatRowTitle = (TextView) convertView.findViewById(R.id.serviceStatusChatRowTitle);
            row.serviceStatusChatRowDescription = (TextView) convertView.findViewById(R.id.serviceStatusChatRowDescription);
            row.serviceStatusChatRowContent = (TextView) convertView.findViewById(R.id.serviceStatusChatRowContent);

            convertView.setTag(row);

        }else{
            row = (LawyerServiceStatusChatListRow) convertView.getTag();
        }

        row.serviceStatusChatRowTitle.setText(currentModel.title);
        row.serviceStatusChatRowDescription.setText(currentModel.description);
        row.serviceStatusChatRowContent.setText(currentModel.content);

        return convertView;
    }
}
