package com.example.team1game.Model;

import android.graphics.Rect;
import android.widget.TextView;

public interface Observer {
    static boolean update(Rect playerRect, TextView healthPointsTextView) {
        return false;
    }
}
