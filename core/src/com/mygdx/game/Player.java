package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private Texture texture;
    private Sprite player;
    private int x;
    private int y;
    private int health = 100;
    private int strength = 5;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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
}