package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.*;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen {

    final BananaPeelSplit game;
    OrthographicCamera camera;
    final Grid gamegrid;

    public Texture murImage;
    public Texture solImage1;
    public Texture solImage2;
    public Texture solImage3;
    public Texture escalierImage;

    // Physics
    private World world;
    private final Box2DDebugRenderer box2dDebugRender;

    public GameScreen(final BananaPeelSplit game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 640);



        // Textures
        murImage = new Texture(Gdx.files.internal("wall.png"));
        solImage1 = new Texture(Gdx.files.internal("ground.png"));
        solImage2 = new Texture(Gdx.files.internal("ground1.png"));
        solImage3 = new Texture(Gdx.files.internal("ground2.png"));
        escalierImage = new Texture(Gdx.files.internal(("escalier.png")));

        //Physics
        world = new World(new Vector2(0, 0), false);

        box2dDebugRender = new Box2DDebugRenderer();

        gamegrid = new Grid(40,40, world);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        world.step(1/60f, 6, 2);

        ScreenUtils.clear(255, 255, 255, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                if (gamegrid.isMur(i, j)) {
                    game.batch.draw(murImage, i * 16, j * 16, 16, 16);
                } else if (gamegrid.isEscalier(i,j)) {
                    game.batch.draw(escalierImage, i * 16, j * 16, 16, 16);
                } else {
                    if (gamegrid.getGrille(i,j) == 0) {
                        game.batch.draw(solImage1, i * 16, j * 16, 16, 16);
                    } else if (gamegrid.getGrille(i,j) == 1) {
                        game.batch.draw(solImage2, i * 16, j * 16, 16, 16);
                    } else {
                        game.batch.draw(solImage3, i * 16, j * 16, 16, 16);
                    }
                }
            }
        }
        game.batch.end();
        box2dDebugRender.render(world, camera.combined);
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
        murImage.dispose();
        solImage1.dispose();
        solImage2.dispose();
        solImage3.dispose();
        escalierImage.dispose();
        world.dispose();
        box2dDebugRender.dispose();
    }
}
