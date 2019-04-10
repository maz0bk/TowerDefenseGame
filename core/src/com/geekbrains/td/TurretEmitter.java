package com.geekbrains.td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TurretEmitter extends ObjectPool<Turret> {
    private GameScreen gameScreen;

    public TurretEmitter(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    protected Turret newObject() {
        return new Turret(gameScreen);
    }

    public boolean setup(int type,int cellX, int cellY) {
        if (!canIDeployItHere(cellX, cellY)) {
            return false;
        }
        Turret turret = getActiveElement();
        turret.setup(type,cellX, cellY);
        return true;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
    }

    public boolean canIDeployItHere(int cellX, int cellY) {
        if (!gameScreen.getMap().isCellEmpty(cellX, cellY)) {
            return false;
        }
        return !isHereTurret(cellX,cellY);
    }
    private boolean isHereTurret(int cellX, int cellY){
        for (int i = 0; i < activeList.size(); i++) {
            Turret t = activeList.get(i);
            if (t.getCellX() == cellX && t.getCellY() == cellY) {
                return true;
            }
        }
        return false;

    }
    public boolean destroyTurret(int cellX, int cellY) {
        for (int i = 0; i < activeList.size(); i++) {
            Turret t = activeList.get(i);
            if (t.getCellX() == cellX && t.getCellY() == cellY) {
                t.deactivate();
                return true;
            }
        }
        return false;
    }

    public void upgradeTurret(int cellX, int cellY) {
        for (int i = 0; i < activeList.size(); i++) {
            Turret t = activeList.get(i);
            if (t.getCellX() == cellX && t.getCellY() == cellY) {
                t.upgrade();
                t.setup(t.getType(),cellX,cellY);
                break;

            }
        }
    }
}
