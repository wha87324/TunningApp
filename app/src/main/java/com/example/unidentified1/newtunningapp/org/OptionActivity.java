package com.example.unidentified1.newtunningapp.org;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidtown.tunningapp.R;

public class OptionActivity extends Activity {
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item1);
        ListView listView = (ListView) findViewById(R.id.listView);
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
        listView.setAdapter(adapter);
        // 아이템을 [클릭]시의 이벤트 리스너를 등록
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // TODO 아이템 클릭시에 구현할 내용은 여기에.
                item = (String) listView.getItemAtPosition(position);

            }
        });
    }
}
