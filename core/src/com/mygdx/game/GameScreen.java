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

    public Texture murImage;
    public Texture solImage1;
    public Texture solImage2;
    public Texture solImage3;
    public Texture escalierMImage;
    public Texture escalierDImage;
    public Texture solLave1;
    public Texture solLave2;
    public Texture bananeImage;

    public Player player;

    public Objet banane;

    // Physics
    private World world;
    private final Box2DDebugRenderer box2dDebugRender;

    public GameScreen(final BananaPeelSplit game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 640);

        // Textures
        murImage = new Texture(Gdx.files.internal("wall.png"));
        solImage1 = new Texture(Gdx.files.internal("ground.png"));
        solImage2 = new Texture(Gdx.files.internal("ground1.png"));
        solImage3 = new Texture(Gdx.files.internal("ground2.png"));
        escalierMImage = new Texture(Gdx.files.internal(("escalier.png")));
        escalierDImage = new Texture(Gdx.files.internal(("descendre.png")));
        solLave2 = new Texture(Gdx.files.internal(("lavaground.png")));
        solLave1 = new Texture(Gdx.files.internal(("lavastone.png")));
        bananeImage = new Texture(Gdx.files.internal(("peauBanane.png")));

        //Physics
        world = new World(new Vector2(0, 0), false);

        box2dDebugRender = new Box2DDebugRenderer();

        gamegrid = new Grid(40, 40, world);

        player = new Player(world, new Vector2(640/2, 640/2));
        banane = new Objet(12, world, new Texture(Gdx.files.internal("peauBanane.png")),new Vector2(100, 100));

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
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                if (gamegrid.isMur(i, j)) {
                    game.batch.draw(murImage, i * 16, j * 16, 16, 16);
                } else if (gamegrid.getGrille(i, j) == 5) {
                    game.batch.draw(escalierMImage, i * 16, j * 16, 16, 16);
                } else if (gamegrid.getGrille(i, j) == 6) {
                    game.batch.draw(escalierDImage, i * 16, j * 16, 16, 16);
                } else { //Mur
                    if (gamegrid.getGrille(i, j) == 0) {
                        //game.batch.draw(solImage1, i * 16, j * 16, 16, 16);
                        game.batch.draw(solLave1, i * 16, j * 16, 16, 16);
                    } else if (gamegrid.getGrille(i, j) == 1) {
                        //game.batch.draw(solImage2, i * 16, j * 16, 16, 16);
                        game.batch.draw(solLave1, i * 16, j * 16, 16, 16);
                    } else {
                        game.batch.draw(solLave2, i * 16, j * 16, 16, 16);
                        //game.batch.draw(solImage3, i * 16, j * 16, 16, 16);
                    }
                }
            }
        }
        player.render(game.batch);
        banane.render(game.batch);
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
        escalierMImage.dispose();
        escalierDImage.dispose();
        solLave2.dispose();
        solLave1.dispose();
        world.dispose();
        box2dDebugRender.dispose();
        player.dispose();
        banane.dispose();
    }
}
