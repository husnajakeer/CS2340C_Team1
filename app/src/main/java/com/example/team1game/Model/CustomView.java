package com.example.team1game.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

public class CustomView extends View {
    private Rect squareRect; // This is the square's bounding rectangle
    private int x;
    private int y;
    private int rectHeight;
    private int rectWidth;

    public CustomView(Context context) {
        super(context);
        // Initialize the squareRect with the coordinates of the square
        squareRect = new Rect(x, y, x + rectWidth, y + rectHeight);
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (squareRect.contains(x, y)) {
                // The tap event occurred within the square's coordinates
                // You can perform actions specific to the square here
                Log.d("SquareTap", "Square tapped at coordinates: (" + x + ", " + y + ")");
            }
        }

        return true; // Return true to indicate that you've handled the touch event
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the square on the canvas based on squareRect coordinates
        // ...
    }
}
