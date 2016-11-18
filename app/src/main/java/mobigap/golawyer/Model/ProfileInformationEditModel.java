package mobigap.golawyer.Model;

import android.text.InputType;

import java.util.Objects;

/**
 * Created by luisresende on 16/11/16.
 */

public class ProfileInformationEditModel {

    public String nameField;
    public String field;
    public int type;
    public String nameFieldWS;

    private static String typeText = "text";
    private static String typeNumber = "number";


    public ProfileInformationEditModel(String nameField, String field, String type, String nameFieldWS) {
        this.nameField = nameField;
        this.field = field;
        this.nameFieldWS = nameFieldWS;
        setType(type);
    }

    public void setType(String type) {
        if (type.equals(typeNumber)){
            this.type = InputType.TYPE_CLASS_NUMBER;
        }else if (type.equals(typeText)){
            this.type = InputType.TYPE_CLASS_TEXT;
        }
    }

    public void setField(String field) {
        this.field = field;
    }
}
