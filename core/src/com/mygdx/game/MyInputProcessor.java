package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {
    public boolean movingRight = false;
    public boolean movingLeft = false;
    public boolean movingForward = false;
    public boolean movingBack = false;

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
