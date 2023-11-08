package com.example.team1game.Model;

import android.graphics.Rect;
import java.util.ArrayList;
import java.util.List;

/**
 * The Collision class is used to handle collision detection and management in a game. It allows game objects
 * to register as observers and be notified when a collision occurs, providing the means to respond to collisions.
 */
public class Collision {
    private List<Subscriber> observers = new ArrayList<>();

    /**
     * This method is called when a collision is detected. It prints a message to indicate that a collision has occurred.
     */
    public void collided() {
        System.out.println("Collide, stop movement");
    }

    /**
     * Adds an observer to the list of subscribers who are interested in collision notifications.
     *
     * @param observer The object to be added as an observer.
     */
    public void addObserver(Subscriber observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of subscribers.
     *
     * @param observer The object to be removed as an observer.
     */
    public void removeObserver(Subscriber observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers about a collision between two objects by calling their
     * `handleCollision` method.
     *
     * @param object1 The first object involved in the collision.
     * @param object2 The second object involved in the collision.
     */
    private void notifyObservers(Rect object1, Rect object2) {
        for (Subscriber observer : observers) {
            observer.handleCollision(object1, object2);
        }
    }

    /**
     * Get the list of observers registered to receive collision notifications.
     *
     * @return A list of subscribers interested in collision events.
     */
    public List<Subscriber> getObservers() {
        return observers;
    }
}
