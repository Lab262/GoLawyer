package mobigap.golawyer.Profile.Information;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobigap.golawyer.Model.ProfileInformationEditModel;
import mobigap.golawyer.R;

/**
 * Created by luisresende on 16/11/16.
 */

public class ProfileInformationEditListAdapter extends BaseAdapter {

    public static ArrayList<ProfileInformationEditModel> data;
    private Context context;
    HashMap<Integer,String> fields = new HashMap<>();
    ArrayList<TextWatcher> textWatchers = new ArrayList<>();

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
        final ProfileInformationEditModel currentModel = data.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.profile_information_row, parent, false);

            row = new ProfileInformationListRow();
            row.nameFieldTextView = (TextView) convertView.findViewById(R.id.nameFieldTextView);
            row.fieldTextView = (EditText) convertView.findViewById(R.id.fieldTextView);
            row.fieldTextView.addTextChangedListener(new Watcher(row.fieldTextView, position));
            convertView.setTag(row);
        }else{
            row = (ProfileInformationListRow) convertView.getTag();
        }

        row.nameFieldTextView.setText(currentModel.nameField);
        row.fieldTextView.setText(currentModel.field);
//
//        if (fields.get(position)==null){
//            row.fieldTextView.setText(currentModel.field);
//        }else {
//            row.fieldTextView.setText(fields.get(position));
//        }

        row.fieldTextView.setInputType(currentModel.type);

        return convertView;
    }
}

class Watcher implements TextWatcher{

    EditText editText;
    int position;

    public Watcher (EditText editText, int position){
        this.editText = editText;
        this.editText.addTextChangedListener(this);
        this.position = position;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        ProfileInformationEditListAdapter.data.get(position).setField(editable.toString());
        System.out.println(ProfileInformationEditListAdapter.data.get(position).nameField + editable.toString());
    }
}
