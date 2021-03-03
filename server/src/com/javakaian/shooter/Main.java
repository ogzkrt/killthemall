package com.javakaian.shooter;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public class Main {

	public static void main(String[] args) {

		HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
		conf.updatesPerSecond = 60;

		HeadlessApplication app = new HeadlessApplication(new KillThemAllServer(), conf);

	}

}
