package com.javakaian.shooter.shapes;

import com.badlogic.gdx.math.Vector2;

public class Player {

	private float size;
	private Vector2 position;
	private int id;

	public Player() {
	}

	public Player(float x, float y, float size, int id) {
		this.position = new Vector2(x, y);
		this.size = size;
		this.id = id;

	}

	public void update(float deltaTime) {

	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getPosition() {
		return position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
