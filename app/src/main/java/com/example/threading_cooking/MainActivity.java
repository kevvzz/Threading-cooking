package com.example.threading_cooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button startCooking, add;
    TextView slicing, broth;
    private volatile boolean stopThreadFlag = false;

    private Handler mainHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startCooking = findViewById(R.id.cookingButton);
        slicing = findViewById(R.id.sliceCooking);
        broth = findViewById(R.id.brothCooking);
        add = findViewById(R.id.button2);

        add.setVisibility(View.INVISIBLE);
        startCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startThread(20);
            }
        });

    }



    public void startThread(int seconds) {

//        for(int i =0; i< seconds; i++){
//            Log.d("THREAD ACTIVITY", "Start Thread : " + i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        GraduationThread thread = new GraduationThread(10);
//        thread.start();

        GraduationRunnable runnable = new GraduationRunnable(seconds);
        new Thread(runnable).start();


    }

    public void stopThread() {
        stopThreadFlag = true;
    }

//    class GraduationThread extends Thread
//    {
//        int seconds;
//
//        //Non-Default Constructor
//        GraduationThread(int seconds){
//            this.seconds = seconds;
//        }
//        @Override
//        public void run(){
//            for(int i =0; i< seconds; i++){
//                Log.d("THREAD ACTIVITY", "Start Thread : " + i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    class GraduationRunnable implements Runnable
    {
        int seconds;

        //Non-Default Constructor
        GraduationRunnable(int seconds){
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for(int i =0; i< seconds; i++){
                if(stopThreadFlag)
                {
                    return;
                }
                if(i == 1) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            slicing.setText("Start Slicing some ingredients!");
                            broth.setText("Start making broth of the chicken chickens!");
                        }
                    });

                }else if(i == 4) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            slicing.setText("Adding all sliced ingredients to the broth!");

                        }
                    });

                }else if(i == 8) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            broth.setText("Please Add the seasonings!");
                            add.setVisibility(View.VISIBLE);
                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    broth.setText(" added up some seasonings");
                                }
                            });

                        }
                    });

                }else if(i == 13) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Tuscan Ravioli Soup has been served", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                Log.d("THREAD ACTIVITY", "Start Thread : " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}