package com.javakaian.shooter.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.javakaian.network.OClient;
import com.javakaian.network.messages.PositionMessage;

public class Player implements GameObject {

	private float x, y, size;
	private String name;
	private OClient mc;

	public Player() {
	}

	public Player(float x, float y, float size, String name, OClient mc) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.name = name;
		this.mc = mc;
	}

	@Override
	public void render(ShapeRenderer sr) {
		// TODO Auto-generated method stub
		sr.setColor(Color.GREEN);
		sr.rect(x, y, size, size);
		sr.setColor(Color.WHITE);
	}

	@Override
	public void update(float deltaTime) {

		float amount = 200 * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.W)) {
			y += amount;
			sendPosition(x, y);

		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			y -= amount;
			sendPosition(x, y);

		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			x -= amount;
			sendPosition(x, y);

		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			x += amount;
			sendPosition(x, y);

		}

	}

	private void sendPosition(float x, float y) {

		PositionMessage p = new PositionMessage();
		p.name = name;
		p.x = x;
		p.y = y;
		mc.getClient().sendUDP(p);
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public String getName() {
		return name;
	}
}
