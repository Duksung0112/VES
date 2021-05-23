package com.example.ves;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class VocaHelper extends SQLiteOpenHelper {

    Context context;

    public VocaHelper(Context context) {
        super(context, "voca.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "create table voca (" +
                    "eng varchar(20) not null," +
                    "kor varchar(100) not null" +
                    ");";
            db.execSQL(sql);
            Toast.makeText(context, "[voca] 테이블 생성", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
