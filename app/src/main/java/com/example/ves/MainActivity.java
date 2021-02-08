package com.example.ves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    EditText edtId, edtPw;
    Button btnLogin=(Button) findViewById(R.id.btnLogin);
    Button btnJoin=(Button) findViewById(R.id.btnJoin);
    ImageView imgVes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        edtId = (EditText) findViewById(R.id.edtId);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        JoinActivity.class);
                startActivity(intent);
            }
        });

    }
}