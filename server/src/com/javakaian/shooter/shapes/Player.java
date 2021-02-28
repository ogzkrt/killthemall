package com.javakaian.shooter.shapes;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.Vector2;

public class Player {

	private float size;
	private Vector2 position;
	private int id;
	private Set<Bullet> bulletSet;

	public Player() {
	}

	public Player(float x, float y, float size, int id) {
		this.position = new Vector2(x, y);
		this.size = size;
		this.id = id;

		bulletSet = new HashSet<Bullet>();
	}

	public void update(float deltaTime) {

		bulletSet.stream().forEach(b -> b.update(deltaTime));

	}

	public void checkForCollisiion() {
		bulletSet = bulletSet.stream().filter(b -> (b.getPosition().dst(position) < 500) && (b.isVisible()))
				.collect(Collectors.toSet());

	}

	public void checkEnemy() {

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

	public Set<Bullet> getBulletSet() {
		return bulletSet;
	}

}
