package com.example.ves;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTabHost;

public class MenuActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menumain);
        mTabHost=(FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Tab1",null),
                HomeActivity.class,null);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Tab2",null),
                VocabularyActivity.class,null);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Tab3",null),
                RecordedanswerActivity.class,null);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Tab4",null),
                MypageActivity.class,null);


    }
}