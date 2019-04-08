package com.geekbrains.td;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Vector2 mousePosition;
    private Camera camera;
    private Map map;
    private MonsterEmitter monsterEmitter;
    private Turret turret;
    private TextureRegion selectedCellTexture;
    private ParticleEmitter particleEmitter;
    private BulletEmitter bulletEmitter;
    private BitmapFont font24;
    private float monsterTimer;
    private int score;
    private int selectedCellX, selectedCellY;
    private Player player;

    public Map getMap() {
        return map;
    }

    public ParticleEmitter getParticleEmitter() {
        return particleEmitter;
    }

    public MonsterEmitter getMonsterEmitter() {
        return monsterEmitter;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    public GameScreen(SpriteBatch batch, Camera camera) {
        this.batch = batch;
        this.camera = camera;
    }

    @Override
    public void show() {
        mousePosition = new Vector2(0, 0);
        InputProcessor myProc = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                mousePosition.set(screenX, screenY);
                ScreenManager.getInstance().getViewport().unproject(mousePosition);

                if (selectedCellX == (int) (mousePosition.x / 80) && selectedCellY == (int) (mousePosition.y / 80)) {
                    map.setWall((int) (mousePosition.x / 80), (int) (mousePosition.y / 80));
                }

                selectedCellX = (int) (mousePosition.x / 80);
                selectedCellY = (int) (mousePosition.y / 80);

                return true;
            }
        };
        // InputMultiplexer im = new InputMultiplexer(stage, myProc);
        Gdx.input.setInputProcessor(myProc);

        this.particleEmitter = new ParticleEmitter();
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/zorque24.ttf");
        this.bulletEmitter = new BulletEmitter(this);
        this.map = new Map("level01.map");
        this.monsterEmitter = new MonsterEmitter(this);
        this.turret = new Turret(this);
        this.selectedCellTexture = Assets.getInstance().getAtlas().findRegion("cursor");
        this.player = new Player();
    }

    @Override
    public void render(float delta) {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        batch.begin();
        map.render(batch);

        batch.setColor(1, 1, 0, 0.5f);
        batch.draw(selectedCellTexture, selectedCellX * 80, selectedCellY * 80);
        batch.setColor(1, 1, 1, 1);

        monsterEmitter.render(batch);
        turret.render(batch);
        bulletEmitter.render(batch);
        particleEmitter.render(batch);

        font24.draw(batch, "SCORE: " + player.getGold(), 20, 700);
        font24.draw(batch, "HEALTH: " + player.getHP(), 150, 700);

        batch.end();
    }

    public void update(float dt) {
        particleEmitter.setup(640, 360, MathUtils.random(-20.0f, 20.0f), MathUtils.random(20.0f, 80.0f), 0.9f, 1.0f, 0.2f, 1, 0, 0, 1, 1, 1, 0, 1);
        map.update(dt);
        monsterEmitter.update(dt);
        turret.update(dt);
        particleEmitter.update(dt);
        generateMonsters(dt);
        bulletEmitter.update(dt);
        checkCollisions();
        monsterEmitter.checkPool();
        particleEmitter.checkPool();
        bulletEmitter.checkPool();
    }

    public void checkCollisions() {
        for (int i = 0; i < bulletEmitter.getActiveList().size(); i++) {
            Bullet b = bulletEmitter.getActiveList().get(i);
            //hw5
            for (int j = 0; j < monsterEmitter.getActiveList().size(); j++) {
                Monster monster = monsterEmitter.getActiveList().get(j);
                if(monster.getPosition().dst(b.getPosition())<30){
                    monster.getDamage(b.getDamage());
                    b.deactivate();
                }
            }
            if (b.getPosition().x < 0 || b.getPosition().x > 1280 ||
                    b.getPosition().y < 0 || b.getPosition().y > 720) {
                b.deactivate();
                continue;
            }
            if (!map.isCellEmpty((int) (b.getPosition().x / 80), (int) (b.getPosition().y / 80))) {
                b.deactivate();
            }
        }
        for (int i = 0; i < monsterEmitter.getActiveList().size(); i++) {
            Monster monster = monsterEmitter.getActiveList().get(i);
            if(monster.getPosition().x<50){
                player.getDamage(10);
                monster.deactivate();
            }
        }
    }

    public void generateMonsters(float dt) {
        monsterTimer += dt;
        if (monsterTimer > 3.0f) {
            monsterTimer = 0;
            monsterEmitter.setup(15, MathUtils.random(0, 8));
        }
    }


    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().resize(width, height);
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

    public Player getPlayer() {
        return player;
    }
}
