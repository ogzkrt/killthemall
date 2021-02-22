package com.javakaian.shooter.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy implements GameObject {

	private float x, y;
	private String name;

	public Enemy() {
		// TODO Auto-generated constructor stub
	}

	public Enemy(float x, float y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;

	}

	@Override
	public void render(ShapeRenderer sr) {

		sr.rect(x, y, 50, 50);
	}

	@Override
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

}
