package com.example.ves;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.fragment.app.Fragment;

public class News3Activity extends Fragment {
    TextView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.news3, container, false);

        lv= (TextView) view.findViewById(R.id.lv);
        TextView newscontent = (TextView) view.findViewById(R.id.newscontent);
        TextView question = (TextView) view.findViewById(R.id.question);

        String url = "https://www.theguardian.com/international";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select("div.fc-container__inner");

        Elements el = element.select("h3.fc-item__title");
        Element el3 = el.get(2);

        String url2 = el3.select("a").attr("href");

        Document doc2 = null;
        try {
            doc2 = Jsoup.connect(url2).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element2 = doc2.select("div.dcr-zs6acm");
        System.out.println(element2.text());
        newscontent.setText(element2.text());

        List<String> qValues = new ArrayList<>();

        qValues.add("Q : How was the article?");
        qValues.add("Q : What do you think is the most important point in this article?");
        qValues.add("Q : What did you get from this article?");
        qValues.add("Q : What do you think about this topic?");
        qValues.add("Q : What did you feel in this article?");

        Random r = new Random();
        String randomValue = qValues.get(r.nextInt(qValues.size()));
        question.setText(randomValue);

        return view;
    }
}
