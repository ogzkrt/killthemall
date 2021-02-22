package com.javakaian.shooter.shapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ManyShapes implements GameObject {
	
	private List<OSquare> squareList ;
	
	public ManyShapes() {
		
		squareList = new ArrayList<OSquare>();
		
		Random r = new Random();
		
		for(int i=0;i<20;i++) {
			int x = r.nextInt(2000);
			int y = r.nextInt(1500)+r.nextInt(100)+r.nextInt(100);
			int size = r.nextInt(50);
			squareList.add(new OSquare(x,y, 50));
		}
	}

	@Override
	public void render(ShapeRenderer sr) {
		for (OSquare oSquare : squareList) {
			oSquare.render(sr);
		}
	}

	@Override
	public void update(float deltaTime) {
		for (OSquare oSquare : squareList) {
			oSquare.update(deltaTime);
		}
	}

}
