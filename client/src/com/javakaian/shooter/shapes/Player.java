package com.javakaian.shooter.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Player {

	private float size;
	private Vector2 position;
	private int id = -1;
	private int health;

	private Vector2 center;

	public Player(float x, float y, float size) {
		this.position = new Vector2(x, y);
		this.size = size;
		this.health = 100;
		center = new Vector2(x, y);
	}

	public void render(ShapeRenderer sr) {
		sr.rect(position.x, position.y, size, size);
		sr.end();
		sr.begin(ShapeType.Filled);
		sr.rect(position.x, position.y, this.health / 2.0f, size);
		sr.end();
		sr.begin(ShapeType.Line);

		center.x = position.x + size / 2;
		center.y = position.y + size / 2;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getCenter() {
		return center;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
