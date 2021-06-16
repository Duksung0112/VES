package com.example.ves;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
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
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

public class News3Activity extends Fragment {
    TextView lv;
    Intent intent;
    SpeechRecognizer mRecognizer;
    ImageButton voiceRecord, imgbPlay, imgbPause, imgbStop;
    TextView sttResult, newscontent, question;
    final int PERMISSION = 1;
    GestureDetector gestureDetector = null;
    String selectedword;
    VocaHelper openHelper;
    SQLiteDatabase db;
    private TextToSpeech tts;
    private int standbyIndex = 0;
    private int lastPlayIndex = 0;
    private final Bundle params = new Bundle();
    private PlayState playState = PlayState.STOP;
    Elements element3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news3, container, false);
        lv = (TextView) view.findViewById(R.id.lv);
        newscontent = (TextView) view.findViewById(R.id.newscontent);
        question = (TextView) view.findViewById(R.id.question);
        openHelper = new VocaHelper(getActivity());
        db = openHelper.getWritableDatabase();
        imgbPlay = (ImageButton) view.findViewById(R.id.imgbPlay);
        imgbPause = (ImageButton) view.findViewById(R.id.imgbPause);
        imgbStop = (ImageButton) view.findViewById(R.id.imgbStop);

        newscontent.setMovementMethod(new ScrollingMovementMethod());


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


        String url = "https://www.theguardian.com/international";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select("div.fc-container__inner");

        Elements el = element.select("h3.fc-item__title");
        Element el1 = el.get(2);

        String url2 = el1.select("a").attr("href");

        Document doc2 = null;
        try {
            doc2 = Jsoup.connect(url2).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element2 = doc2.select("div.dcr-zs6acm");
        newscontent.setText(element2.text());

        element3 = element2.select("p.dcr-s23rjr");



        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);


        tts = new TextToSpeech(this.getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    //사용할 언어를 설정
                    int result = tts.setLanguage(Locale.ENGLISH);
                    //언어 데이터가 없거나 혹은 언어가 지원하지 않으면...
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getActivity(), "이 언어는 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        imgbPlay.setEnabled(true);
                        //음성 톤
                        tts.setPitch(0.7f);
                        //읽는 속도
                        tts.setSpeechRate(1.0f);

                    }
                } else {

                }
            }
        });

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {

            }

            @Override
            public void onDone(String s) {
                clearAll();
            }

            @Override
            public void onError(String s) {
                showState("재생 중 에러가 발생했습니다.");
            }

            @Override
            public void onRangeStart(String utteranceId, int start, int end, int frame) {
                lastPlayIndex = start;
            }
        });



        imgbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newscontent2 = element3.get(0).text() + element3.get(1).text() + element3.get(2).text(); // 전체 기사는 너무 길어서 tts 안되어서 일부만 일단 나오도록 함

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (playState.isStopping() && !tts.isSpeaking()) {
                        tts.speak(newscontent2, TextToSpeech.QUEUE_FLUSH, params, newscontent2);
                    } else if (playState.isWaiting()) {
                        standbyIndex += lastPlayIndex;
                        tts.speak(newscontent2.substring(standbyIndex), TextToSpeech.QUEUE_FLUSH, params, newscontent2.substring(standbyIndex));
                    }
                    playState = PlayState.PLAY;


                } else {

                    tts.speak(newscontent2, TextToSpeech.QUEUE_FLUSH, null);
                }

            }

        });


        imgbPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playState.isPlaying()) {
                    playState = PlayState.WAIT;
                    tts.stop();
                }

            }

        });

        imgbStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tts.stop();
                clearAll();
            }

        });








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
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");
        voiceRecord.setOnClickListener(v -> {
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this.getActivity());
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(intent);
        });

        registerForContextMenu(newscontent);


        return view;
    }



    private void clearAll() {
        playState = PlayState.STOP;
        standbyIndex = 0;
        lastPlayIndex = 0;

    }

    private void showState(final String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onPause() {
        if (playState.isPlaying()) {
            playState = PlayState.WAIT;
            tts.stop();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (playState.isWaiting()) {
            String newscontent2 = element3.get(0).text() + element3.get(1).text() + element3.get(2).text(); // 전체 기사는 너무 길어서 tts 안되어서 일부만 일단 나오도록 함

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (playState.isStopping() && !tts.isSpeaking()) {
                    tts.speak(newscontent2, TextToSpeech.QUEUE_FLUSH, params, newscontent2);
                } else if (playState.isWaiting()) {
                    standbyIndex += lastPlayIndex;
                    tts.speak(newscontent2.substring(standbyIndex), TextToSpeech.QUEUE_FLUSH, params, newscontent2.substring(standbyIndex));
                }
                playState = PlayState.PLAY;

            } else {

                tts.speak(newscontent2, TextToSpeech.QUEUE_FLUSH, null);
            }

        }
        super.onResume();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거한다.
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            //tts = null;
        }
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

    public String chooseWord(int offset) {

        Spannable span = (Spannable) newscontent.getText();

        String strWord="";


        for(int i=offset-1;;i--){     //선택한 글자(알파벳하나)가 포함된 단어의 앞부분 받아오기

            String a=String.valueOf(newscontent.getText().charAt(i));

            span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

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

            span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

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

