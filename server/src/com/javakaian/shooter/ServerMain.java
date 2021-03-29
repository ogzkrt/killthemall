package com.javakaian.shooter;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public class ServerMain {

	public static void main(String[] args) {

		/**
		 * Since we are not doing any rendering. Server should be headless application.
		 */
		HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();

		conf.updatesPerSecond = 60;

		/** Create server. */
		new HeadlessApplication(new KillThemAllServer(), conf);
	}

}
