package com.bartosz.stolarek.catchme;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class ReflexActivity extends AppCompatActivity implements View.OnClickListener {
    private Button readyButton, goButton;
    private TextView tvTitle, tvDescription, tvAvgText, tvTriesText;
    private int randomTime;
    private long currentTime;
    private boolean start;
    private TimerMs timer;
    private GameOverDialogW dialog;
    private Handler handler;
    private Runnable r;

    @Override
    protected void onStart() {
        super.onStart();
        timer = new TimerMs();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflex);

        //buttons
        readyButton = ((Button) findViewById(R.id.readyButton));
        goButton = ((Button) findViewById(R.id.goButton));

        //textViews
        tvTitle = ((TextView) findViewById(R.id.tvTitle));
        tvDescription = ((TextView) findViewById(R.id.tvDescription));
        tvAvgText = ((TextView) findViewById(R.id.tvAvgText));
        tvTriesText = ((TextView) findViewById(R.id.tvTriesText));
        //event listeners
        readyButton.setOnClickListener(this);
        goButton.setOnClickListener(this);

        handler = new Handler();
        //start flag
        start = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.readyButton:
                if(!start) {
                    readyClick();
                } else {
                    //error when too fast
                    tooFast();
                }
                break;
            case R.id.goButton:
                //stop game
                stopGame();
                //check tries
                checkTries();
                //check if current time is the best
                checkTime();
                break;
        }
    }
    //after clicking 'ready' button first time
    private void readyClick() {
        start = true;
        tvTitle.setText("");
        tvDescription.setText("");
        randomTime = getRandomNumberInRange(1000, 5000);
        startGame(randomTime);
    }

    //after clicking 'ready' before 'go' show
    private void tooFast() {
        handler.removeCallbacks(r);
        start = false;
        tvTitle.setText("Too fast!");
        tvDescription.setText("Try again");
    }

    //after clicking 'go' button
    private void stopGame() {
        currentTime = timer.elapsedTime();
        //calculate average of all results
        long avg = timer.calculateAverage();
        start = false;
        //change buttons
        goButton.setVisibility(View.GONE);
        readyButton.setVisibility(View.VISIBLE);
        //set average text
        tvAvgText.setText((int) avg + " ms");
    }

    //check tries
    private void checkTries() {
        int tries = timer.getTries();
        if(tries == 5) {
            Log.d("wanna try again?", "yes");
            tvTriesText.setText(tries + " of 5");
            dialog = new GameOverDialogW(this, (int) timer.getBest());
            dialog.show();
        } else {
            tvTriesText.setText(tries + " of 5");
        }
    }

    //check time
    private void checkTime() {
        if(currentTime <= timer.getBest()) {
            tvTitle.setText("Congratulations!");
            tvDescription.setText("Best time " + currentTime + " ms");
        } else {
            tvTitle.setText("Current time");
            tvDescription.setText(currentTime + " ms");
        }
    }

    //random time generator
    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    //set button to 'Go' after 'randomTime'
    private void startGame(int time) {
        handler.postDelayed(r = new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                readyButton.setVisibility(View.GONE);
                goButton.setVisibility(View.VISIBLE);
                timer.start();
            }
        }, time);
    }
}

