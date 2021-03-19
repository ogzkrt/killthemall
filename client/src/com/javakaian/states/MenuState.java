package com.javakaian.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.javakaian.shooter.input.MenuStateInput;
import com.javakaian.shooter.utils.GameUtils;

public class MenuState extends State {

	private BitmapFont smallFont;

	public MenuState(StateController sc) {
		super(sc);

		ip = new MenuStateInput(this);
		smallFont = GameUtils.generateBitmapFont(32, Color.WHITE);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		float red = 50f;
		float green = 63f;
		float blue = 94f;
		Gdx.gl.glClearColor(red / 255f, green / 255f, blue / 255f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.begin();
		GameUtils.renderCenter("Menu", sb, bitmapFont);
		GameUtils.renderCenter("Press Space to Contunie", sb, smallFont, 0.6f);
		sb.end();

	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	public void quit() {
		Gdx.app.exit();
	}

	@Override
	public void dispose() {

	}

	public void restart() {

		PlayState playState = (PlayState) this.sc.getStateMap().get(StateEnum.PlayState.ordinal());
		playState.restart();

	}

}
