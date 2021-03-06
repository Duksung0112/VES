package com.example.ves;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.lang.reflect.Field;

import androidx.fragment.app.Fragment;

public class DefaultsoundActivity extends Fragment {
    Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.defaultsound, container, false);

        btnSave = (Button) view.findViewById(R.id.btnSave);

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

        return view;

    }

}
