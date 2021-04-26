package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

public class Enemy {
    private World world;
    private Vector2 position;

    public Body body;
    private Texture texture;
    private Texture vieTexture;

    private Player player;

    private int vie = 3;
    private boolean facingRight = true;

    private boolean attackVisible = false;
    private Texture attackTexture;

    private Timer invincibilityTimer;
    private boolean invincibility = false;

    public boolean dead = false;


    public Enemy(World world, Vector2 position, Player player){
        this.world = world;
        this.position = position;
        this.player = player;
        invincibilityTimer = new Timer();
        texture = new Texture(Gdx.files.internal("feufollet.png"));
        attackTexture = new Texture(Gdx.files.internal("slash.png"));

        vieTexture = new Texture(Gdx.files.internal("coeur.png"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.allowSleep = false;
        bodyDef.fixedRotation = true;
        bodyDef.linearDamping = 0.3f;

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(4);
        shape.setPosition(new Vector2(0, 0));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.2f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.shape = shape;
        // indique sur quel type de collision cette forme de l'objet est
        fixtureDef.filter.categoryBits = Collision.ENEMY;
        // indique avec quel type d'objet il va entrer en collision
        fixtureDef.filter.maskBits = Collision.MURS | Collision.PLAYER | Collision.PLAYER_ATTACK_SENSOR;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(this);

    }

    public void update(){
        if(invincibility) return;
        Vector2 dir = player.body.getPosition().cpy().sub(body.getPosition());
        if(dir.len() < 100){
            dir.nor();
            body.setLinearVelocity(dir.scl(32));
        }
    }

    public void dispose(){
        texture.dispose();
        vieTexture.dispose();
        attackTexture.dispose();
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

    public void hurt(Vector2 dir){
        if(invincibility) return;
        vie--;
        if(vie <= 0 ){
            dead = true;
            return;
        }

        body.applyLinearImpulse(dir.scl(50000), Vector2.Zero, true);

        invincibility = true;
        invincibilityTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                invincibility = false;
            }
        }, 0.22f);
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }


}
