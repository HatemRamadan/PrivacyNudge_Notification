package com.hatem.notificationapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ToggleButton toggleButton1;
    private ToggleButton toggleButton2;
    private TextView textView;
    private int counter =0;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MThread thread = new MThread(this);
        Handler mHandler = new Handler();

        Runnable start = new Runnable() {
            @Override
            public void run() {
                thread.start();
            }
        };
        Runnable stop = new Runnable() {
            @Override
            public void run() {
                thread.interrupt();
            }
        };
        mHandler.postDelayed(start,10000);  // Starts the background thread (MThread) after 10 second from launching the app.
        mHandler.postDelayed(stop,30*60000);  // Interrupts the background thread (MThread) after 30 minutes.
        this.toggleButton1 = new ToggleButton(this);
        this.toggleButton2 = new ToggleButton(this);
        this.toggleButton1 = findViewById(R.id.toggleButton);
        this.toggleButton2 = findViewById(R.id.toggleButton2);
        this.toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                thread.switchToggled(isChecked,toggleButton2.isChecked()); // In case sound toggle value has changed
            }
        });
        this.toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                thread.switchToggled(toggleButton1.isChecked(),isChecked); // In case vibration toggle value has changed
            }
        });

        this.button = new Button(this);
        this.textView = new TextView(this);
        this.button = findViewById(R.id.button);
        this.textView = findViewById(R.id.textView3);
        this.button.setBackgroundColor(Color.argb(0,0,0,0));
        this.textView.setBackgroundColor(Color.argb(0,0,0,0));
        this.textView.setTextSize(30);

        //reading from log.txt which contains the number of nudges that have been displayed.
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if(counter<3)
                    return;
                counter=0;
                File file = new File(getFilesDir(),"log.txt");
                String line = "";
                try {
                    FileInputStream fileInputStream = new FileInputStream (file);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    line = bufferedReader.readLine();
                    fileInputStream.close();

                    bufferedReader.close();
                }
                catch(FileNotFoundException e) {
                    Log.d("FileNotFoundException", e.getMessage());
                }
                catch(IOException e) {
                    Log.d("IOException", e.getMessage());
                }
                textView.setText(line);
            }
        });
    }
}
