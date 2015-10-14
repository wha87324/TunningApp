package com.example.unidentified1.newtunningapp.org.sidor.androidapps.simpletuner;

import android.view.View;
import android.widget.AdapterView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by unidentified1 on 2015-08-24.
 */
public class Controller implements Observer, AdapterView.OnItemSelectedListener {

    private double frequency;

    @Override
    public void update(Observable observable, Object obj) {
        if(obj instanceof SoundAnalyzer.AnalyzedSound) {
            SoundAnalyzer.AnalyzedSound result = (SoundAnalyzer.AnalyzedSound) obj;
            frequency = FrequencySmoothener.getSmoothFrequency(result);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
