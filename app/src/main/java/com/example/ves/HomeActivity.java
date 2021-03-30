package com.example.ves;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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


        String url = "https://sports.news.naver.com/wfootball/index.nhn";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select("div.home_news");
        //String title = element.select("h2").text().substring(0,4);
        //System.out.println("스포츠 "+title);
        //System.out.println("===========================");
        for (Element el : element.select("li")) {
            System.out.println(el.text());
            tvlv1.setText(el.text());
        }
        //System.out.println("===========================");


        return view;
    }

}