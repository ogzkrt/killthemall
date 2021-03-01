package com.javakaian.shooter.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player {

	private float size;
	private Vector2 position;
	private int id = -1;

	public Player() {
	}

	public Player(float x, float y, float size) {
		this.position = new Vector2(x, y);
		this.size = size;

	}

	public void render(ShapeRenderer sr) {
		// TODO Auto-generated method stub
		sr.setColor(Color.GREEN);
		sr.rect(position.x, position.y, size, size);
		sr.setColor(Color.WHITE);

	}

	public void setPosition(Vector2 position) {
		this.position = position;
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

}
