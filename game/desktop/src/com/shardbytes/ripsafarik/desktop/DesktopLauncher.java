package com.shardbytes.ripsafarik.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.shardbytes.ripsafarik.game.MainGame;

public class DesktopLauncher{
	
	public static void main(String[] arg){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(MainGame.INSTANCE, config);
		
	}
	
}
