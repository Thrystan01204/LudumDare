package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy {
    private World world;
    private Vector2 position;


    public Enemy(World world, Vector2 position){
        this.world = world;
        this.position = position;


    }
}
