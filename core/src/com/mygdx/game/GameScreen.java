package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.*;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen {

    final BananaPeelSplit game;
    OrthographicCamera camera;
    final Grid gamegrid;



    public Player player;

    // Physics
    private World world;
    private final Box2DDebugRenderer box2dDebugRender;

    public GameScreen(final BananaPeelSplit game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640/3, 640/3);


        //Physics
        world = new World(new Vector2(0, 0), false);

        box2dDebugRender = new Box2DDebugRenderer();

        gamegrid = new Grid(40, 40, world);

        player = new Player(world, gamegrid.getStartPosition());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        player.handleInputs();

        world.step(1/60f, 6, 2);

        ScreenUtils.clear(255, 255, 255, 1);

        camera.position.set(player.getPosition(), 0);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        gamegrid.render(game.batch);
        player.render(game.batch);
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
        world.dispose();
        box2dDebugRender.dispose();
        player.dispose();
    }
}
