package com.javakaian.shooter.shapes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.javakaian.network.OClient;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.ShootMessage;

public class Player implements GameObject {

	private float x, y, size;
	private String name;
	private OClient mc;
	private Set<Bullet> bulletSet;

	public Player() {
	}

	public Player(float x, float y, float size, OClient mc) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.name = UUID.randomUUID().toString().replaceAll("-", "");
		this.mc = mc;

		bulletSet = new HashSet<Bullet>();
	}

	@Override
	public void render(ShapeRenderer sr) {
		// TODO Auto-generated method stub
		sr.setColor(Color.GREEN);
		sr.rect(x, y, size, size);
		sr.setColor(Color.WHITE);

		bulletSet.stream().forEach(b -> b.render(sr));
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

		bulletSet.stream().forEach(b -> b.update(deltaTime));
		bulletSet = bulletSet.stream().filter(b -> b.getPosition().y < Gdx.graphics.getHeight())
				.collect(Collectors.toSet());

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

	public void shoot() {
		bulletSet.add(new Bullet(x + size / 2 - 5, y + size / 2 - 5, 10));

		ShootMessage m = new ShootMessage();
		m.name = name;
		mc.getClient().sendUDP(m);

	}
}
