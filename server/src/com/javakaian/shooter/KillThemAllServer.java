package com.javakaian.shooter;

import org.apache.log4j.Logger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class KillThemAllServer extends ApplicationAdapter {

	private float time;
	private int updateCounter;

	private ServerWorld serverWorld;

	private Logger logger = Logger.getLogger(KillThemAllServer.class);

	public KillThemAllServer() {

		time = 0;
		updateCounter = 0;

		serverWorld = new ServerWorld();

	}

	@Override
	public void create() {

		logger.debug("Server is up");

	}

	@Override
	public void render() {

		float deltaTime = Gdx.graphics.getDeltaTime();
		time += deltaTime;
		updateCounter++;
		if (time >= 1) {
			time = 0;
			updateCounter = 0;
		}

		serverWorld.update(deltaTime);
	}

	@Override
	public void dispose() {
		// this method will be called when the server disposed. Dispose here things that
		// you wanna.
	}

}
