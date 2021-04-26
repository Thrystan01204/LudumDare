package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

public class Player {

    private World world;
    private Body body;

    private Texture texture;
    private Timer attackTimer;
    private Sound attackSound;
    private boolean attackVisible = false;
    private Texture attackTexture;

    private int health = 100;
    private int strength = 5;
    private float moveSpeed = 48.0f;

    private boolean facingRight = true;

    public Player(World world, Vector2 position){
        this.world = world;
        attackTimer = new Timer();
        attackSound = Gdx.audio.newSound(Gdx.files.internal("player_attack.wav"));
        texture = new Texture(Gdx.files.internal("Player.png"));
        attackTexture = new Texture(Gdx.files.internal("slash.png"));


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.allowSleep = false;
        bodyDef.fixedRotation = true;
        bodyDef.linearDamping = 0;

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(4);
        shape.setPosition(new Vector2(0, -4));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.2f;
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    // Attaque un ennemi
    // int howMuch : force de l'attaque, quantité de PV à retirer à l'ennemi
    public int attack(int howMuch){
        return howMuch;
    }

    // Fait perdre de la vie à un joueur
    // int howMuch : quantité de vie à retirer
    public void loseHealth(int howMuch) {
        setHealth(getHealth() - howMuch);
    }

    // Permet de rendre de la vie à un joueur
    // int howMuch : quantité de vie à ajouter
    public void gainHealth(int howMuch){
        setHealth(getHealth() + howMuch);
    }

    public void handleInputs(){
        Vector2 direction = new Vector2(0, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.Z)) direction.y = 1;
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) direction.y = -1;

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            direction.x = 1;
            facingRight = true;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            direction.x = -1;
            facingRight = false;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) attack();

        direction.nor();
        direction.scl(moveSpeed);

        body.setLinearVelocity(direction);
    }

    public void dispose(){
        texture.dispose();
        attackTexture.dispose();
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
        }, 0.15f);
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, body.getPosition().x-8, body.getPosition().y-8, 16, 16, 0, 0, 16, 16, !facingRight, false);
        if(attackVisible){
            float xOffset = facingRight ? 8 : -8;
            batch.draw(attackTexture, body.getPosition().x-8+xOffset, body.getPosition().y-8, 16, 16, 0, 0, 16, 16, !facingRight, false);
        }

    }

    public Vector2 getPosition(){
        return body.getPosition();
    }
}