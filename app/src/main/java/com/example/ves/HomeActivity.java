package com.example.ves;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
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

        if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }


        ImageButton imglv1 = (ImageButton)view.findViewById(R.id.imglv1);
        ImageButton imglv2 = (ImageButton)view.findViewById(R.id.imglv2);
        ImageButton imglv3 = (ImageButton)view.findViewById(R.id.imglv3);
        TextView tvlv1 = (TextView)view.findViewById(R.id.tvlv1);
        TextView tvlv2 = (TextView)view.findViewById(R.id.tvlv2);
        TextView tvlv3 = (TextView)view.findViewById(R.id.tvlv3);
        LinearLayout lo1 = (LinearLayout)view.findViewById(R.id.lo1);
        LinearLayout lo2 = (LinearLayout)view.findViewById(R.id.lo2);
        LinearLayout lo3 = (LinearLayout)view.findViewById(R.id.lo3);


        lo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new News1Activity()).commit();
            }
        });

        lo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new News2Activity()).commit();
            }
        });

        lo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new News3Activity()).commit();
            }
        });

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


        String url = "https://www.theguardian.com/international";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element = doc.select("div.fc-container__inner");

        Elements el = element.select("h3.fc-item__title");

        Element el1 = el.get(0);
        tvlv1.setText(el1.text());


        Element el2 = el.get(1);
        tvlv2.setText(el2.text());


        Element el3 = el.get(2);
        tvlv3.setText(el3.text());


        return view;
    }

}