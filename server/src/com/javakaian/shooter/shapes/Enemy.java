package com.javakaian.shooter.shapes;

import java.util.UUID;

public class Enemy {

	private float x, y, size;
	private String name;
	private boolean visible = true;

	public Enemy() {
		// TODO Auto-generated constructor stub
	}

	public Enemy(float x, float y, float size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.name = UUID.randomUUID().toString().replaceAll("-", "");

	}

	public void update(float deltaTime) {

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

}
