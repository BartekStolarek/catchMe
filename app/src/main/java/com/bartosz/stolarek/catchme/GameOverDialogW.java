package com.bartosz.stolarek.catchme;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mrozuu on 21.01.2018.
 */

public class GameOverDialogW extends Dialog implements
        android.view.View.OnClickListener {
    public Activity activity;
    public Dialog dialog;
    public Button yes, no;
    public TextView textPoints;
    public int time;

    public GameOverDialogW(Activity a, int time) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.time = time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_over_w);

        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        textPoints = (TextView) findViewById(R.id.textPoints);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        textPoints.setText("Your best time " + time + " ms");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                activity.recreate();
                break;
            case R.id.btn_no:
                activity.finish();
                break;
            default:
                dismiss();
                activity.finish();
                break;
        }
        dismiss();
    }
}