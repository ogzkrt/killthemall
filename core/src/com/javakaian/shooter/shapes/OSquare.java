package com.javakaian.shooter.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class OSquare implements GameObject{
	
	private float x,y,size;
	
	public OSquare(float x,float y,float size) {
		this.x = x;
		this.y=y;
		this.size=size;
	}

	@Override
	public void render(ShapeRenderer sr) {
		sr.rect(x, y, size, size);
	}

	@Override
	public void update(float deltaTime) {
		
	}
	
	

}
