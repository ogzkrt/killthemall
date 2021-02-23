package com.javakaian.shooter.shapes;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

public class Bullet {

	private Vector2 position;
	private float size;
	private String name;

	public Bullet() {
	}

	public Bullet(float x, float y, float size) {
		this.position = new Vector2(x, y);
		this.size = size;
		this.name = UUID.randomUUID().toString().replaceAll("-", "");
	}

	public void update(float deltaTime) {

		float speed = deltaTime * 400;
		position.y += speed;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
