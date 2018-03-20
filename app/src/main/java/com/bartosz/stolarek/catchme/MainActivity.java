package com.bartosz.stolarek.catchme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button buttonBlocks;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBlocks = (Button) findViewById(R.id.buttonBlocks);
    }

    public void startBlocks(View v) {
        intent = new Intent(MainActivity.this, BlocksActivity.class);
        startActivity(intent);
    }

    public void startReflex(View v) {
        intent = new Intent(MainActivity.this, ReflexActivity.class);
        startActivity(intent);
    }
}
