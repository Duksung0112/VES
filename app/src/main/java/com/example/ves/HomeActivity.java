package com.example.ves;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

public class HomeActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle SavedInstanceState){

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.home);

        TabHost tabHost = getTabHost();

        ImageView imgHome = new ImageView(this);
        imgHome.setImageResource(R.drawable.ic_baseline_home_24);

        ImageView imgWord = new ImageView(this);
        imgWord.setImageResource(R.drawable.ic_baseline_menu_book_24);

        ImageView imgMic = new ImageView(this);
        imgMic.setImageResource(R.drawable.ic_baseline_mic_24);

        ImageView imgMy = new ImageView(this);
        imgMy.setImageResource(R.drawable.ic_baseline_perm_identity_24);

        TabHost.TabSpec tabSong = tabHost.newTabSpec("SONG").setIndicator(imgHome);
        tabSong.setContent(R.id.tabSong);
        tabHost.addTab(tabSong);

        TabHost.TabSpec tabArtist = tabHost.newTabSpec("ARTIST").setIndicator(imgWord);
        tabArtist.setContegint(R.id.taba);
        tabHost.addTab(tabArtist);

        TabHost.TabSpec tabAlbum = tabHost.newTabSpec("ALBUM").setIndicator(imgMic);
        tabAlbum.setContent(R.id.tabb);
        tabHost.addTab(tabAlbum);

        TabHost.TabSpec tabWeb = tabHost.newTabSpec("WEB").setIndicator(imgMy);

        tabWeb.setContent(R.id.tabc);
        tabHost.addTab(tabWeb);

        tabHost.setCurrentTab(3);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = (screenHeight * 15)/200;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = (screenHeight * 15)/200;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = (screenHeight * 15)/200;
        tabHost.getTabWidget().getChildAt(3).getLayoutParams().height = (screenHeight * 15)/200; 


    }
}
