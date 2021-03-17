package com.example.ves;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class HomeActivity extends Fragment {

    public HomeActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);

        ImageButton imglv1 = (ImageButton)view.findViewById(R.id.imglv1);
        ImageButton imglv2 = (ImageButton)view.findViewById(R.id.imglv2);
        ImageButton imglv3 = (ImageButton)view.findViewById(R.id.imglv3);




        imglv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new News1Activity()).commit();
            }
        });

        imglv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new News2Activity()).commit();
            }
        });

        imglv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new News3Activity()).commit();
            }
        });


        return view;
    }

}