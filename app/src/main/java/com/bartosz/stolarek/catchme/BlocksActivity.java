package com.bartosz.stolarek.catchme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BlocksActivity extends AppCompatActivity {
    Button buttonEasy, buttonMedium, buttonHard;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);

        buttonEasy = (Button) findViewById(R.id.buttonEasy);
        buttonMedium = (Button) findViewById(R.id.buttonMedium);
        buttonHard = (Button) findViewById(R.id.buttonHard);

        intent = new Intent(BlocksActivity.this, GameActivity.class);
    }

    public void startEasy(View v) {
        //startActivity(new Intent(MainActivity.this, GameActivity.class));
        intent.putExtra("level", "easy");
        startActivity(intent);
    }
    public void startMedium(View v) {
        intent.putExtra("level", "medium");
        startActivity(intent);
    }
    public void startHard(View v) {
        intent.putExtra("level", "hard");
        startActivity(intent);
    }
}