package com.example.unidentified1.newtunningapp.org.androidtown.tunningapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;


import com.example.unidentified1.newtunningapp.org.AwesomeActivity;
import com.example.unidentified1.newtunningapp.org.SearchActivity;

import org.androidtown.tunningapp.R;

import java.util.ArrayList;

public class STTActivity extends AppCompatActivity {

    //인텐트, SpeechRecognizer,텍스트뷰 삽입
    Intent i;
    SpeechRecognizer mRecognizer;
    TextView textView;

    String[] code = {"C","D","E","F","G","A","B","a"};
    String[] code2 = {"7","#","m","m7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity);

        SoundManager.getInstance().Init(this); // 매니저 사용을 위한 초기화


        SoundManager.getInstance().addSound(1, R.raw.acode);
        SoundManager.getInstance().addSound(2, R.raw.bcode);
        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //인텐트 생성
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName()); //호출한 패키지
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US"); //음성인식 언어 설정

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this); //음성인식 개체
        mRecognizer.setRecognitionListener(listener); //음성인식 리스너

        textView = (TextView) findViewById(R.id.textView);

    }

    //음성인식 시작 버튼
    public void onButtonClicked(View v) {
        Toast.makeText(getApplicationContext(), "음성인식이 시작되었습니다 말해주세요.", Toast.LENGTH_LONG).show(); //시작되면 Toast를 이용해 보여줌
        mRecognizer.startListening(i); //음성인식 시작


    }

    //음성인식 리스너
    private RecognitionListener listener = new RecognitionListener() {

        //음성 인식 준비가 되었으면
        @Override
        public void onReadyForSpeech(Bundle params) {
        }

        //입력이 시작되면
        @Override
        public void onBeginningOfSpeech() {
        }

        //입력 소리 변경 시
        @Override
        public void onRmsChanged(float rmsdB) {
        }

        //더 많은 소리를 받을 때
        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        //음성 입력이 끝났으면
        @Override
        public void onEndOfSpeech() {

        }

        //에러가 발생하면
        @Override
        public void onError(int error) {

            if (error == 7 || error ==6 || error ==8 ) { //7번 오류가 뜨면
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                    mRecognizer.stopListening();
                        mRecognizer.startListening(i);//입력 다시 시작
                    }
                }, 300); //0.3초 마다
            }
            Log.e("SpeechRecognizer", error + ""); //에러 로그
        }

        //음성 인식 결과 받음
        @Override
        public void onResults(Bundle results) {
            ///
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            textView.setText(rs[0]);
            setString(rs[0]);
            new Handler().postDelayed(new Runnable() { //초마다 다시 시작하기 위해
                @Override
                public void run() {
                    mRecognizer.stopListening(); //음성입력 멈춤
                    mRecognizer.startListening(i); //음성입력 시작
                }
            }, 300); //0.3초마다 다시


            ///
        }

        //인식 결과의 일부가 유효할 때
        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        //미래의 이벤트를 추가하기 위해 미리 예약되어진 함수
        @Override
        public void onEvent(int eventType, Bundle params) {
        }


    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void setString(String str){
        String equals = str;
        for(int i=0; i< code.length; i++){
            if(code[i].equals(equals)){
                if(i == 7){
                    SoundManager.getInstance().play(1);
                }
            }
        }
    }
    public void stringEquals(){

    }

    @Override
    public void onBackPressed() {
        AwesomeActivity.handler.sendEmptyMessage(0);
        super.onBackPressed();
    }
}
