package com.example.ves;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import androidx.fragment.app.Fragment;

public class News1Activity extends Fragment {
    TextView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news1, container, false);

        lv= (TextView) view.findViewById(R.id.lv);
        EditText newscontent = (EditText) view.findViewById(R.id.newscontent);

        String url = "https://www.theguardian.com/international";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select("div.fc-container__inner");

        Element el = element.select("h3.fc-item__title").first();

        String url2 = el.select("a").attr("href");

        Document doc2 = null;
        try {
            doc2 = Jsoup.connect(url2).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element2 = doc2.select("div.css-a6edhj");
        System.out.println(element2.text());
        newscontent.setText(element2.text());


        return view;
    }
}
