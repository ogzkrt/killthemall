package com.javakaian.shooter.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class AimLine {

	private Vector2 begin;
	private Vector2 end;

	private OrthographicCamera camera;

	private float angle = (float) (Math.PI / 2);

	public AimLine(Vector2 begin, Vector2 end) {

		this.begin = begin;
		this.end = end;
	}

	public void render(ShapeRenderer sr) {

		sr.setColor(Color.RED);
		sr.line(begin, end);
		sr.setColor(Color.WHITE);
	}

	public void update(float deltaTime) {

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			Vector3 up = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			end.x = up.x;
			end.y = up.y;

			angle = calculateAngle();

		} else {
			end = begin;
			angle = (float) (Math.PI / 2);
		}

	}

	private float calculateAngle() {

		Vector2 pos = new Vector2(begin.x, begin.y);
		Vector2 mouse = new Vector2(end.x, end.y);
		return -mouse.sub(pos).angleRad();

	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void setBegin(Vector2 begin) {
		this.begin = begin;
	}

	public void setEnd(Vector2 end) {
		this.end = end;
	}

	public float getAngle() {
		return angle;
	}
}
