package com.javakaian.shooter.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface GameObject {

	public void render(ShapeRenderer sr);
	public void update(float deltaTime);
}
