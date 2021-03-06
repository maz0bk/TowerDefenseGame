package com.geekbrains.td;

import com.badlogic.gdx.math.Vector2;

public class Bullet implements Poolable {
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private int damage;

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public Bullet() {
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.active = false;
        this.damage = 0;
    }

    public void setup(float x, float y, float vx, float vy, int damage) {
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.active = true;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
