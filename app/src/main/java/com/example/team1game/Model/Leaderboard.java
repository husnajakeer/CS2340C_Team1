package com.example.team1game.Model;

import android.os.CountDownTimer;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Leaderboard {
    private volatile static Leaderboard leaderboard;
    private List<Integer> scores = new ArrayList<>();
    //private final Date dateTime;
    public static Leaderboard getLeaderboard(){
        if (leaderboard == null){
            synchronized (Leaderboard.class){
                if (leaderboard == null) {
                    leaderboard = new Leaderboard();
                }
            }
        }
        return leaderboard;
    }
    private void decreaseScoreByTime(Player player){
//        double duration = TimeUnit.MINUTES.toMillis(1);
//        //new CountDownTimer(duration, 1000).start();
//        double currentTime = System.currentTimeMillis();
//        double timeElapsed = currentTime - dateTime.getTime(); // Time elapsed in milliseconds
//        int decrease = (int) (timeElapsed / DECREASE_INTERVAL_MS) * DECREASE_AMOUNT;
//        score = Math.max(0, score - decrease); // Ensure score doesn't go below 0
    }
}
