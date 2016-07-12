package mobigap.golawyer.Register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mobigap.golawyer.R;

public class RegisterActivity extends AppCompatActivity {

    View headerPersonalInformation, headerWhereFound, headerOfficeInformation, headerBankInformation, headerTermsUse;
    TextView namePersonalInformation, nameWhereFound, nameOfficeInformation, nameBankInformation, nameTermsUse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getInstanceViews();
        setPropertiesViews();
    }

    private void getInstanceViews(){
        headerPersonalInformation = findViewById(R.id.headerPersonalInformation);
        namePersonalInformation = (TextView) headerPersonalInformation.findViewById(R.id.nameHeader);

        headerWhereFound = findViewById(R.id.headerWhereFound);
        nameWhereFound = (TextView) headerWhereFound.findViewById(R.id.nameHeader);

        headerOfficeInformation = findViewById(R.id.headerOfficeInformation);
        nameOfficeInformation = (TextView) headerOfficeInformation.findViewById(R.id.nameHeader);

        headerBankInformation = findViewById(R.id.headerBankInformation);
        nameBankInformation = (TextView) headerBankInformation.findViewById(R.id.nameHeader);

        headerTermsUse = findViewById(R.id.headerTermsUse);
        nameTermsUse = (TextView) headerTermsUse.findViewById(R.id.nameHeader);
    }

    private void setPropertiesViews(){
        namePersonalInformation.setText(R.string.name_personal_information);
        nameWhereFound.setText(R.string.name_where_found);
        nameOfficeInformation.setText(R.string.name_office_information);
        nameBankInformation.setText(R.string.name_bank_information);
        nameTermsUse.setText(R.string.name_terms_use);
    }
}
