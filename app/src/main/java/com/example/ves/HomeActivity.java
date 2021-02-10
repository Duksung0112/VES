package com.example.ves;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

public class HomeActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle SavedInstanceState){

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.home);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSong = tabHost.newTabSpec("SONG").setIndicator("노래별");
        tabSong.setContent(R.id.tabSong);
        tabHost.addTab(tabSong);

        TabHost.TabSpec tabArtist = tabHost.newTabSpec("ARTIST").setIndicator("가수별");
        tabArtist.setContent(R.id.taba);
        tabHost.addTab(tabArtist);

        TabHost.TabSpec tabAlbum = tabHost.newTabSpec("ALBUM").setIndicator("이미지뷰");
        tabAlbum.setContent(R.id.tabb);
        tabHost.addTab(tabAlbum);

        TabHost.TabSpec tabWeb = tabHost.newTabSpec("WEB").setIndicator("웹");
        tabWeb.setContent(R.id.tabc);
        tabHost.addTab(tabWeb);

        tabHost.setCurrentTab(3);

    }
}
