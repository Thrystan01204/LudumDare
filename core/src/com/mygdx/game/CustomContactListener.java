package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
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
        Player player = (Player) playerFixture.getUserData();

        if(otherFixture.getUserData() instanceof Objet ){
            Objet obj = (Objet) otherFixture.getUserData();
            obj.pickup();
            if (obj.isType() == 11) {
                player.setVie(player.getVie() + 1);
            } else if (obj.isType() == 10) {
            } else if (obj.isType() == 12) {
                player.hurt();
            }
        } else if(otherFixture.getUserData() instanceof Enemy){
            Enemy enemy = (Enemy) otherFixture.getUserData();

            // c'est une attaque du joueur
            if(playerFixture.getFilterData().categoryBits == Collision.PLAYER_ATTACK_SENSOR){
                Vector2 dir = new Vector2();
                if(player.body.getLinearVelocity().len2() == 0){
                    dir.set(player.facingRight ? 8 : -8, 0);
                } else {
                    dir.set(player.body.getLinearVelocity());
                }
                dir.nor();
                enemy.hurt(dir);
            } else {
                player.hurt();
            }
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
