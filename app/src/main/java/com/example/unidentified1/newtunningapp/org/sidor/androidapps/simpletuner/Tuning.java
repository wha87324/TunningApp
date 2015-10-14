package com.example.unidentified1.newtunningapp.org.sidor.androidapps.simpletuner;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Tuning {
	public static final String TAG = "RealGuitarTuner";

	private static class TuningType {
		public String humanReadableName;
		public double [] freqs;
		public String [] stringNames;
		public TuningType(String name, double [] f, String [] sn) {
			humanReadableName = name;
			freqs = f;
			stringNames = sn;
		}
	}
	
	private static TuningType [] tuningTypes = new TuningType[]{
		new TuningType("Standard",
				new double[]{82.41, 110.00, 146.83, 196.00, 246.94, 329.63},
				new String[]{"E","A","D","G","B","E"}) ,
	};
	
	public static void populateSpinner(Activity parent, Spinner s) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(parent, 
				android.R.layout.simple_spinner_item);
		for(int i=0; i<tuningTypes.length; ++i) {
			String label=tuningTypes[i].humanReadableName + " (";
			for(int j=0; j<tuningTypes[i].stringNames.length; ++j) {
				label+=tuningTypes[i].stringNames[j] + 
				((j==tuningTypes[i].stringNames.length -1) ? ")": ",");
			}
			adapter.add(label);
		}
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try {
            s.setAdapter(adapter);
        }catch (NullPointerException e){

        }
	}
	
	public class GuitarString {
		public int stringId; // no of string in the order of ascending frequency
		public double minFreq;
		public double maxFreq;
		public double freq;
		public String name;
		public GuitarString(int s,double f, double mif, double maf, String n) {
			stringId=s;
			freq=f;
			minFreq=mif;
			maxFreq=maf;
			name=n;
		}
	}
	private final GuitarString zeroString = new GuitarString(0,0.0,0.0,0.0,"0");
	private GuitarString [] strings;
	private String humanReadableName;
	private int tuningId = 0;
	
	public int getTuningId() {
		return tuningId;
	}
	public void initStrings(double [] freqs, String [] names) {
		strings = new GuitarString[freqs.length];
		for(int i=0; i<freqs.length; ++i) {
			double ldist = (i==0) ? 0.75*(2*freqs[i]-(freqs[i]+freqs[i+1])/2) 
					              : (freqs[i]+freqs[i-1])/2;
			double rdist = (i==freqs.length-1) ? 1.5*(2*freqs[i] - (freqs[i]+freqs[i-1])/2)
					                           : (freqs[i]+freqs[i+1])/2;
			//Log.e(TAG, "" + freqs[i] + ": " + (ldist) + " " + rdist);
			strings[i]=new GuitarString(i+1,freqs[i],ldist,rdist, names[i]);
		}
	}
/*
	private void outputStringsFrequencies() {
		for(int i=0; i<strings.length; ++i) {
			Log.d(TAG, strings[i].name + ": " + strings[i].freq + " e [" + 
					strings[i].minFreq + "," + strings[i].maxFreq + "]");
		}
	}
*/
	public Tuning(int tuningNumber) {
		initStrings(tuningTypes[tuningNumber].freqs,
				    tuningTypes[tuningNumber].stringNames);
		humanReadableName = tuningTypes[tuningNumber].humanReadableName;
		tuningId = tuningNumber;
		//outputStringsFrequencies();
	}
	
	public String getName() {
		return humanReadableName;
	}
	
	GuitarString getString(double frequency) {
		for(int i=0; i<strings.length; ++i) {
			if(strings[i].minFreq <=frequency && frequency<=strings[i].maxFreq)
				return strings[i];
		}
		return zeroString;
	}
}
