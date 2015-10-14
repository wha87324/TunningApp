package com.example.unidentified1.newtunningapp.org.sidor.androidapps.simpletuner;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.tunningapp.R;


public class SimpleGuitarTunerActivity extends Activity {
	// switch off gc logs.
	// System.setProperty("log.tag.falvikvm", "SUPPRESS"); 
	public static final String TAG = "RealGuitarTuner";
	
	private final boolean LAUNCHANALYZER = true;

	
	private ImageView guitar = null;
	private Spinner tuningSelector = null;
	private SoundAnalyzer soundAnalyzer = null ;
	private UiController uiController = null;
	private TextView mainMessage = null;

    private final long FINAL_INTENRVAL_TIME = 2000;
    private long backPressedTime =0;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.main);
        uiController = new UiController(this);
        if(LAUNCHANALYZER) {
	        try {
	        	soundAnalyzer = new SoundAnalyzer();
	        } catch(Exception e) {
	        	Toast.makeText(this, "The are problems with your microphone :(", Toast.LENGTH_LONG ).show();
	        	Log.e(TAG, "Exception when instantiating SoundAnalyzer: " + e.getMessage());
	        }
	        soundAnalyzer.addObserver(uiController);
        }
        guitar = (ImageView)findViewById(R.id.guitar);
        mainMessage = (TextView)findViewById(R.id.mainMessage);

        Tuning.populateSpinner(this, tuningSelector);
        try {
            tuningSelector.setOnItemSelectedListener(uiController);
        }catch (NullPointerException e){

        }
    }
	
	private Map<Integer, Bitmap> preloadedImages;
	private BitmapFactory.Options bitmapOptions;
	
	private Bitmap getAndCacheBitmap(int id) {
		if(preloadedImages == null)
			preloadedImages = new HashMap<Integer,Bitmap>();
		Bitmap ret = preloadedImages.get(id);
		if(ret == null) {
			if(bitmapOptions == null) {
				BitmapFactory.Options options=new BitmapFactory.Options();
				options.inSampleSize = 4; // The higher it goes, the smaller the image.
			}
			ret = BitmapFactory.decodeResource(getResources(), id, bitmapOptions);
			preloadedImages.put(id, ret);
		}
		return ret;
	}
	
	public void dumpArray(final double [] inputArray, final int elements) {
		Log.d(TAG, "Starting File writer thread...");
		final double [] array = new double[elements];
		for(int i=0; i<elements; ++i)
			array[i] = inputArray[i];
		new Thread(new Runnable() {
			@Override
			public void run() {
				try { // catches IOException below
					// Location: /data/data/your_project_package_structure/files/samplefile.txt         
					String name = "Chart_" + (int)(Math.random()*1000) + ".data";
					FileOutputStream fOut = openFileOutput(name,
							Context.MODE_WORLD_READABLE);
					OutputStreamWriter osw = new OutputStreamWriter(fOut); 

					// Write the string to the file
					for(int i=0; i<elements; ++i) 
							osw.write("" + array[i] + "\n");
					/* ensure that everything is
					 * really written out and close */
					osw.flush();
					osw.close();
					Log.d(TAG, "Successfully dumped array in file " + name);
				} catch(Exception e) {
					Log.e(TAG,e.getMessage());
				}
			}
		}).start();
	}

	private int [] stringNumberToImageId = new int[]{
			R.mipmap.strings0,
			R.mipmap.strings1,
			R.mipmap.strings2,
			R.mipmap.strings3,
			R.mipmap.strings4,
			R.mipmap.strings5,
			R.mipmap.strings6
	};
	

	
	
    int oldString = 0;
    // 1-6 strings (ascending frequency), 0 - no string.
    public void changeString(int stringId) {
    	if(oldString!=stringId) {
    		guitar.setImageBitmap(getAndCacheBitmap(stringNumberToImageId[stringId]));
        	oldString=stringId;
    	}
    }
    
	int [] targetColor =         new int[]{160,80,40};
	int [] awayFromTargetColor = new int[]{160,160,160};
    
    public void displayMessage(String msg, boolean positiveFeedback) {
    	int textColor = positiveFeedback ? Color.rgb(34,139,34) : Color.rgb(255,36,0);
    	mainMessage.setText(msg);
    	mainMessage.setTextColor(textColor);
    }
    
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
        Log.d(TAG,"onDestroy()");

	}

	@Override
	protected void onPause() {
		super.onPause();
        Log.d(TAG,"onPause()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
        Log.d(TAG,"onRestart()");

	}

	@Override
	protected void onResume() {
		super.onResume();
        Log.d(TAG,"onResume()");
        if(soundAnalyzer!=null)
        	soundAnalyzer.ensureStarted();
	}

	@Override
	protected void onStart() {
		super.onStart();
        Log.d(TAG,"onStart()");
        if(soundAnalyzer!=null)
        	soundAnalyzer.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
        Log.d(TAG,"onStop()");
        if(soundAnalyzer!=null)
        	soundAnalyzer.stop();
	}
    @Override
    public void onBackPressed(){
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if(0 <= intervalTime && FINAL_INTENRVAL_TIME >= intervalTime){
            super.onBackPressed();
        }
        else{
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "버튼을 한번더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }

}