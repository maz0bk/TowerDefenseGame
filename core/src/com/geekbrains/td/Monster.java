package com.geekbrains.td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Monster implements Poolable {
    private GameScreen gameScreen;
    private Map map;

    private TextureRegion texture;
    private TextureRegion textureHp;
    private Vector2 position;
    private Vector2 destination;

    private Vector2 velocity;

    private int hp;
    private int hpMax;

    private boolean active;

    @Override
    public boolean isActive() {
        return active;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Monster(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.map = gameScreen.getMap();
        this.texture = Assets.getInstance().getAtlas().findRegion("monster");
        this.textureHp = Assets.getInstance().getAtlas().findRegion("monsterHp");
        this.position = new Vector2(640, 360);
        this.destination = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.hpMax = 100;
        this.hp = this.hpMax;
        this.active = false;
    }

    public void activate(float x, float y) {
        this.texture = Assets.getInstance().getAtlas().findRegion("monster");
        this.textureHp = Assets.getInstance().getAtlas().findRegion("monsterHp");
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(-100.0f, 0.0f);
        this.hpMax = 100;
        this.hp = this.hpMax;
        this.getNextPoint();
        this.active = true;
    }

    public void getNextPoint() {
        map.buildRoute(gameScreen.getMap().CASTLE_X, gameScreen.getMap().CASTLE_Y, (int) (position.x / 80), (int) (position.y / 80), destination);
        destination.scl(80, 80).add(40, 40);
    }
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 40, position.y - 40);
        batch.draw(textureHp, position.x - 40, position.y + 40 - 12, 56 * ((float)hp / hpMax), 12);
    }

    public void update(float dt) {
        velocity.set(destination).sub(position).nor().scl(100.0f);
        position.mulAdd(velocity, dt);
        if (position.dst(destination) < 2.0f) {
            getNextPoint();
        }
    }
    public boolean getDamage(int damage){
        hp-=damage;

        if(hp<=0){
            deactivate();
            return true;
        }
        return false;
    }

    public void deactivate() {
        active = false;
    }
}
