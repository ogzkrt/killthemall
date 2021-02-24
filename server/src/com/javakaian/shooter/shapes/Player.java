package com.javakaian.shooter.shapes;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.Vector2;

public class Player {

	private float size;
	private Vector2 position;
	private String name;
	private Set<Bullet> bulletSet;

	public Player() {
	}

	public Player(float x, float y, float size, String name) {
		this.position = new Vector2(x, y);
		this.size = size;
		this.name = name;

		bulletSet = new HashSet<Bullet>();
	}

	public void update(float deltaTime) {

		bulletSet.stream().forEach(b -> b.update(deltaTime));

	}

	public void checkForCollisiion() {
		bulletSet = bulletSet.stream().filter(b -> b.getPosition().y > 0).collect(Collectors.toSet());

	}

	public void checkEnemy() {

	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getPosition() {
		return position;
	}

	public String getName() {
		return name;
	}

	public Set<Bullet> getBulletSet() {
		return bulletSet;
	}

}
