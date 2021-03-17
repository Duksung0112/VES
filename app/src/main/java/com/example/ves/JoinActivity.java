package com.example.ves;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class JoinActivity extends Activity {
    TextView tvId, tvPw , tvPwcheck, tvName, tvType;
    EditText edtId, edtPw , edtPwcheck, edtName;
    Button btnIdcheck , btnJoin2;
    RadioGroup radioType;
    RadioButton rdStudent;
    RadioButton rdTeacher;

    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.join);

        radioType= (RadioGroup)findViewById(R.id.radioType);
        rdStudent = (RadioButton) findViewById(R.id.rdStudent);
        rdTeacher = (RadioButton) findViewById(R.id.rdTeacher);
        btnJoin2 = (Button) findViewById(R.id.btnJoin2);
        edtPw = (EditText) findViewById(R.id.edtPw);
        edtPwcheck = (EditText) findViewById(R.id.edtPwcheck);




        btnJoin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPw.getText().toString().equals(edtPwcheck.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(), MenumainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

}

