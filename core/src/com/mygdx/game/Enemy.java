package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

public class Enemy {
    private World world;
    private Vector2 position;

    public Body body;
    private Texture texture;
    private Texture vieTexture;

    private int vie = 3;
    private boolean facingRight = true;

    private Timer attackTimer;
    private Sound attackSound;
    private boolean attackVisible = false;
    private Texture attackTexture;

    public boolean dead = false;


    public Enemy(World world, Vector2 position){
        this.world = world;
        this.position = position;

        attackTimer = new Timer();
        attackSound = Gdx.audio.newSound(Gdx.files.internal("player_attack.wav"));
        texture = new Texture(Gdx.files.internal("feufollet.png"));
        attackTexture = new Texture(Gdx.files.internal("slash.png"));

        vieTexture = new Texture(Gdx.files.internal("coeur.png"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.allowSleep = false;
        bodyDef.fixedRotation = true;
        bodyDef.linearDamping = 0.99f;

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(4);
        shape.setPosition(new Vector2(0, 0));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.2f;
        fixtureDef.shape = shape;
        // indique sur quel type de collision cette forme de l'objet est
        fixtureDef.filter.categoryBits = Collision.ENEMY;
        // indique avec quel type d'objet il va entrer en collision
        fixtureDef.filter.maskBits = Collision.MURS | Collision.PLAYER;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(this);

    }

    public void dispose(){
        texture.dispose();
        vieTexture.dispose();
        attackTexture.dispose();
        attackSound.dispose();
    }
    public void attack(){
        if(attackVisible) return;
        attackSound.play();
        attackVisible = true;
        attackTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                attackVisible = false;
            }
        }, 0.2f);
    }

    public void render(SpriteBatch batch){

        float x = body.getPosition().x-8;
        float y = body.getPosition().y-8;

        batch.draw(texture, x, y, 16, 16, 0, 0, 16, 16, !facingRight, false);

        if(attackVisible){
            float xOffset = facingRight ? 8 : -8;
            batch.draw(attackTexture, x+xOffset, y, 16, 16, 0, 0, 16, 16, !facingRight, false);
        }

        // affichage de la vie
        for (int i=0; i < vie; i++){
            batch.draw(vieTexture, x+i*8-4, y+16, 8, 8);
        }

    }

    public void hurt(boolean playerFacingRight){
        if(playerFacingRight)
            body.applyLinearImpulse(100, 0, 0, 0, true);
        else
            body.applyLinearImpulse(-100, 0, 0, 0, true);
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }


}
