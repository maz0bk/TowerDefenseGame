package com.geekbrains.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Turret {
    private GameScreen gameScreen;

    private TextureRegion texture;
    private int cellX, cellY;
    private float angle;

    private int tapCount;
    private long lastTapTime;

    public Turret(GameScreen gameScreen, TextureAtlas atlas) {
        this.gameScreen = gameScreen;
        this.texture = new TextureRegion(atlas.findRegion("turrets"), 0, 0, 80, 80);
        this.cellX = 8;
        this.cellY = 4;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, cellX * 80, cellY * 80, 40, 40, 80, 80, 1, 1, angle);
    }

    public void update(float dt) {
       // angle += 180.0 * dt;
        Vector2 monsterPosition = new Vector2(gameScreen.getMonster().getPosition());
        monsterPosition.sub(cellX*80,cellY*80);
        angle = monsterPosition.angle();
        if (Gdx.input.justTouched()) {

            if (lastTapTime == 0 && tapCount == 0) {
                lastTapTime = TimeUtils.nanoTime();
                tapCount++;
                return;
            }

            if (tapCount ==1 && TimeUtils.nanoTime() - lastTapTime < 199333333) {
                cellX = Gdx.input.getX() / 80;
                cellY = (720 - Gdx.input.getY()) / 80;

                lastTapTime = 0;
                tapCount = 0;
            }
            else {
                lastTapTime = 0;
                tapCount = 0;
            }

        }

    }
}
