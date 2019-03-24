package com.geekbrains.td;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TowerDefenseGame extends Game {
    // Домашнее задание:
    // - Распишите для себя в коде комментарии, что тут происходит (ParticleEmitter можете пропустить)
    // - Если возникают вопросы: обязательно задавайте их или в домашке, или в телеграме

    private SpriteBatch batch;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        ScreenManager.getInstance().init(this, batch);
        ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        getScreen().render(dt);//???
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
