package com.geekbrains.td;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private int gold;
    private int hp;
    private int hpMax;

    public Player() {
        this.hpMax = 500;
        this.hp = 500;
        this.gold = 500;
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

    public boolean isMoneyEnough(int i) {
        return gold >= i;
    }

    public int getHpMax() {
        return hpMax;
    }

    public void renderInfo(SpriteBatch batch, BitmapFont font24) {
        font24.draw(batch, "SCORE: " + gold, 20, 700);
        font24.draw(batch, "HEALTH: " + hp+"/"+hpMax, 150, 700);
    }
}
