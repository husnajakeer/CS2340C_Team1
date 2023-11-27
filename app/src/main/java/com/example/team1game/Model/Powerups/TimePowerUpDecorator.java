package com.example.team1game.Model.Powerups;
import com.example.team1game.Model.Player;
import com.example.team1game.Model.PlayerDecorator;

public class TimePowerUpDecorator implements PlayerDecorator {
    private int score;
    private int timeBoost;

    public TimePowerUpDecorator(int score, int timeBoost) {
        this.score = score;
        this.timeBoost = timeBoost;
    }

    @Override
    public void applyEffect(Player player) {
        player.setScore(score + timeBoost);
    }
}