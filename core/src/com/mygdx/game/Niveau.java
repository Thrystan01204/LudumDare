package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class Niveau implements Screen {

    final BananaPeelSplit game;

    public boolean lvl1 = true;
    public boolean end_game = false;

    OrthographicCamera camera;

    public Niveau(final BananaPeelSplit game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 640);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Tap anywhere to begin !", 245, 290);

        game.batch.end();

        if(lvl1) {
            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            game.font.draw(game.batch, "Level - 1", 300, 320);
            game.batch.end();
            game.setScreen(new GameScreen(game, 1));
            dispose();
        } else {
            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            game.font.draw(game.batch, "Level - 2", 300, 320);
            game.batch.end();
            game.setScreen(new GameScreen(game, 2));
            dispose();
        }

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
