package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.*;

public class GameScreen implements Screen {

    final BananaPeelSplit game;
    OrthographicCamera camera;
    final Grid gamegrid = new Grid(80,40);

    public Texture murImage;
    public Texture solImage1;
    public Texture solImage2;
    public Texture solImage3;
    public Texture escalierImage;

    public GameScreen(final BananaPeelSplit game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 640);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255, 255, 255, 1);
        murImage = new Texture(Gdx.files.internal("wall.png"));
        solImage1 = new Texture(Gdx.files.internal("ground.png"));
        solImage2 = new Texture(Gdx.files.internal("ground1.png"));
        solImage3 = new Texture(Gdx.files.internal("ground2.png"));
        escalierImage = new Texture(Gdx.files.internal(("escalier.png")));
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 40; j++) {
                if (gamegrid.isMur(i, j)) {
                    game.batch.draw(murImage, i * 16, j * 16, 16, 16);
                } else if (gamegrid.isEscalier(i,j)) {
                    game.batch.draw(escalierImage, i * 16, j * 16, 16, 16);
                } else {
                        game.batch.draw(solImage2, i * 16, j * 16, 16, 16);
                }
            }
        }
        game.batch.end();


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
        game.batch.dispose();
        game.font.dispose();
    }
}
