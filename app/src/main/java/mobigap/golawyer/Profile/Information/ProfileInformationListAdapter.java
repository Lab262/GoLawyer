package mobigap.golawyer.Profile.Information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mobigap.golawyer.Model.ProfileInformationModel;
import mobigap.golawyer.R;

/**
 * Created by luisresende on 26/10/16.
 */

public class ProfileInformationListAdapter extends BaseAdapter {

    private static ArrayList<ProfileInformationModel> data;
    private Context context;

    public ProfileInformationListAdapter(Context context, ArrayList<ProfileInformationModel> data) {
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

        ProfileInformationListRow row;
        ProfileInformationModel currentModel = data.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.profile_information_row, parent, false);

            row = new ProfileInformationListRow();
            row.nameFieldTextView = (TextView) convertView.findViewById(R.id.nameFieldTextView);
            row.fieldTextView = (TextView) convertView.findViewById(R.id.fieldTextView);

            convertView.setTag(row);

        }else{
            row = (ProfileInformationListRow) convertView.getTag();
        }

        row.nameFieldTextView.setText(currentModel.nameField);
        row.fieldTextView.setText(currentModel.field);

        return convertView;
    }
}
