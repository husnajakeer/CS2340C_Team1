package com.example.team1game.Model.Powerups;

import com.example.team1game.Model.Player;
import com.example.team1game.Model.PlayerDecorator;
import com.example.team1game.Model.BaseScreen;

public class HealthPowerUpDecorator implements PlayerDecorator {
    private int hearts;
    private BaseScreen baseScreen;

    public HealthPowerUpDecorator(int hearts, BaseScreen baseScreen) {
        this.hearts = hearts;
        this.baseScreen = baseScreen;
    }

    @Override
    public void applyEffect(Player player) {
        baseScreen.setNumOfHearts(hearts + 20);
    }
}
