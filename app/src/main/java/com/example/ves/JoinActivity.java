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
    Button btnIdcheck , btnJoin2;

    RadioGroup radioType;
    RadioButton rdStudent;
    RadioButton rdTeacher;

    @Override
    protected void onCreate(Bundle SavedInstanceState){
         radioType= (RadioGroup)findViewById(R.id.radioType);
        rdStudent = (RadioButton) findViewById(R.id.rdStudent);
        rdTeacher = (RadioButton) findViewById(R.id.rdTeacher);

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.join);

    }


}

