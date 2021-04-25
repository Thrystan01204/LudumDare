package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BananaPeelSplit;

public class CedricDesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "BananaPeelSplit";
        config.width = 1280;
        config.height = 640;
        new LwjglApplication(new BananaPeelSplit(), config);
    }
}

