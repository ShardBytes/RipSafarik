package com.shardbytes.ripsafarik.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.shardbytes.ripsafarik.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RipSafarik BETA";
		config.width = 800;
		config.height = 500;
		new LwjglApplication(new MainGame(), config);
	}
}
