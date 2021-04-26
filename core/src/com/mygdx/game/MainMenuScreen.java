package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final BananaPeelSplit game;

    public Texture font;
    OrthographicCamera camera;
    private Music mainMusic;

    public MainMenuScreen(final BananaPeelSplit game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 640);
        font = new Texture(Gdx.files.internal("bananapeel.png"));
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("bosca_test2.wav"));
        mainMusic.setLooping(true);
        mainMusic.play();
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
        game.batch.draw(font, 0, 0, 640, 640);
        game.font.draw(game.batch, "Tap anywhere to begin !", 245, 290);

        game.batch.end();

        if(Gdx.input.justTouched()){
            game.setScreen(new IntroScreen(game));
            mainMusic.stop();
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
        font.dispose();
        mainMusic.dispose();
    }
}
