package com.example.ves;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class VocabularyActivity extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary);


        ListView listView = (ListView) findViewById(R.id.word_list);
        final ArrayList<String> wordList = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)

            {

                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                tv.setTextColor(Color.BLACK);

                return view;

            }


        };
        listView.setAdapter(adapter);

        wordList.add("사과");
        wordList.add("배");
        wordList.add("사과");
        wordList.add("배");
        wordList.add("사과");
        wordList.add("사과");
        wordList.add("사과");
        wordList.add("사과");
        wordList.add("사과");
        wordList.add("사과");
        wordList.add("배");
        wordList.add("배");
        wordList.add("배");
        wordList.add("배");
        wordList.add("배");
        wordList.add("배");
        wordList.add("사과");
        wordList.add("사과");
        wordList.add("사과");
        wordList.add("사과");
        wordList.add("배");
        wordList.add("배");
        wordList.add("배");
        wordList.add("배");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menumain, container, false);


    }

}