package com.example.ves;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ves.R;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class VocabularyActivity extends Fragment {

    public VocabularyActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vocabulary, container, false);

        Button quizbt = (Button)view.findViewById(R.id.quizbt);

        ListView listView = (ListView)view.findViewById(R.id.word_list);
        final ArrayList<String> wordList = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, wordList)
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(voiceSpinner);
            android.widget.ListPopupWindow popupWindow2 = (android.widget.ListPopupWindow) popup.get(proSpinner);

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
