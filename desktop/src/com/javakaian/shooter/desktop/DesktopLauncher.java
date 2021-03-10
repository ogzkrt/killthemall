package com.javakaian.shooter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.javakaian.shooter.KillThemAll;
import com.javakaian.shooter.utils.GameConstants;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConstants.SCREEN_WIDTH;
		config.height = GameConstants.SCREEN_HEIGHT;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		// config.x = 2500;
		new LwjglApplication(new KillThemAll(), config);
	}
}
