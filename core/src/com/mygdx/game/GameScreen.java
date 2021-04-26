package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.*;
import com.badlogic.gdx.physics.box2d.World;


public class GameScreen implements Screen {

    final BananaPeelSplit game;
    OrthographicCamera camera;
    final Grid gamegrid;

    ArrayList<Objet> objets;
    ArrayList<Enemy> enemies;
    public Player player;
    private CustomContactListener customContactListener;
    private Music levelMusic;
    private int niveau;

    public boolean finNiveau = false;

    // Physics
    private World world;
    //private final Box2DDebugRenderer box2dDebugRender;

    public GameScreen(final BananaPeelSplit game, int niveau) {
        this.game = game;
        this.niveau = niveau;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640/3, 640/3);

        levelMusic = Gdx.audio.newMusic(Gdx.files.internal("dungeon.wav"));
        levelMusic.setLooping(true);
        levelMusic.play();


        //Physics
        world = new World(new Vector2(0, 0), false);
        customContactListener = new CustomContactListener(game, this);

        world.setContactListener(customContactListener);

        //box2dDebugRender = new Box2DDebugRenderer();

        gamegrid = new Grid(40, 40, world, niveau);

        player = new Player(world, gamegrid.getStartPosition());

        objets = new ArrayList<Objet>();

        enemies = new ArrayList<Enemy>();

        Random r = new Random();
        for(int i=0; i < 20*game.level; i++){


            int x = r.nextInt(40);
            int y = r.nextInt(40);

            if(!gamegrid.isMur(x,y)){
                enemies.add(new Enemy(world, new Vector2(x*16+8, y*16+8) , player, game));
            }

        }
        for(int i=0; i < 5*(3-game.level); i++){

            int x = r.nextInt(40);
            int y = r.nextInt(40);

            if(!gamegrid.isMur(x,y)){
                objets.add(new Objet(11, world, new Vector2(x*16+8, y*16+8)));
            }

            x = r.nextInt(40);
            y = r.nextInt(40);

            if(!gamegrid.isMur(x,y)){
                objets.add(new Objet(12, world, new Vector2(x*16+8, y*16+8)));
            }

        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(finNiveau){
            if(game.level == 1){
                game.level = 2;
                game.setScreen(new GameScreen(game, game.level));
                levelMusic.stop();
                dispose();
            } else {
                game.setScreen(new WinScreen(game));
                levelMusic.stop();
                dispose();
            }
            return;
        }

        if(player.dead){
            game.setScreen(new GameOverScreen(game));
            levelMusic.stop();
            dispose();
            return;
        }

        player.handleInputs();

        world.step(1/60f, 6, 2);

        for(int i=0; i < objets.size(); i++){
            Objet o = objets.get(i);
            if(o.pickedup){
                world.destroyBody(o.body);
                objets.remove(o);
                o.dispose();
            }
        }

        for(int i=0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            if(e.dead){
                world.destroyBody(e.body);
                enemies.remove(e);
                e.dispose();
            } else {
                e.update();
            }
        }

        ScreenUtils.clear(0, 0, 0, 1);

        camera.position.set(player.getPosition(), 0);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        gamegrid.render(game.batch);


        for(int i=0; i < objets.size(); i++){
            Objet o = objets.get(i);
            o.render(game.batch);
        }

        for(int i=0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            e.render(game.batch);
        }

        player.render(game.batch);

        game.batch.end();

        //box2dDebugRender.render(world, camera.combined);
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
        //box2dDebugRender.dispose();
        levelMusic.dispose();
        player.dispose();
    }
}
