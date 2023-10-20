package com.example.team1game.Model;
/**
 * grace is working on, no touchy
 */

import java.util.ArrayList;
import java.util.List;

public class Collision {
    private List<Subscriber> observers = new ArrayList<>();
    public void collided(){

        System.out.println("collide, stop movement");
    }
    public void addObserver(Subscriber observer) {
        observers.add(observer);
    }

    public void removeObserver(Subscriber observer) {
        observers.remove(observer);
    }
    private void notifyObservers(Object object1, Object object2) {
        for (Subscriber observer : observers) {
            observer.onCollision(object1, object2);
        }
    }
}
