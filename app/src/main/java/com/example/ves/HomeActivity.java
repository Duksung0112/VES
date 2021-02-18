package com.example.ves;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class HomeActivity extends TabActivity {
    Button setPro, setInfo, setPre;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.home);

        setPro = (Button) findViewById(R.id.setPro);
        setInfo = (Button) findViewById(R.id.setInfo);
        setPre = (Button) findViewById(R.id.setPre);


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




        TabHost tabHost = getTabHost();

        ImageView imgHome = new ImageView(this);
        imgHome.setImageResource(R.drawable.ic_baseline_home_24);

        ImageView imgWord = new ImageView(this);
        imgWord.setImageResource(R.drawable.ic_baseline_menu_book_24);

        ImageView imgMic = new ImageView(this);
        imgMic.setImageResource(R.drawable.ic_baseline_mic_24);

        ImageView imgMy = new ImageView(this);
        imgMy.setImageResource(R.drawable.ic_baseline_perm_identity_24);


        TabHost.TabSpec tabHome = tabHost.newTabSpec("HOME").setIndicator(imgHome);
        tabHome.setContent(R.id.tabHome);
        tabHost.addTab(tabHome);

        TabHost.TabSpec tabWord = tabHost.newTabSpec("WORD").setIndicator(imgWord);
        tabWord.setContent(R.id.tabWord);
        tabHost.addTab(tabWord);

        TabHost.TabSpec tabMic = tabHost.newTabSpec("MIC").setIndicator(imgMic);
        tabMic.setContent(R.id.tabMic);
        tabHost.addTab(tabMic);

        TabHost.TabSpec tabMy = tabHost.newTabSpec("MY").setIndicator(imgMy);
        tabMy.setContent(R.id.tabMy);
        tabHost.addTab(tabMy);

        tabHost.setCurrentTab(0);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = (screenHeight * 15) / 200;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = (screenHeight * 15) / 200;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = (screenHeight * 15) / 200;
        tabHost.getTabWidget().getChildAt(3).getLayoutParams().height = (screenHeight * 15) / 200;

        Spinner proSpinner = (Spinner) findViewById(R.id.spPro);
        ArrayAdapter proAdapter = ArrayAdapter.createFromResource(this,
                R.array.pronounce_choice, android.R.layout.simple_spinner_item);
        proAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proSpinner.setAdapter(proAdapter);

        Spinner voiceSpinner = (Spinner) findViewById(R.id.spVoice);
        ArrayAdapter voiceAdapter = ArrayAdapter.createFromResource(this,
                R.array.voice_choice, android.R.layout.simple_spinner_item);
        voiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voiceSpinner.setAdapter(voiceAdapter);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(voiceSpinner);
            android.widget.ListPopupWindow popupWindow2 = (android.widget.ListPopupWindow) popup.get(proSpinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(600);
            popupWindow2.setHeight(600);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

         setPro.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent intent = new Intent(getApplicationContext(),DefaultsoundActivity.class);
                         startActivity(intent);
                     }
                 });

        setInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),ModifyActivity.class);
                        startActivity(intent);
                    }
                });


    }
}