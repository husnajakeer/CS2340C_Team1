package com.example.team1game.Model;

import android.graphics.Rect;

public interface Observer {
    static boolean update(Rect playerRect, Rect enemyRect) {
        return false;
    }
}