package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {
    // Ensemble de booléens qui permettent de définir le déplacement sur la carte
    private boolean movingRight = false;
    private boolean movingLeft = false;
    private boolean movingForward = false;
    private boolean movingBack = false;

    // Ensemble de méthodes qui permettent de récupérer la valeur d'un booléen de déplacement
    public boolean isMovingBack() {
        return movingBack;
    }

    public boolean isMovingForward() {
        return movingForward;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    // En fonction de la touche pressé, modifies le booléen associé
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
                movingRight = true;
                break;
            case Input.Keys.Q:
                movingLeft = true;
                break;
            case Input.Keys.Z:
                movingForward = true;
                break;
            case Input.Keys.S:
                movingBack = true;
                break;
        }
        return true;
    }

    // En fonction de la touche pressé, modifies le booléen associé
    @Override
    public boolean keyUp(int keycode){
            switch (keycode) {
                case Input.Keys.D:
                    movingRight = false;
                    break;
                case Input.Keys.Q:
                    movingLeft = false;
                    break;
                case Input.Keys.Z:
                    movingForward = false;
                    break;
                case Input.Keys.S:
                    movingBack = false;
                    break;
            }
            return true;
        }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
