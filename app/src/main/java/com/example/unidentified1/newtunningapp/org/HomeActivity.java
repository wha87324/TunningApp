package com.example.unidentified1.newtunningapp.org;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unidentified1.newtunningapp.org.androidtown.tunningapp.SoundManager;

import org.androidtown.tunningapp.R;

import java.lang.reflect.Field;

/**
 * @author Adil Soomro
 *
 */
public class HomeActivity extends Activity {

    String item,item2;
    TextView textView;
    ImageView imageView;
    String codeimagename;

    int a;
    String codename;
    String[] code = {"cm","c#","db","d","d#","eb","e","f","f#","gb","g","g#","ab","a","a#","bb","b"};
    String[] code1 = {"m","6","7","9","m6","m7","m9","dim","aug","sus4","7sus4","maj7","mmaj7","add9"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        SoundManager.getInstance().Init(this); // 매니저 사용을 위한 초기화


        SoundManager.getInstance().addSound(1, R.raw.acode);
        // ArrayAdapter를 통해 LIST로 표시할 오브젝트를 지정한다.
        // 여기서는 심플하게 그냥 String
        // 레이아웃 android.R.layout.simple_list_item_1 는 안드로이드가 기본적으로 제공하는 간단한 아이템 레이아웃
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.simple_list_item_123);
        textView = (TextView)findViewById(R.id.textView5);
        imageView = (ImageView)findViewById(R.id.imageView2);
        // 아이템을 추가
        adapter.add("cm");
        adapter.add("c#");
        adapter.add("db");
        adapter.add("d");
        adapter.add("d#");
        adapter.add("eb");
        adapter.add("e");
        adapter.add("f");
        adapter.add("f#");
        adapter.add("gb");
        adapter.add("g");
        adapter.add("g#");
        adapter.add("ab");
        adapter.add("a");
        adapter.add("a#");
        adapter.add("bb");
        adapter.add("b");

        adapter1.add("m");
        adapter1.add("6");
        adapter1.add("7");
        adapter1.add("9");
        adapter1.add("m6");
        adapter1.add("m7");
        adapter1.add("m9");
        adapter1.add("dim");
        adapter1.add("aug");
        adapter1.add("sus4");
        adapter1.add("7sus4");
        adapter1.add("maj7");
        adapter1.add("mmaj7");
        adapter1.add("add9");

        // ListView 가져오기
        ListView listView = (ListView) findViewById(R.id.listView);
        ListView listView1 = (ListView) findViewById(R.id.listView2);
        // ListView에 각각의 아이템표시를 제어하는 Adapter를 설정
        listView.setAdapter(adapter);
        listView1.setAdapter(adapter1);

        // 아이템을 [클릭]시의 이벤트 리스너를 등록
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // TODO 아이템 클릭시에 구현할 내용은 여기에.
                item = (String) listView.getItemAtPosition(position);
                ItemClick(1);
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // TODO 아이템 클릭시에 구현할 내용은 여기에.
                item2 = (String) listView.getItemAtPosition(position);
                ItemClick(2);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AwesomeActivity.handler.sendEmptyMessage(0);
        super.onBackPressed();
    }
    public void ItemClick(int i) {

        if(i == 1){
            codename = item;
        }else if(i == 2){
            if(item == null){
                return;
            }else{
                codename = item + item2;
            }
        }
        textView.setText(codename);
        showImage();
    }
    public void CodeplayClick(View v){
        if(codename.equals(item)){
            for(int i=0; i< code.length; i++) {
                if (code[i].equals(codename)) {
                    SoundManager.getInstance().play(i + 1);
                }
            }
        }else{
            for(int i=0; i<code.length; i++){
                for(int i1 = 0; i1<code1.length; i1++){
                    if(codename.equals(code[i]+code1[i1])){
                        SoundManager.getInstance().play(((i + 1)*10)+(i1+1));
                    }
                }
            }
        }
    }
    private void showImage() {

        if(codename.equals(item)){
            for(int i=0; i< code.length; i++) {
                if (code[i].equals(codename)) {
                    codeimagename = code[i];
                }
            }
        }else{
            for(int i=0; i<code.length; i++){
                for(int i1 = 0; i1<code1.length; i1++){
                    if(codename.equals(code[i]+code1[i1])){
                        codeimagename = code[i] + code1[i1];
                    }
                }
            }
        }
        String uri = "drawable/" + codeimagename ;

        // int imageResource = R.drawable.icon;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        imageView.setImageDrawable(image);
    }
}