package com.example.sdtu_project;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int progressStatus = 0;
    private Handler handler = new Handler();


    ProgressBar pb;
    public static String smartPark_username= "";
    public static String smartPark_password="";
    public static String smartPark_userid="";
    public static String smartPark_balance="0";
    public static String smartPark_license="";
    public static String smartPark_cnicNo="";
    public static String smartPark_address="";

    public static String smartPark_contactNo="";
    public static String smartPark_joinDate="";
    public static String smartPark_SlotNo="";

    public static int smartPark_slono=0;



    // public static String UrlAddress="http://scllhike.000webhostapp.com/smartpark/android/";
    public static String UrlAddress="http://192.168.10.4/fyp/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main);



        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 5;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            pb.setProgress(progressStatus);
                            //  textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent t= new Intent(MainActivity.this,Login.class);
                startActivity(t);
            }


        }).start();
    }


    public static void create_delay( final int dd){

        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(dd);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }
}
