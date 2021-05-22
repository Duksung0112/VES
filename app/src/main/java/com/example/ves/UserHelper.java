package com.example.ves;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class UserHelper extends SQLiteOpenHelper {

    Context context;

    public UserHelper(Context context) {
        super(context, "user.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "create table user (" +
                    "userid varchar(20) not null primary key," +
                    "pw varchar(20) not null," +
                    "username varchar(20) not null," +
                    "usertype int not null default 0" +
                    ");";
            db.execSQL(sql);
            Toast.makeText(context, "[user] 테이블 생성", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
