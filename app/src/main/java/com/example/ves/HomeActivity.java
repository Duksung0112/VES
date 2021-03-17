package com.example.ves;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

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

                getFragmentManager().beginTransaction().replace(R.id.container, new NewsActivity()).commit();

            }
        });

        imglv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new NewsActivity()).commit();

            }
        });

        imglv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new NewsActivity()).commit();


            }
        });

        return view;
    }

}