package com.javakaian.shooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class KillThemAllServer extends ApplicationAdapter {

	private float time;
	private int updateCounter;

	private ServerWorld serverWorld;

	public KillThemAllServer() {

		time = 0;
		updateCounter = 0;

		serverWorld = new ServerWorld();

	}

	@Override
	public void create() {

		System.out.println("Server is up");

	}

	@Override
	public void render() {

		float deltaTime = Gdx.graphics.getDeltaTime();
		time += deltaTime;
		updateCounter++;
		if (time >= 1) {
			time = 0;
			System.out.println("Update Count : " + updateCounter);
			updateCounter = 0;
		}

		serverWorld.update(deltaTime);
	}

	@Override
	public void dispose() {

	}

}
