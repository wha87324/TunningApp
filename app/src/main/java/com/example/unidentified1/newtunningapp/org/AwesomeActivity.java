package com.example.unidentified1.newtunningapp.org;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.unidentified1.newtunningapp.org.sidor.androidapps.simpletuner.SimpleGuitarTunerActivity;

import org.androidtown.tunningapp.R;

/**
 * @author Adil Soomro
 *
 */
@SuppressWarnings("deprecation")
public class AwesomeActivity extends TabActivity {
	TabHost tabHost;
	/** Called when the activity is first created. */
	public static Handler handler;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		tabHost = getTabHost();
		setTabs();

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				tabHost.setCurrentTab(0);
			}
		};
	}
	private void setTabs()
	{
		addTab("Home", R.drawable.tab_home,SimpleGuitarTunerActivity.class);
		addTab("Search", R.drawable.tab_search, SearchActivity.class);
		addTab("Fake", R.drawable.tab_search, OptionActivity.class);
	}
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}