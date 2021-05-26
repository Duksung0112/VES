package com.example.ves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText edtId, edtPw;
    Button btnLogin, btnJoin;
    ImageView imgVes;
    UserHelper openHelper;
    SQLiteDatabase db;

    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHelper = new UserHelper(this);
        db = openHelper.getWritableDatabase();
        edtId= (EditText) findViewById(R.id.edtId);
        edtPw = (EditText) findViewById(R.id.edtPw);
        btnLogin=(Button) findViewById(R.id.btnLogin);
        btnJoin=(Button) findViewById(R.id.btnJoin);



        btnJoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = edtId.getText().toString();
                String pw = edtPw.getText().toString();

                String sql = "select * from user where userid = '"+ userid + "' and pw = '" + pw+ "';";
                Cursor cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    String no = cursor.getString(0);
                    String rest_id = cursor.getString(1);
                    Log.d("select", "no : " + no + "\nrest_id : " + rest_id);
                }
                if(cursor.getCount() == 1) {
                    Toast.makeText(MainActivity.this, userid + "님 환영합니다", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MenumainActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }

        }) ;


        textViewResult = findViewById(R.id.text_view_result);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://203.252.219.223:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<Post>> call = retrofitAPI.getUserinfo();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for(Post post : posts){
                    String content = "";
                    content += "ID: "+post.getUserid()+"\n";
                    content += "PW: "+post.getPw()+"\n";
                    content += "Name: "+post.getUsername()+"\n";
                    content += "Type: "+post.getUsertype()+"\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });





    }
}