package com.javakaian.shooter.shapes;

import com.badlogic.gdx.math.Vector2;

public class Bullet {

	private Vector2 position;
	private float size;
	private float angle;
	private boolean visible = true;

	private float ttlCounter = 0;

	public Bullet() {
	}

	public Bullet(float x, float y, float size, float angle) {
		this.position = new Vector2(x, y);
		this.size = size;
		this.angle = angle;
	}

	public void update(float deltaTime) {

		float speed = deltaTime * 500;
		this.ttlCounter += deltaTime;

		float dx = (float) Math.cos(angle);
		float dy = (float) Math.sin(angle);

		position.y -= speed * dy;
		position.x += speed * dx;
		if (ttlCounter > 2) {
			visible = false;
			ttlCounter = 0;
		}
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
