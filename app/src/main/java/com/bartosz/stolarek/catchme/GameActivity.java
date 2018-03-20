package com.bartosz.stolarek.catchme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView pointsText, pointsLabel;
    Boolean playing;
    Integer activeBlock, points, miliseconds, allBlocks, oldNumber;
    CountDownTimer timer;
    GameOverDialog dialog;
    Intent intent;
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/score");
    TextView highScoreView;
    String highScore;
    String scoreEasy;
    String scoreMedium;
    String scoreHard;

    public static final String EASY_KEY = "easy";
    public static final String MEDIUM_KEY = "medium";
    public static final String HARD_KEY = "hard";
    public static final String QUOTE_KEY = "quote";
    private List<Button> buttons;
    private static final int[] BUTTON_IDS = {
            R.id.button0,
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button10,
            R.id.button11,
            R.id.button12,
            R.id.button13,
            R.id.button14,
            R.id.button15,
            R.id.button16,
            R.id.button17,
            R.id.button18,
            R.id.button19,
            R.id.button20,
            R.id.button21,
            R.id.button22,
            R.id.button23,
            R.id.button24
    };

    protected void onStart(){
        super.onStart();
        mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    highScoreView = (TextView) findViewById(R.id.topPointsText);
                    scoreEasy =  documentSnapshot.getString(EASY_KEY);
                    scoreMedium =  documentSnapshot.getString(MEDIUM_KEY);
                    scoreHard =  documentSnapshot.getString(HARD_KEY);
                    if (intent.getStringExtra("level").equals("easy")){
                        highScore = documentSnapshot.getString(EASY_KEY);
                        highScoreView.setText(highScore);
                    }
                    else  if (intent.getStringExtra("level").equals("medium")){
                        highScore = documentSnapshot.getString(MEDIUM_KEY);
                        highScoreView.setText(highScore);
                    }
                    else if (intent.getStringExtra("level").equals("hard")){
                        highScore = documentSnapshot.getString(HARD_KEY);
                        highScoreView.setText(highScore);
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        highScoreView = (TextView) findViewById(R.id.topPointsText);
        intent = getIntent();

        if (intent.getStringExtra("level").equals("easy")) {
            setContentView(R.layout.game_easy);
            allBlocks = 9;
        }
        else if (intent.getStringExtra("level").equals("medium")) {
            setContentView(R.layout.game_medium);
            allBlocks = 16;
        }
        else if (intent.getStringExtra("level").equals("hard")) {
            setContentView(R.layout.game_hard);
            allBlocks = 25;
        }
        else {
            setContentView(R.layout.game_easy);
            allBlocks = 9;
        }

        miliseconds = 2000;
        points = 0;
        pointsText = (TextView) findViewById(R.id.pointsText);
        pointsLabel = (TextView) findViewById(R.id.pointsLabel);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(miliseconds);
        playing = true;
        buttons = new ArrayList<Button>();

        randomNumber();

        timer = new CountDownTimer(miliseconds, 100) {
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int)millisUntilFinished);
            }
            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    public void clickedBlock(View v) {
        if (String.valueOf(v.getTag()).equals(activeBlock.toString())) {
            setTimer();
            points++;
            pointsText.setText(points.toString());
            randomNumber();
        }
        else {
            gameOver();
        }
    }

    private void randomNumber() {
        Random rand = new Random();
        oldNumber = activeBlock;

        while (oldNumber == activeBlock)
            activeBlock = rand.nextInt(allBlocks);

        setBlocks();
    }

    private void setTimer() {
        miliseconds = 900;

        timer.cancel();
        timer = new CountDownTimer(miliseconds, 100) {
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int)millisUntilFinished);
            }
            public void onFinish() {
                gameOver();
            }
        }.start();
        progressBar.setMax(miliseconds);
    }

    private void setBlocks() {
        int i = 0;
        for(int id : BUTTON_IDS) {
            if (i < allBlocks) {
                Button button = (Button)findViewById(id);
                if (i == activeBlock)
                    button.setBackgroundColor(Color.rgb(255, 64, 129));
                else
                    button.setBackgroundColor(Color.rgb(51, 51, 51));
            }
            i++;
        }
    }

    private void disableBlocks() {
        int i = 0;
        for(int id : BUTTON_IDS) {
            if (i < allBlocks) {
                Button button = (Button)findViewById(id);
                button.setBackgroundColor(Color.rgb(51, 51, 51));
                button.setEnabled(false);
            }
            i++;
        }
    }

    public void saveScore() {

        if (points <= Integer.parseInt(highScore))
        {
            return;
        }

        Map<String, Object> dataToSave = new HashMap<String, Object>();
        if(intent.getStringExtra("level").equals("easy")){
            dataToSave.put(EASY_KEY, points.toString());
            dataToSave.put(MEDIUM_KEY, scoreMedium);
            dataToSave.put(HARD_KEY, scoreHard);
        }
        else if(intent.getStringExtra("level").equals("medium")){
            dataToSave.put(EASY_KEY, scoreEasy);
            dataToSave.put(MEDIUM_KEY, points.toString());
            dataToSave.put(HARD_KEY, scoreHard);
        }
        else if(intent.getStringExtra("level").equals("hard")){
            dataToSave.put(EASY_KEY, scoreEasy);
            dataToSave.put(MEDIUM_KEY, scoreMedium);
            dataToSave.put(HARD_KEY, points.toString());
        }
        else {return;}
        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {}
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {}
        });
    }

    private void gameOver() {
        playing = false;
        timer.cancel();
        disableBlocks();
        saveScore();
        dialog = new GameOverDialog(this, points);
        dialog.show();
    }
}
