package com.javakaian.shooter;

import com.badlogic.gdx.backends.headless.HeadlessApplication;

public class Main {

	public static void main(String[] args) {

		HeadlessApplication app = new HeadlessApplication(new KillThemAllServer());

	}

}
