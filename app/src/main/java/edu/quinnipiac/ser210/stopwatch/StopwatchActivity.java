package edu.quinnipiac.ser210.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;
import android.os.Handler;
import android.widget.TextView;


public class StopwatchActivity extends Activity {

    private int seconds = 0; //Time that gets displayed to TextView
    private boolean running; //State of timer (running, not running)
    private boolean wasRunning; //Records if stopwatch was running before onStop() was called

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        //If bundle contains name/value pairs, retrieve values
        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds"); //Call value by name ("seconds")
            running = savedInstanceState.getBoolean("running"); //Call value by name ("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer(); //When activity is created, the stopwatch updates too
    }

    //Add name/value pairs to bundle before app destroyed (onDestroy() gets called)
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    /*
    Don't need onStop() and onStart() anymore. Just onPause() and onResume()

//    //Stop the stopwatching when app is not visible
//    public void onStop() {
//        super.onStop();
//        wasRunning = running; //record if stopwatch was running when onStop() was called
//        running = false;
//    }
//
//    //If stopwatch was running, set it back to running
//    public void onStart() {
//        super.onStart();
//        if(wasRunning) {
//            running = true;
//        }
//    }
      */

    //If activity is paused, stop the stopwatch
    protected void onPause() {
        super.onPause();
        wasRunning = running; //record if stopwatch was running when onStop() was called
        running = false;
    }

    //If activity resumed, start stopwatch again if it was running previously
    protected void onResume() {
        super.onResume();
        if(wasRunning) {
            running = true;
        }
    }

    //Start the stopwatch running when the Start button is clicked
    public void onClickStart(View view) {
        running = true;
    }

    //Stop the stopwatch from running when Stop button is clicked
    public void onClickStop(View view) {
        running = false;
    }

    //Reset the stopwatch when the Reset button is clicked
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    public void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%60)/60;
                int secs = seconds%60;
                //Format the seconds into "hours:minutes:seconds"
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time); //Sets the TextView text
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });

    }

}
