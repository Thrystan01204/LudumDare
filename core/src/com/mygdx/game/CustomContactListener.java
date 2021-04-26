package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class CustomContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA == null || fixtureB == null) return;
        if(fixtureA.getUserData() == null || fixtureB.getFilterData() == null) return;

        boolean sensorA = fixtureA.isSensor();
        boolean sensorB = fixtureB.isSensor();
        if(sensorA && sensorB) return;

        if(fixtureA.getUserData() instanceof Player)
            playerBeginCollisionDetection(fixtureA, fixtureB);
        else if(fixtureB.getUserData() instanceof Player )
            playerBeginCollisionDetection(fixtureB, fixtureA);

    }

    public void playerBeginCollisionDetection(Fixture playerFixture, Fixture otherFixture){
        if(otherFixture.getUserData() instanceof Objet ){
            Objet obj = (Objet) otherFixture.getUserData();
            obj.pickup();
        } else if(otherFixture.getUserData() instanceof Enemy){
            Enemy enemy = (Enemy) otherFixture.getUserData();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
