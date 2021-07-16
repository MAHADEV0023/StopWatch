package org.geeksforgeeks.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;

    private boolean running;

    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {

            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }
        runTimer();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }


    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }


    public void onClickStart(View view) {
        running = true;
        Toast.makeText(this, "Stop Watch Started!", Toast.LENGTH_SHORT).show();
    }

    public void onClickStop(View view) {
        running = false;
        Toast.makeText(this, "Stop Watch Paused!", Toast.LENGTH_SHORT).show();

    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        Toast.makeText(this, "Resetting Stop Watch!!", Toast.LENGTH_SHORT).show();

    }


    private void runTimer() {

        final TextView timeView = (TextView) findViewById(R.id.time_view);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override

            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                timeView.setText(time);


                if (running) {
                    seconds++;
                }

                // Post the code again with a delay of 1 second.

                handler.postDelayed(this, 1000);
            }
        });
    }

}