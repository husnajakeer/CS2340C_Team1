package com.example.team1game.Model.Powerups;

import com.example.team1game.Model.Player;
import com.example.team1game.Model.BaseScreen;
import com.example.team1game.Model.PlayerDecorator;

public class ScorePowerUpDecorator implements PlayerDecorator {
    private int scoreBonus;

    public ScorePowerUpDecorator(int scoreBonus) {
        this.scoreBonus = scoreBonus;
    }

    @Override
    public void applyEffect(Player player) {
        BaseScreen.score += scoreBonus;
    }
}
