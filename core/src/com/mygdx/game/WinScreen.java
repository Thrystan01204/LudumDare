package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class WinScreen implements Screen {
    final BananaPeelSplit game;

    OrthographicCamera camera;
    private Music mainMusic;

    public WinScreen(final BananaPeelSplit game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 640);
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("intro_fin.wav"));
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


        game.font.draw(game.batch, "Deep down, you found your friend, banana peel are so slippery aren't they ?", 100, 290);
        game.font.draw(game.batch, "You win ! Thanks for playing !", 100, 270);
        game.font.draw(game.batch, "Tap to resart the game", 100, 240);

        game.batch.end();

        if(Gdx.input.justTouched()){
            game.setScreen(new MainMenuScreen(game));
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
        mainMusic.dispose();
    }
}
