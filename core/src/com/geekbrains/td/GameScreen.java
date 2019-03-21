package com.geekbrains.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Map map;
    private Monster monster;
    private Turret turret;

    public Monster getMonster() {
        return monster;
    }

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        TextureAtlas atlas = new TextureAtlas("images/game.pack");
        this.map = new Map("level01.map", atlas);
        this.monster = new Monster(this, atlas);
        this.turret = new Turret(this, atlas);
    }

    @Override
    public void render(float delta) {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        batch.begin();
        map.render(batch);
        monster.render(batch);
        turret.render(batch);
        batch.end();
    }

    public void update(float dt) {
        map.update(dt);
        monster.update(dt);
        turret.update(dt);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
