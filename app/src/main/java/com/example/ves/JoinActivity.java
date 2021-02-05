package com.example.ves;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class JoinActivity extends Activity {
    TextView tvId, tvPw , tvPwcheck, tvName, tvType;
    EditText edtId, edtPw , edtPwcheck, edtName;
    Button btnIdcheck , btnJoin;

    RadioGroup radioType = (RadioGroup)findViewById(R.id.radioType);
    RadioButton rdStudent = (RadioButton) findViewById(R.id.rdStudent);
    RadioButton rdTeacher = (RadioButton) findViewById(R.id.rdTeacher);

    @Override
    protected void onCreate(Bundle SavedInstanceState){

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.join);

    }


}

