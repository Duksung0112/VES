package com.example.ves;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class MypageActivity extends FragmentActivity {
    Button setPro, setInfo, setPre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPro = (Button) findViewById(R.id.setPro);
        setInfo = (Button) findViewById(R.id.setInfo);
        setPre = (Button) findViewById(R.id.setPre);

        setPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DefaultsoundActivity.class);
                startActivity(intent);
            }
        });

        setInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                startActivity(intent);
            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menumain, container, false);


    }

}
