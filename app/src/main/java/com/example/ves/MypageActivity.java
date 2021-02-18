package com.example.ves;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ves.R;


public class MypageActivity extends Fragment {

    public MypageActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage,
                container, false);
        Button setPro = (Button)view.findViewById(R.id.setPro);
        Button setInfo = (Button)view.findViewById(R.id.setInfo);
        Button setPre = (Button)view.findViewById(R.id.setPre);

        setPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MypageActivity fragment1 = new MypageActivity();
                //getSupport().beginTransaction().add(R.id.framelayout, fragment1).commit();

                //Intent intent = new Intent(getApplicationContext(),DefaultsoundActivity.class);
                //startActivity(intent);
            }
        });

        setInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(),ModifyActivity.class);
                //startActivity(intent);
            }
        });

        return view;
    }


}
