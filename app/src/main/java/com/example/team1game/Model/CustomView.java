package com.example.team1game.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

/**
 * The CustomView class represents a custom view
 * used to draw and interact with a square shape on an Android canvas.
 * It provides methods for handling touch
 * events and drawing the square on the canvas.
 */
public class CustomView extends View {
    private Rect squareRect; // This is the square's bounding rectangle
    private int x;
    private int y;
    private int rectHeight;
    private int rectWidth;

    /**
     * Constructs a CustomView object with the specified context.
     *
     * @param context The context of the Android application.
     */
    public CustomView(Context context) {
        super(context);
        // Initialize the squareRect with the coordinates of the square
        squareRect = new Rect(x, y, x + rectWidth, y + rectHeight);
    }

    // /**
    //  * Handles touch events for the square. This method is called
    //  when a touch event occurs on the view.
    //  *
    //  * @param event The MotionEvent representing the touch event.
    //  * @return True if the touch event is handled, false otherwise.
    //  */
    // @Override
    // public boolean onTouchEvent(MotionEvent event) {
    //     int x = (int) event.getX();
    //     int y = (int) event.getY();
    //
    //     if (event.getAction() == MotionEvent.ACTION_DOWN) {
    //         if (squareRect.contains(x, y)) {
    //             // The tap event occurred within the square's coordinates
    //             // You can perform actions specific to the square here
    //             Log.d("SquareTap", "Square tapped at coordinates: (" + x + ", " + y + ")");
    //         }
    //     }
    //
    //     return true; // Return true to indicate that you've handled the touch event
    // }

    /**
     * This method is called to draw the square on the canvas.
     *
     * @param canvas The canvas on which the square is to be drawn.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the square on the canvas based on squareRect coordinates
        // ...
    }
}
