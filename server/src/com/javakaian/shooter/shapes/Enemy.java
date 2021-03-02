package com.javakaian.shooter.shapes;

import java.util.UUID;

import com.badlogic.gdx.math.Rectangle;

public class Enemy {

	private float x, y, size;
	private String name;
	private boolean visible = true;
	private Rectangle boundRect;

	public Enemy() {
		// TODO Auto-generated constructor stub
	}

	public Enemy(float x, float y, float size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.name = UUID.randomUUID().toString().replaceAll("-", "");
		this.boundRect = new Rectangle(x, y, size, size);

	}

	public void update(float deltaTime) {

		this.boundRect.x = x;
		this.boundRect.y = y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Rectangle getBoundRect() {
		return boundRect;
	}
}
