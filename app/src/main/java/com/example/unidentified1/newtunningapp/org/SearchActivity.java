package com.example.unidentified1.newtunningapp.org;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.example.unidentified1.newtunningapp.org.androidtown.tunningapp.STTActivity;

import org.androidtown.tunningapp.R;

/**
 * @author Adil Soomro
 *
 */
public class SearchActivity extends Activity {



    private AlertDialog mDialog = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_actvity);


    }

    @Override
    public void onResume() {
        mDialog = createInflaterDialog();
        mDialog.show();
        super.onResume();
    }

    private AlertDialog createInflaterDialog() {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);


        ab.setPositiveButton("터치 모드", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(SearchActivity.this , HomeActivity.class);
                startActivity(intent);
            }
        });

        ab.setNegativeButton("음성인식 모드", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(SearchActivity.this , STTActivity.class);
                startActivity(intent);
            }
        });

        return ab.create();
    }

    /**
     * 다이얼로그 종료
     * @param dialog
     */
    private void setDismiss(Dialog dialog){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

}
