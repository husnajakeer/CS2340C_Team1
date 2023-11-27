package com.example.team1game.Model.Powerups;

import com.example.team1game.Model.Player;
import com.example.team1game.Model.BaseScreen;
import com.example.team1game.Model.PlayerDecorator;

public class AttackPowerUpDecorator implements PlayerDecorator {
    private int scoreMultiplier;
    private int timeBoost;

    public AttackPowerUpDecorator(int scoreMultiplier) {
        this.scoreMultiplier = scoreMultiplier;
    }

    @Override
    public void applyEffect(Player player) {
        BaseScreen.setScoreMultiplier(scoreMultiplier * 2);
    }
}
