package mobigap.golawyer.Profile.Information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import mobigap.golawyer.Model.ProfileInformationEditModel;
import mobigap.golawyer.R;

/**
 * Created by luisresende on 16/11/16.
 */

public class ProfileInformationEditListAdapter extends BaseAdapter {

    public ArrayList<ProfileInformationEditModel> data;
    private Context context;

    public ProfileInformationEditListAdapter(Context context, final ArrayList<ProfileInformationEditModel> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ProfileInformationListRow row;
        ProfileInformationEditModel currentModel = data.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.profile_information_row, null);

            row = new ProfileInformationListRow();
            row.nameFieldTextView = (TextView) convertView.findViewById(R.id.nameFieldTextView);
            row.fieldTextView = (EditText) convertView.findViewById(R.id.fieldTextView);
            convertView.setTag(row);
        }else{
            row = (ProfileInformationListRow) convertView.getTag();
        }

        row.nameFieldTextView.setText(currentModel.nameField);
        row.fieldTextView.setText(currentModel.field);
        row.fieldTextView.setId(position);
        row.fieldTextView.setInputType(currentModel.type);

        //we need to update adapter once we finish with editing
        row.fieldTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    data.get(position).field = Caption.getText().toString();
                }
            }
        });

        return convertView;
    }
}
