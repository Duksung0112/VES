package com.example.ves;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ves.R;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class VocabularyActivity extends Fragment {

    String word, wordmean;
    VocaHelper openHelper;
    SQLiteDatabase db;

    public VocabularyActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vocabulary, container, false);
        Button quizbt = (Button)view.findViewById(R.id.quizbt);
        ListView listView = (ListView)view.findViewById(R.id.word_list);
        openHelper = new VocaHelper(getActivity());
        db = openHelper.getWritableDatabase();

        MyAdapter mMyAdapter = new MyAdapter();


        /*

        if (getArguments() != null)
        {
            word = getArguments().getString("word"); // 프래그먼트1에서 받아온 값 넣기
            wordmean = getArguments().getString("wordmean"); // 프래그먼트1에서 받아온 값 넣기

            mMyAdapter.addItem(word, wordmean);
        }

        */


        String sql = "select * from voca;";
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            
            String eng = cursor.getString(0) ;
            String kor = cursor.getString(1) ;

            mMyAdapter.addItem(eng, kor);
            
        }






        listView.setAdapter(mMyAdapter);



        Spinner proSpinner = (Spinner) view.findViewById(R.id.spPro);
        ArrayAdapter proAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.pronounce_choice, android.R.layout.simple_spinner_item);
        proAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proSpinner.setAdapter(proAdapter);

        Spinner voiceSpinner = (Spinner) view.findViewById(R.id.spVoice);
        ArrayAdapter voiceAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.voice_choice, android.R.layout.simple_spinner_item);
        voiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voiceSpinner.setAdapter(voiceAdapter);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            ListPopupWindow popupWindow = (ListPopupWindow) popup.get(voiceSpinner);
            ListPopupWindow popupWindow2 = (ListPopupWindow) popup.get(proSpinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(600);
            popupWindow2.setHeight(600);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        quizbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new QuizActivity()).commit();

            }

        });


        // Inflate the layout for this fragment
        return view;
    }

}
