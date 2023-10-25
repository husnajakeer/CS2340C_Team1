package com.example.team1game.Model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Grid {
    private int numRows = 32;
    private int numColumns = 48;
    private int[][] grid = new int[numRows][numColumns];

    public void grid() {
        // Fill the grid with zeros
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                grid[i][j] = 0;
            }
        }
    }
    public void drawGrid(Canvas canvas, Paint paint) {
        int cellWidth = canvas.getWidth() / numColumns;
        int cellHeight = canvas.getHeight() / numRows;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (grid[i][j] == 1) {
                    // Fill the cell with a rectangle using drawRect()
                    int left = j * cellWidth;
                    int top = i * cellHeight;
                    int right = left + cellWidth;
                    int bottom = top + cellHeight;
                    canvas.drawRect(left, top, right, bottom, paint);
                }
            }
        }
    }
    private void unWalkable() {

    }

}
