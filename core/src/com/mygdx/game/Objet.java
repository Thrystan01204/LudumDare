package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Objet {

    //Epee = 10; Potion = 11; Peau de Banane = 12;
    private int type;

    private Texture texture;
    private Sound pickupSound;
    boolean pickedup = false;

    private World world;

    public Body body;

    final Texture peauBanane = new Texture(Gdx.files.internal("peauBanane.png"));
    final Texture epee = new Texture(Gdx.files.internal("epee.png"));
    final Texture potion = new Texture(Gdx.files.internal("potion.png"));

    public Objet(int type, World world, Vector2 position) {
        this.type = type;
        this.world = world;
        this.texture = texture;
        pickupSound = Gdx.audio.newSound(Gdx.files.internal("object_pickup.wav"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position);
        bodyDef.fixedRotation =true;
        bodyDef.linearDamping = 0.5f;
        bodyDef.allowSleep = false;

        body = world.createBody(bodyDef);

        // forme du corps
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4, 4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.restitution = 0.8f;

        // indique sur quel type de collision cette forme de l'objet est
        fixtureDef.filter.categoryBits = Collision.OBJECT;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(this);
    }

    //Obtenir le type de l'objet
    public int isType() {
        return this.type;
    }

    public void render(SpriteBatch batch) {
        if (this.type == 10) {
            batch.draw(epee, body.getPosition().x - 4, body.getPosition().y - 4, 8, 8);
        } else if (this.type == 11) {
            batch.draw(potion, body.getPosition().x - 4, body.getPosition().y - 4, 8, 8);
        } else {
            batch.draw(peauBanane, body.getPosition().x - 4, body.getPosition().y - 4, 8, 8);
        }
    }

    public void pickup(){
        pickupSound.play();
        pickedup = true;
    }

    public void dispose(){
        peauBanane.dispose();
        epee.dispose();
        potion.dispose();
    }


        //Obtenir la position
    public Vector2 getPosition(){
        return body.getPosition();
    }
}
