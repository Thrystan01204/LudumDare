package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyInputProcessor;


public class TristanMain extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Sprite sprite;
    MyInputProcessor inputProcessor = new MyInputProcessor();

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        sprite = new Sprite(img);
        sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth()/2,
                Gdx.graphics.getHeight()/2 - sprite.getHeight()/2);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render () {
        if(inputProcessor.isMovingLeft())
            sprite.translateX(-1f);
        if(inputProcessor.isMovingRight())
            sprite.translateX(1f);
        if(inputProcessor.isMovingForward())
            sprite.translateY(1f);
        if(inputProcessor.isMovingBack())
            sprite.translateY(-1f);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}
