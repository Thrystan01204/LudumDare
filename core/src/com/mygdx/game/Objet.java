package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Objet {

    //Epee = 10; Potion = 11; Peau de Banane = 12;
    private int type;

    private Texture texture;

    private World world;

    private Body body;

    public Objet(int type, World world, Texture texture, Vector2 position) {
        this.type = type;
        this.world = world;
        this.texture = texture;

        BodyDef bodyDef = new BodyDef();
        this.body = world.createBody(bodyDef);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position);
        // forme du corps
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 8);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    //Obtenir le type de l'objet
    public int isType() {
        return this.type;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 8, body.getPosition().y - 8, 16, 16);
    }

    public void dispose(){
        texture.dispose();
    }


        //Obtenir la position
    public Vector2 getPosition(){
        return body.getPosition();
    }
}
