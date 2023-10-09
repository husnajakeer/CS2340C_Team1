package com.example.team1game.Model;

public class Score {
    private double scoreTime = 0;
    private double scoreDmgDone = 0;
    private double scoreMissedAttacks = 0;
    public void increaseScore() {

    }
    public void decreaseScore() {

    }
    // Getter method for scoreTime
    public double getScoreTime() {
        return scoreTime;
    }

    // Setter method for scoreTime
    public void setScoreTime(double scoreTime) {
        this.scoreTime = scoreTime;
    }

    // Getter method for scoreDmgDone
    public double getScoreDmgDone() {
        return scoreDmgDone;
    }

    // Setter method for scoreDmgDone
    public void setScoreDmgDone(double scoreDmgDone) {
        this.scoreDmgDone = scoreDmgDone;
    }

    // Getter method for scoreMissedAttacks
    public double getScoreMissedAttacks() {
        return scoreMissedAttacks;
    }

    // Setter method for scoreMissedAttacks
    public void setScoreMissedAttacks(double scoreMissedAttacks) {
        this.scoreMissedAttacks = scoreMissedAttacks;
    }
}