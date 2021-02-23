package com.javakaian.shooter.shapes;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

public class Enemy {

	private float x, y, size;
	private String name;

	private Set<Bullet> bulletSet;

	public Enemy() {
		// TODO Auto-generated constructor stub
	}

	public Enemy(float x, float y, String name) {
		this.x = x;
		this.y = y;
		this.size = 50;
		this.name = name;

		bulletSet = new HashSet<Bullet>();

	}

	public void update(float deltaTime) {
		bulletSet.forEach(b -> b.update(deltaTime));
		bulletSet = bulletSet.stream().filter(b -> b.getPosition().y < Gdx.graphics.getHeight())
				.collect(Collectors.toSet());
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

	public void shoot() {
		bulletSet.add(new Bullet(x + size / 2 - 5, y + size / 2 - 5, 10));
	}

}
