package com.geekbrains.td;

public class Player {
    private int gold;
    private int hp;

    public Player() {
        this.hp = 500;
    }

    public void addGold(int gold){
        this.gold+=gold;
    }
    public void getDamage(int damage){
        hp-=damage;
    }

    public int getGold() {
        return gold;
    }

    public int getHP() {
        return hp;
    }
}
