package com.javakaian.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.javakaian.shooter.input.GameOverInput;
import com.javakaian.shooter.utils.GameUtils;

public class GameOverState extends State {

	public GameOverState(StateController sc) {
		super(sc);
		ip = new GameOverInput(this);
		smallFont = GameUtils.generateBitmapFont(32, Color.WHITE);

	}

	private BitmapFont smallFont;

	@Override
	public void render() {
		float red = 50f;
		float green = 63f;
		float blue = 94f;
		Gdx.gl.glClearColor(red / 255f, green / 255f, blue / 255f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.begin();
		GameUtils.renderCenter("Game Over", sb, bitmapFont);
		GameUtils.renderCenter("Press R to Restart", sb, smallFont, 0.6f);
		sb.end();
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void restart() {
		PlayState playState = (PlayState) this.sc.getStateMap().get(StateEnum.PlayState.ordinal());
		playState.restart();
	}

}
