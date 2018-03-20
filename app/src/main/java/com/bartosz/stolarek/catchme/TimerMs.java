package com.bartosz.stolarek.catchme;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mrozuu on 21.01.2018.
 */

public class TimerMs {
    private long start;
    private int tries;
    private ArrayList<Long> results;

    public TimerMs() {
        start = 0;
        tries = 0;
        results = new ArrayList<Long>();
    }

    public void start() {
        if (start == 0) {
            start = System.currentTimeMillis();
        }
        Log.d("start", "wbiło");
    }

    public long calculateAverage() {
        long sum = 0;
        long average = 0;

        if(!results.isEmpty()) {
            for (long mark : results) {
                sum += mark;
            }

            average = (sum / results.size());
        }
        return average;
    }

    public long getBest() {
        return Collections.min(results);
    }

    public int getTries() {
        return tries;
    }

    public long elapsedTime() {
        Log.d("elapsedTime", "wbiło");
        if(start == 0) { return 0; } //You need to start it first

        long time = (System.currentTimeMillis() - start);
        start = 0; // reset start to allow you to start it again later
//        Log.d("Time: ", String.valueOf(time));
        results.add(time);
        tries++;
        return time;
    }

}
