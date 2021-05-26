package com.example.ves;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;


public class News1Activity extends Fragment {
    TextView lv;
    Intent intent;
    SpeechRecognizer mRecognizer;
    ImageButton voiceRecord;
    TextView sttResult, newscontent, question;
    final int PERMISSION = 1;
    GestureDetector gestureDetector = null;
    String selectedword;
    VocaHelper openHelper;
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.news1, container, false);

        lv = (TextView) view.findViewById(R.id.lv);
        newscontent = (TextView) view.findViewById(R.id.newscontent);
        question = (TextView) view.findViewById(R.id.question);
        openHelper = new VocaHelper(getActivity());
        db = openHelper.getWritableDatabase();

        newscontent.setMovementMethod(new ScrollingMovementMethod());

/*

        Spannable span = Spannable.Factory.getInstance().newSpannable("click");
        String text = span.toString();

        int start = text.indexOf("click");
        int end = start + "click".length();
        span.setSpan(new UnderlineSpan()
                , start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        newscontent.setText(span);
        newscontent.setHighlightColor(Color.TRANSPARENT);
        newscontent.setMovementMethod(LinkMovementMethod.getInstance());


            Element el = element.select("h3.fc-item__title").first();


*/

        String url = "https://www.theguardian.com/international";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select("div.fc-container__inner");

        Elements el = element.select("h3.fc-item__title");
        Element el1 = el.get(0);

        String url2 = el1.select("a").attr("href");

        Document doc2 = null;
        try {
            doc2 = Jsoup.connect(url2).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element2 = doc2.select("div.css-8bpe6e");
        newscontent.setText(element2.text());


        newscontent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                switch (v.getId()) {
                    case R.id.newscontent:
                        newscontent.setTextIsSelectable(true);
                        break;
                }
                return false;
            }
        });



        gestureDetector = new GestureDetector(getActivity(), new GestureListener());

        newscontent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });



        List<String> qValues = new ArrayList<>();

        qValues.add("Q : How was the article?");
        qValues.add("Q : What is the most important point in this article?");
        qValues.add("Q : What did you get from this article?");
        qValues.add("Q : What do you think about this topic?");
        qValues.add("Q : What did you feel in this article?");

        Random r = new Random();
        String randomValue = qValues.get(r.nextInt(qValues.size()));
        question.setText(randomValue);


        if ( Build.VERSION.SDK_INT >= 23 ){
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }

        sttResult = (TextView)view.findViewById(R.id.sttResult);
        voiceRecord = (ImageButton)view.findViewById(R.id.voiceRecord);
        intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getActivity().getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
        voiceRecord.setOnClickListener(v -> {
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this.getActivity());
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(intent);
        });

        registerForContextMenu(newscontent);


        return view;
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getActivity().getApplicationContext(),"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
        }
        @Override public void onBeginningOfSpeech() {}
        @Override public void onRmsChanged(float rmsdB) {}
        @Override public void onBufferReceived(byte[] buffer) {}
        @Override public void onEndOfSpeech() {}
        @Override public void onError(int error) {
            String message;
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                    default: message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getActivity().getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줍니다.
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for(int i = 0; i < matches.size() ; i++){
                sttResult.setText(matches.get(i));
            }
        }
        @Override
        public void onPartialResults(Bundle partialResults) {}

        @Override
        public void onEvent(int eventType, Bundle params) {} };


    public int getIndex(TextView view, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= view.getTotalPaddingLeft();
        y -= view.getTotalPaddingTop();

        x += view.getScrollX();
        y += view.getScrollY();

        Layout layout = view.getLayout();

        int line = layout.getLineForVertical(y);
        int offset = layout.getOffsetForHorizontal(line, x);

        return offset;
    }

    public String chooseWord(int offset){

        String strWord="";

        for(int i=offset-1;;i--){     //선택한 글자(알파벳하나)가 포함된 단어의 앞부분 받아오기

            String a=String.valueOf(newscontent.getText().charAt(i));

            if(!a.equals(" ")&&!a.equals(",") &&!a.equals("]")&&!a.equals(")")&&!a.equals("`")&&!a.equals("?")&&!a.equals(".")&&!a.equals("/")&&!a.equals(";")&&!a.equals(":")){

                strWord=a+strWord;

                //    Log.d("offset","offset "+offset);

                //    Log.d("offset","word "+strWord);

            }

            else

                break;

        }

        for(int i=offset;;i++){   //선택한 글자(알파벳하나)가 포함된 단어의 뒷부분 받아오기

            String a=String.valueOf(newscontent.getText().charAt(i));

            if(!a.equals(" ")&&!a.equals(",") &&!a.equals("]")&&!a.equals(")")&&!a.equals("`")&&!a.equals("?")&&!a.equals(".")&&!a.equals("/")&&!a.equals(";")&&!a.equals(":")){
                strWord=strWord+a;

                //    Log.d("offset","offset "+offset);

                //    Log.d("offset","word "+strWord);

            }

            else

                break;

        }

        return strWord;

    }

    private class GestureListener implements GestureDetector.OnGestureListener {

        public GestureListener() {
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
            int offset = getIndex(newscontent, motionEvent);
            System.out.println(chooseWord(offset));
            selectedword = chooseWord(offset);

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            return false;
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);


        menu.clear();
        menu.add(0, 1, 100, "단어장에 추가");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 롱클릭했을 때 나오는 context Menu 의 항목을 선택(클릭) 했을 때 호출
        switch(item.getItemId()) {
            case 1 :// 단어장에 추가 선택시
                //Toast.makeText(getContext(), selectedword, Toast.LENGTH_SHORT).show();

                /*

                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putString("word", selectedword);//번들에 넘길 값 저장
                String wordurl = "https://dic.daum.net/search.do?q=" + selectedword;
                Document worddoc = null;
                try {
                    worddoc = Jsoup.connect(wordurl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements wordelement = worddoc.select("div.card_word");
                Elements el = wordelement.select("ul.list_search");
                Element selectedwordmean = el.get(0);
                bundle.putString("wordmean", selectedwordmean.text());//번들에 넘길 값 저장
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                VocabularyActivity fragment2 = new VocabularyActivity();//프래그먼트2 선언
                fragment2.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                transaction.replace(R.id.container, fragment2);
                transaction.commit();


                 */


                String wordurl = "https://dic.daum.net/search.do?q=" + selectedword;
                Document worddoc = null;
                try {
                    worddoc = Jsoup.connect(wordurl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements wordelement = worddoc.select("div.card_word");
                Elements el = wordelement.select("ul.list_search");
                Element selectedwordmean = el.get(0);

                String eng = selectedword;
                String kor = selectedwordmean.text();


                String sql = "insert into voca(eng, kor) values('" + eng + "','" + kor + "');";
                db.execSQL(sql);
                Toast.makeText(getActivity(), "단어가 저장되었습니다.", Toast.LENGTH_SHORT).show();



                return true;
        }

        return super.onContextItemSelected(item);
    }





}

